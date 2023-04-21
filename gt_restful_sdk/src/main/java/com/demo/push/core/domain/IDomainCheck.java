package com.demo.push.core.domain;

/**
 * ClassName:IDomainCheck
 * Package:com.demo.push.core.domain
 * Description:
 *
 * @Date:2023/4/4 17:06
 * @Author:qs@1.com
 */
public interface IDomainCheck {
    /**
     * 域名检测
     *
     * @param url
     * @return true表示成功，false表示失败，成功数越多，表示域名可用性越高
     */
    boolean check(String url);
}
