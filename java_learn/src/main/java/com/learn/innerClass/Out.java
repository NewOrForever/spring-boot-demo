package com.learn.innerClass;

import com.learn.Child;
import com.learn.Parent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

/**
 * ClassName:Out
 * Package:com.learn.innerClass
 * Description:
 *
 * @Date:2021/12/28 16:58
 * @Author:qs@1.com
 */
public class Out {

    public Out() {
        System.out.println("this is out");
    }

    public class Inner {
        private String str;
        public Inner() {
            System.out.println("this is inner");
        }
        public Inner(@Lazy String str){
            this.str = str;
        }
    }

    public static class StaticInner {
        public StaticInner() {
            System.out.println("this is StaticInner");
        }
    }


    public static void main(String[] args) {
        Out out = new Out();
        Inner inner = out.new Inner();
        StaticInner staticInner = new StaticInner();

        try {
            // 获取内部类构造方法或实例化时都要传入父类
            Constructor<Inner> constructor = Inner.class.getDeclaredConstructor(Out.class, String.class);
            Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
            int len = parameterAnnotations.length;
            System.out.println("len=" + len);
            int parameterCount = constructor.getParameterCount(); // 1
            System.out.println("parameterCount=" + parameterCount); // 2：将父类这个参数算进去了

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
