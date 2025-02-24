package com.example.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName:LoggingExample
 * Package:com.example.boot
 * Description:
 *
 * @Date:2025/2/24 15:08
 * @Author:qs@1.com
 */
public class LoggingExample {
    // 获取 user 模块的 logger
    private static final Logger userLogger = LoggerFactory.getLogger("userLogger");

    // 获取 order 模块的 logger
    private static final Logger orderLogger = LoggerFactory.getLogger("orderLogger");

    // 获取默认的 logger
    private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);

    public static void main(String[] args) {
        // 记录 user 模块的日志
        userLogger.info("User module: User created successfully.");
        userLogger.error("User module: Error occurred while creating user.");

        // 记录 order 模块的日志
        orderLogger.info("Order module: Order placed successfully.");
        orderLogger.error("Order module: Error occurred while placing order.");

        logger.info("default logger for info");
        logger.error("default logger for error");
    }
}
