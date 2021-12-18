package com.learn.observer;


/**
 * ClassName:main
 * Package:com.example.boot
 * Description:
 *
 * @Date:2021/11/25 9:52
 * @Author:qs@1.com
 */
public class observerMain {
    public static void main(String[] args) {
        String str = "我我...我我...我要..要要...要要...学学学..学学...编编编编....编程..程....程程程程..程.";
//        Pattern pattern = Pattern.compile("//.{2,}");
//        Matcher matcher = pattern.matcher(str);
//        matcher.matches();
        //str = str.replaceAll("\\.+", "");

//        Pattern pattern = Pattern.compile("(.)\\1+");
//        Matcher matcher = pattern.matcher(str);
//        matcher.find()

//        str = str.replace(".", "");
//        str = str.replaceAll("(.)\\1+", "$1");
//        System.out.println(str);

        /****************** 堆栈 ********************/
//        StrackList strackList = new StrackList();
//        // 入栈
//        strackList.add("库里");
//        strackList.add("泡椒");
//        strackList.add("大胡子");
//        strackList.add("死神");
//        strackList.add("77");
//        System.out.println("入栈，index = " + strackList.getSize());
//        // 出栈
//        Object o1 = strackList.pop();
//        Object o2 = strackList.pop();
//        System.out.println("出栈，o1 = " + o1);
//        System.out.println("出栈，o2 = " + o2);
//        System.out.println("出栈，index = " + strackList.getSize());

        /******************* 观察者模式 *********************/
        WeatherStation station = new WeatherStation();
        station.workStart();

        station.addListener(new Student("库里"));
        station.addListener(new Employee("大胡子"));
    }
}
