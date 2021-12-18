package com.learn.observer;

/**
 * ClassName:BookWeather
 * Package:com.example.boot.observer
 * Description: 订阅天气的人实现的接口，具有相同的操作：接收天气并执行相应操作
 *
 * @Date:2021/11/29 10:55
 * @Author:qs@1.com
 */
public interface BookWeather {
    void notifyWeather(String weather);

}
