package com.learn.observer;

/**
 * ClassName:Student
 * Package:com.example.boot.observer
 * Description:
 *
 * @Date:2021/11/29 11:13
 * @Author:qs@1.com
 */
public class Student implements BookWeather {
    private String userName;

    public Student(String userName) {
        this.userName = userName;
    }


    @Override
    public void notifyWeather(String weather) {
        switch (weather) {
            case "下雨":
                System.out.println("下雨了，" + this.userName + "撑着伞去上学");
                break;
            case  "下雪":
                System.out.println("下雪了，" + this.userName + "打着雪仗去上学");
                break;
            case "多云":
                System.out.println("多云，" + this.userName + "带了一把伞去上学");
                break;
            case  "晴天":
                System.out.println("晴天，" + this.userName + "高高兴兴去上学");
                break;
            default:
                break;
        }
    }
}
