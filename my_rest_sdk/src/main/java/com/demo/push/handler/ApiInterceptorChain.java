package com.demo.push.handler;

import java.util.List;

/**
 * ClassName:ApiInterceptorChain
 * Package:com.demo.push.handler
 * Description:
 *
 * @Date:2023/4/7 14:30
 * @Author:qs@1.com
 */
public class ApiInterceptorChain {
    private List<ApiInterceptor> interceptorList;

    public void addInterceptor(ApiInterceptor apiInterceptor) {
        interceptorList.add(apiInterceptor);
    }

    public List<ApiInterceptor> getInterceptorList() {
        return interceptorList;
    }

}
