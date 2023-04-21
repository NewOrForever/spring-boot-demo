package com.demo.push.core.manager;

import com.demo.push.MyApiConfiguration;
import com.demo.push.common.http.HttpManager;
import com.demo.push.core.Configs;
import com.demo.push.core.domain.DomainCheck;
import com.demo.push.core.domain.DomainListBO;
import com.demo.push.core.domain.IDomainCheck;
import com.demo.push.core.domain.RasDomainBO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName:HostManager
 * Package:com.demo.push.core.manager
 * Description:
 *
 * @Date:2023/4/3 18:00
 * @Author:qs@1.com
 */
public class HostManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public HostManager(MyApiConfiguration configuration, HttpManager httpManager) {
        this.configuration = configuration;
        this.using = configuration.getDomain();
        this.httpManager = httpManager;
    }

    private final HttpManager httpManager;

    private final MyApiConfiguration configuration;

    /**
     * 正在使用的域名
     */
    private String using;
    /**
     * 切换域名的锁
     */
    private final Lock switchUsingLock = new ReentrantLock();

    /**
     * 天上拉下来的域名
     */
    private RasDomainBO rasDomain;
    private final Object setRasDomainLock = new Object();

    private List<String> sortedDomains;

    /**
     * 域名优先级队列
     */
    private BlockingQueue<String> sortedHostQueue;

    /**
     * 增量，域名切换次数
     */
    private final AtomicInteger switchIncr = new AtomicInteger(0);
    /**
     * 总量，域名切换次数
     */
    private final AtomicInteger switchTotal = new AtomicInteger(0);

    public String getUsing() {
        return this.using;
    }

    /**
     * 处理天上返回的域名
     *
     * @param domainBO
     */
    public void handleDomain(RasDomainBO domainBO) {
        if (domainBO == null || CollectionUtils.isEmpty(domainBO.getHostList())) {
            return;
        }
        if (setRasDomainBO(domainBO)) {
            if (!CollectionUtils.isEmpty(sortedDomains)) {
                sortedDomains.clear();
            }
            resetHostQueue();
            switchTo(domainBO.getHostList().get(0).getDomainList().get(0));
        }
    }

    /**
     * @param willUse
     */
    public void switchTo(String willUse) {
        if (StringUtils.isNotEmpty(willUse) && willUse.equals(this.using)) {
            return;
        }
        logger.debug("switch host. old: {}, new: {}.", this.using, willUse);
        switchIncr.incrementAndGet();
        switchTotal.incrementAndGet();
        this.using = willUse;
    }

    /**
     * 切换域名
     */
    public void switchHost() {
        if (CollectionUtils.isEmpty(sortedHostQueue)) {
            resetHostQueue();
        }
        if (!switchUsingLock.tryLock()) {
            return;
        }
        try {
            final String host = sortedHostQueue.poll();
            if (StringUtils.isNotEmpty(host)) {
                switchTo(host);
            } else {
                logger.debug("switchHost. hostQueue.poll() return null. host: {}.", host);
                switchTo(configuration.getDomain());
            }
        } finally {
            switchUsingLock.unlock();
        }
    }

    /**
     * 当 {@link #sortedHostQueue}为空时重置
     */
    private synchronized void resetHostQueue() {
        if (CollectionUtils.isNotEmpty(sortedHostQueue)) {
            return;
        }
        resetHostQueueHard();
    }

    private void resetHostQueueHard() {
        RasDomainBO rasDomain = this.rasDomain;
        BlockingQueue<String> queue = new LinkedBlockingDeque<String>();
        if (CollectionUtils.isEmpty(sortedDomains)) {
            if (rasDomain != null &&
                    CollectionUtils.isNotEmpty(rasDomain.getHostList())) {
                for (DomainListBO domainListBO : rasDomain.getHostList()) {
                    if (CollectionUtils.isNotEmpty(domainListBO.getDomainList())) {
                        queue.addAll(domainListBO.getDomainList());
                    }
                }
            }
        } else {
            queue.addAll(sortedDomains);
        }
        if (StringUtils.isNotEmpty(configuration.getDomain())) {
            queue.add(configuration.getDomain());
        }
        if (CollectionUtils.isNotEmpty(Configs.URLS)) {
            queue.addAll(Configs.URLS);
        }
        this.sortedHostQueue = queue;
    }

    public RasDomainBO getRasDomain() {
        return rasDomain;
    }

    public boolean setRasDomainBO(RasDomainBO rasDomainBO) {
        if (rasDomainBO == null ||
                CollectionUtils.isEmpty(rasDomainBO.getHostList()) ||
                StringUtils.isEmpty(rasDomainBO.getDomainHash())) {
            return false;
        }
        if (this.rasDomain != null &&
                rasDomainBO.getDomainHash().equalsIgnoreCase(this.rasDomain.getDomainHash())) {
            return false;
        }
        synchronized (setRasDomainLock) {
            if (this.rasDomain != null &&
                    rasDomainBO.getDomainHash().equalsIgnoreCase(this.rasDomain.getDomainHash())) {
                return false;
            }
            this.rasDomain = rasDomainBO;
            return true;
        }
    }

    public BlockingQueue<String> getSortedHostQueue() {
        return sortedHostQueue;
    }

    public void setSortedHostQueue(BlockingQueue<String> sortedHostQueue) {
        this.sortedHostQueue = sortedHostQueue;
    }

    public String domainHash() {
        return rasDomain == null ? null : rasDomain.getDomainHash();
    }

    /**
     * 分析最稳定域名
     */
    public void analyseStableDomain() {
        if (rasDomain == null || CollectionUtils.isEmpty(rasDomain.getHostList())) {
            logger.debug("Analysing stopped because the hostList is empty.");
            return;
        }
        final List<String> sortedHost = new DomainCheck(new IDomainCheck() {
            @Override
            public boolean check(final String url) {
                FutureTask<Boolean> task = new FutureTask(new Callable() {
                    @Override
                    public Object call() throws Exception {
                        httpManager.syncHttps(url + "/v2/check", "head", null, null, null);
                        return true;
                    }
                });
                new Thread(task).start();
                try {
                    return task.get(1, TimeUnit.SECONDS);
                } catch (TimeoutException e) {
                    task.cancel(true);
                } catch (Exception e) {
                }
                return false;
            }
        }).sort(rasDomain.getHostList());
        if (CollectionUtils.isNotEmpty(sortedHost)) {
            this.sortedDomains = sortedHost;
        }
        logger.debug("analyseStableDomain finished. result: {}.", sortedHost);
        resetHostQueueHard();
        switchHost();
    }

    public int getSwitchIncrNum() {
        return switchIncr.getAndSet(0);
    }

    public int getSwitchTotalNum() {
        return switchTotal.get();
    }
}
