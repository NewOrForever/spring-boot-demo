package com.learn.fromDubbo;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * ClassName:fromDubbo
 * Package:com.learn
 * Description:
 *
 * @Date:2022/5/30 14:38
 * @Author:qs@1.com
 */
public class LoadBalanceTest {
    public static final Map<String, Integer> ACTIVITY_LIST = new LinkedHashMap<String, Integer>();
    static {
        // 服务器当前的最小活跃数
        ACTIVITY_LIST.put("192.168.0.1", 2);
        ACTIVITY_LIST.put("192.168.0.2", 0);
        ACTIVITY_LIST.put("192.168.0.3", 1);
        ACTIVITY_LIST.put("192.168.0.4", 3);
        ACTIVITY_LIST.put("192.168.0.5", 0);
        ACTIVITY_LIST.put("192.168.0.6", 1);
        ACTIVITY_LIST.put("192.168.0.7", 4);
        ACTIVITY_LIST.put("192.168.0.8", 2);
        ACTIVITY_LIST.put("192.168.0.9", 7);
        ACTIVITY_LIST.put("192.168.0.10", 3);
    }
    public static void main(String[] args) {
        Optional<Integer> minVal = ACTIVITY_LIST.values().stream().min(Comparator.naturalOrder());
        if (minVal.isPresent()) {
            System.out.println(minVal.get());
        }
    }
}
