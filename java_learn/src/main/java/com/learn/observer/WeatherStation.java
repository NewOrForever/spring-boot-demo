package com.learn.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ClassName:WeatherStation
 * Package:com.example.boot.observer
 * Description: 观察者模式（发布订阅）
 * WeatherStation是一个发布者，实现BookWeather的是订阅者，
 * 发布者维护着需要发布的订阅者列表，当发布消息时，会告知这些订阅者去执行相应的操作
 *
 * @Date:2021/11/29 10:50
 * @Author:qs@1.com
 */
public class WeatherStation {

    private String weather;
    String[] weathers = {"下雨", "下雪", "晴天", "多云"};
    Random random = new Random();
    // 订阅天气的人
    static List<BookWeather> list = new ArrayList<>();

    /**
     * 添加订阅者
     * @param booker
     */
    public void addListener(BookWeather booker){
        list.add(booker);
    }

    /**
     * 气象站工作
     */
    public void workStart() {
        new Thread(() -> {
            while (true) {
                updateWeather();
                try {
                    Thread.sleep(random.nextInt(1000) + 500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateWeather() {
        weather = weathers[random.nextInt(4)];
        for (BookWeather bookWeather : list) {
            // 不同类型的订阅者根据天气执行对应的操作
            bookWeather.notifyWeather(weather);
        }

    }


    public String getWeather() {
        return weather;
    }
}
