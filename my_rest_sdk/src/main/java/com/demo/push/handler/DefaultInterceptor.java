package com.demo.push.handler;

import com.demo.push.ApiConfiguration;
import com.demo.push.common.ApiException;
import com.demo.push.core.ApiProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
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

    private final AtomicInteger failNum = new AtomicInteger(0);

    private final ApiConfiguration configuration;

    public DefaultInterceptor(ApiConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void handleHeader(ApiProxyFactory.ApiParam apiParam, Map<String, Object> header, String body) {

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
        if (failNum.incrementAndGet() > Configs.MAX_FAIL_CONTINUOUSLY) {
            log.debug("The num of failures are frequent.");
            failNum.set(0);

        }
    }

    @Override
    public void afterCompletion(ApiProxyFactory.ApiParam apiParam, Map<String, Object> header, String body, String result) {
        try {
                long cost = System.currentTimeMillis() - START_TIME.get();
                log.debug("api request cost: {}ms", cost);
        } finally {
            START_TIME.remove();
        }
    }


}
