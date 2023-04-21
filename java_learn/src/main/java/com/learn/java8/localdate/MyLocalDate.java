package com.learn.java8.localdate;

import net.sf.cglib.core.Local;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;

/**
 * ClassName:MyLocalDate
 * Package:com.learn.java8.localdate
 * Description:
 *
 * @Date:2023/4/21 9:58
 * @Author:qs@1.com
 */
public class MyLocalDate {
    public static void main(String[] args) {
        /*********************localdate************************/
        // 当前日期
        LocalDate now = LocalDate.now();
        System.out.println(now);
        System.out.println(now.getYear());
        System.out.println(now.getMonth());
        System.out.println(now.getDayOfMonth());
        System.out.println(now.getDayOfWeek());
        System.out.println(now.getDayOfYear());

        // 指定日期
        LocalDate date1 = LocalDate.of(2023, 5, 1);
        System.out.println(date1);
        System.out.println("今天到五一还剩下" + now.until(date1, ChronoUnit.DAYS) + "天");

        // 转格式
        System.out.println(date1.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));

        /*********************LocalTime***********************/
        System.out.println("============> LocalTime Test");
        LocalTime timeNow = LocalTime.now();
        System.out.println(timeNow);

        // 指定时间
        LocalTime time1 = LocalTime.of(10, 18, 30);
        System.out.println(time1);

        // 日期+时间拼接
        LocalDateTime localDateTime = timeNow.atDate(now);
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));

        System.out.println("10点18分到现在已经过去了" + time1.until(timeNow, ChronoUnit.MINUTES) + "分钟了");

        System.out.println("往前推1小时：" + timeNow.minus(1, ChronoUnit.HOURS));
        System.out.println("往后推1小时：" + timeNow.plusHours(1));

        /************************LocalDateTime*************************/
        LocalDateTime nowdateTime = LocalDateTime.now();
        System.out.println(nowdateTime);

        LocalDateTime datetime1 = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        System.out.println(datetime1);

        System.out.println(LocalDateTime.MAX);

        // parse
        LocalDateTime parseDatetime = LocalDateTime.parse("2023-01-02T11:12:29");
        System.out.println(parseDatetime);
        System.out.println(LocalDateTime.parse("2023-01-02 11:13:29", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        String formatDatetime = parseDatetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(formatDatetime);

        // with 方法替换
        System.out.println("月份替换后的时间：" + nowdateTime.with(ChronoField.MONTH_OF_YEAR, 5));

        // 比较时间
        System.out.println("时间比较结果：" + nowdateTime.isAfter(datetime1));

        // 增减时间
        LocalDateTime minusWeekDateTime = nowdateTime.minusWeeks(2);
        LocalDateTime plusMonthsDateTime = nowdateTime.plusMonths(1);
        System.out.println("上两周的时间：" + minusWeekDateTime);
        System.out.println("下个月的时间：" + plusMonthsDateTime);

        long start = System.currentTimeMillis();
        System.out.println("======> 开始时间" + start);
        LocalDateTime finalPlusDt = LocalDateTime.now();
        for (int i = 0; i < 10000; i++) {
            finalPlusDt = finalPlusDt.plusDays(1);
        }
        long end = System.currentTimeMillis();
        System.out.println("======> 结束时间" + end);
        System.out.println("------------> 总计耗时：" + (end - start));
        System.out.println(finalPlusDt);

        /********************DateTimeFormatter********************/
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(dtf.format(nowdateTime));


        /**********************Period**************************/
        Period between = Period.between(date1, now);
        System.out.println(between);
        System.out.println("时间间隔：" + "年-" + between.getYears() + "，月-" + between.getMonths() + "，日-" + between.getDays());

        // 用于加减时间
        Period plusMonthPeriod = Period.ofMonths(1);
        Period negativeMonthPeriod = Period.ofMonths(-1);
        System.out.println("当前时间后移1个月：" + now.plus(plusMonthPeriod));
        System.out.println("当前时间前推1个月：" + now.plus(negativeMonthPeriod));

        /**********************Duration 主要用于 时分秒  ===> LocalTIme **************************/
        Duration duration = Duration.between(time1, timeNow);
        System.out.println(duration);
        System.out.println(duration.negated());
        System.out.println(duration.toDays());

//        Duration duration2 = Duration.between(date1, now);
//        System.out.println(duration2.toDays()); // 报错了哦
        Duration duration1 = Duration.ofHours(2);
        System.out.println("当前时间后移2小时：" + timeNow.plus(duration1));

    }
}
