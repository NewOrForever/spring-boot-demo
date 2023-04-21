package com.learn.java8.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * ClassName:FunctionTest
 * Package:com.learn.java8.lambda
 * Description:
 *
 * @Date:2023/4/20 16:48
 * @Author:qs@1.com
 */
public class FunctionTest {
    public static void main(String[] args) {
        testConsumer();
        testPredicate();
        testFunction();
        testBiFunction();
    }

    private static void testConsumer() {
        Consumer<String> consumer1 = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s + ", consumer1");
            }
        };
        Consumer<String> consumer2 = s -> System.out.println(s + ", this is consumer2");
        // andthen 其实又重新生成了一个 Consumner，然后执行 accept -》 consumer1.accept(s); consumer2.accept(s);
        consumer1.andThen(consumer2).accept("sss");
    }

    private static void testPredicate() {
        Predicate<Integer> predicate = new Predicate<Integer>() {
            @Override
            public boolean test(Integer i) {
                return i == 1;
            }
        };
        Predicate<Integer> predicateAnd = i -> i == 2;

        System.out.println(predicate.and(predicateAnd).test(1));
        System.out.println(predicate.or(predicateAnd).test(1));
        System.out.println(predicate.test(1));
        System.out.println(predicate.negate().test(1));
        System.out.println(Predicate.isEqual("a").test("a"));
        System.out.println(Predicate.isEqual("a").test("b"));
    }

    private static void testFunction() {
        Function<Character, Integer> function = new Function<Character, Integer>() {
            @Override
            public Integer apply(Character character) {
                return (int) character;
            }
        };
        Function<Integer, Integer> function1 = c -> c + 1000;
        System.out.println(function.apply('a'));
        // function执行结果作为参数传入 function1
        System.out.println(function.andThen(function1).apply('a'));

        Function<Character, Integer> function2 = c -> (int) c;
        System.out.println(function2.apply('b'));

        // 先执行before 再执行first function
        Function<Integer, Character> before = t -> (char) t.intValue();
        System.out.println(function.compose(before).apply(99));

        // 将参数原样输出
        Function<Object, Object> identity = Function.identity();
        System.out.println(identity.apply("aaaaaa"));
    }

    private static void testBiFunction() {
        BiFunction<String, String, String> function = new BiFunction<String, String, String>() {
            @Override
            public String apply(String t1, String t2) {
                return t1 + t2;
            }
        };
        System.out.println(function.apply("hello", "world"));

        Function<String, String> function1 = t -> t + "function";
        System.out.println(function.andThen(function1).apply("hello", "world"));

        BiFunction<String, String, String> function2 = (t1, t2) -> t1 + t2;
        System.out.println(function2.apply("hi, ", "bifunction"));

    }
}
