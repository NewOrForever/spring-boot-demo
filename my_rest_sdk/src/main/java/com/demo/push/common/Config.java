package com.demo.push.common;

import com.demo.push.ApiConfiguration;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * ClassName:Config
 * Package:com.demo.push.common
 * Description:
 *
 * @Date:2023/4/3 17:56
 * @Author:qs@1.com
 */
public class Config {
    public static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    int MAX_FAIL_CONTINUOUSLY = 3;
}
