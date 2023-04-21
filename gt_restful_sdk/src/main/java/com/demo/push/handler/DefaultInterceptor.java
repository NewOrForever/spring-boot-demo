package com.demo.push.handler;

import com.demo.push.MyApiConfiguration;
import com.demo.push.common.ApiException;
import com.demo.push.core.ApiProxyFactory;
import com.demo.push.core.Configs;
import com.demo.push.core.manager.HostManager;
import com.demo.push.core.status.ServiceState;
import com.demo.push.core.status.StateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName:DefaultInterceptor
 * Package:com.demo.push.handler
 * Description:
 *
 * @Date:2023/4/4 17:49
 * @Author:qs@1.com
 */
public class DefaultInterceptor implements ApiInterceptor {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private static final ThreadLocal<Long> START_TIME = new ThreadLocal<>();

    private final ConcurrentMap<String, StateWrapper> stateWrapperMap = new ConcurrentHashMap<String, StateWrapper>();

    private final AtomicInteger failNum = new AtomicInteger(0);

    private final HostManager hostManager;
    private final BlockingQueue<StateWrapper> reportDataQueue;
    private final MyApiConfiguration configuration;

    public DefaultInterceptor(HostManager hostManager, BlockingQueue<StateWrapper> reportDataQueue, MyApiConfiguration configuration) {
        this.hostManager = hostManager;
        this.reportDataQueue = reportDataQueue;
        this.configuration = configuration;
    }

    @Override
    public void pre(ApiProxyFactory.ApiParam apiParam, Map<String, Object> header, String body) {
        START_TIME.set(System.currentTimeMillis());
    }

    @Override
    public void post(ApiProxyFactory.ApiParam apiParam, Map<String, Object> header, String body, String result) {
        log.debug("success. param: {}, result: {}.", apiParam, result);
        failNum.set(0);
    }

    @Override
    public void handleException(ApiProxyFactory.ApiParam apiParam, Map<String, Object> header, String body, ApiException e) {
        log.error("http error. param: {}, body: {}.", apiParam, body, e);
        if (configuration.isOpenCheckHealthDataSwitch()) {
            ServiceState serviceState = get(hostManager.getUsing()).get(apiParam.getUri());
            serviceState.incrFailedTimes();
        }
        if (failNum.incrementAndGet() > Configs.MAX_FAIL_CONTINUOUSLY) {
            log.debug("The num of failures are frequent.");
            failNum.set(0);
            if (configuration.isOpenCheckHealthDataSwitch()) {
                this.reportDataQueue.offer(getAndRemove(hostManager.getUsing()));
            }
            if (configuration.isOpenAnalyseStableDomainSwitch()) {
                // 切换域名
                hostManager.switchHost();
            }
        }
    }

    @Override
    public void afterCompletion(ApiProxyFactory.ApiParam apiParam, Map<String, Object> header, String body, String result) {
        try {
            if (configuration.isOpenCheckHealthDataSwitch()) {
                long cost = System.currentTimeMillis() - START_TIME.get();
                final ServiceState serviceState = get(hostManager.getUsing()).get(apiParam.getUri());
                serviceState.addCallTime(cost);
                serviceState.incrCallTimes();
            }
        } finally {
            START_TIME.remove();
        }
    }

    private StateWrapper get(String host) {
        StateWrapper stateWrapper = stateWrapperMap.get(host);
        if (stateWrapper != null) {
            return stateWrapper;
        }
        synchronized (stateWrapperMap) {
            stateWrapper = stateWrapperMap.get(host);
            if (stateWrapper != null) {
                return stateWrapper;
            }
            stateWrapper = new StateWrapper(host);
            stateWrapperMap.put(host, stateWrapper);
            return stateWrapper;
        }
    }

    public StateWrapper getAndRemove(String host) {
        return stateWrapperMap.remove(host);
    }
}
