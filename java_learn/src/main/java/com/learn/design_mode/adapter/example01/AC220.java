package com.learn.design_mode.adapter.example01;

import org.springframework.aop.config.AopConfigUtils;

/**
 * ClassName:AC220
 * Package:com.learn.design_mode.adapter.example01
 * Description: 220v 交流电
 *
 * @Date:2023/2/25 11:25
 * @Author:qs@1.com
 */
public class AC220 {
    public int output220v() {
        int output = 220;
        System.out.println("输出220v交流电");
        return output;
    }
}
