package com.demo.push.core;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:Configs
 * Package:com.demo.push.core
 * Description:
 *
 * @Date:2023/4/4 17:00
 * @Author:qs@1.com
 */
public interface Configs {
    int MAX_FAIL_CONTINUOUSLY = 3;

    String HEADER_DOMAIN_HASH_KEY = "domainHash";
    String HEADER_OPEN_STABLE_DOMAIN = "openStableDomain";
    String SDK_VERSION = "1.0.0.9";
    /**
     * 预置域名列表
     */
    List<String> URLS = new ArrayList<String>() {
        {
            this.add("https://restapi.getui.com/v2/");
            this.add("https://cncrestapi.getui.com/v2/");
            this.add("https://nzrestapi.getui.com/v2/");
        }
    };
}
