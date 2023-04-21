package com.learn.java8.lambda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ClassName:StreamTest
 * Package:com.learn.java8.lambda
 * Description:
 *
 * @Date:2023/2/1 9:54
 * @Author:qs@1.com
 */
public class StreamTest {
    public static void main(String[] args) {
        testGroupingBy();
    }

    private static void testMap() {
        List<String> ignoreTables = new ArrayList<>();
        ignoreTables.add("sys_");

        List<String> patternIgnoreTables = ignoreTables.stream().map(x -> {
            if (x.contains("*")) {
                return x.substring(0, x.indexOf("*") + 1);
            }
            // list 有元素的
            return null;
        }).collect(Collectors.toList());

        System.out.println(patternIgnoreTables.size());
    }

    private static void testFlatMap() {
        // 将一个Stream中的元素拆分成多个Stream，然后将这些Stream合并成一个Stream。
        // 具体来说，它会把一个包含多个集合的Stream转换为一个包含所有集合中元素的Stream。

        /**
         * example one
         */
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        List<Character> letters = list.stream().flatMap(x -> x.chars().mapToObj(c -> (char) c)).collect(Collectors.toList());
        System.out.println(letters);

        /**
         * example two
         */
        List<List<Integer>> seqList = new ArrayList<>();
        seqList.add(Arrays.asList(1, 2));
        seqList.add(Arrays.asList(3, 4));
        seqList.add(Arrays.asList(5, 6));
        List<Integer> seq = seqList.stream()
                .flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(seq);

        /**
         * example three
         */
        List<String> fileList = Arrays.asList("D:\\workspace\\tuling\\spring-boot-demo\\java_learn\\src\\main\\java\\com\\learn\\java8\\lambda\\1.txt",
                "D:\\workspace\\tuling\\spring-boot-demo\\java_learn\\src\\main\\java\\com\\learn\\java8\\lambda\\2.txt");
        List<String> fileContent = fileList.stream().flatMap(f -> {
            try {
                return Files.lines(Paths.get(f));
            } catch (IOException e) {
                return Stream.empty();
            }
        }).collect(Collectors.toList());
        System.out.println(fileContent);

    }

    //        List<EqualsTo> equalsTos = tables.stream()
//                .map(item -> {
//                    TableInfo tableInfo = TableInfoHelper.getTableInfo(item.getName());
//                    if (tableInfo.isWithLogicDelete()) {
//                        TableFieldInfo logicDeleteFieldInfo = tableInfo.getLogicDeleteFieldInfo();
//                        final String notDeleteValue = logicDeleteFieldInfo.getLogicNotDeleteValue();
//                        String logicDeleteColumn = logicDeleteFieldInfo.getColumn();
//                        return Optional.of(new EqualsTo(getAliasColumn(item, logicDeleteColumn), new StringValue(notDeleteValue)));
//                    }
//                    return Optional.<EqualsTo>empty();
//                })
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .collect(Collectors.toList());
    private static void testReduce() {
        /**
         * 操作数
         */
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = nums.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);
        Integer max = nums.stream().reduce(0, Integer::max);
        System.out.println(max);
        Integer min = nums.stream().reduce(0, Integer::min);
        System.out.println(min);

        /**
         * 操作字符串
         */
        List<String> strs = Arrays.asList("hello", "world");
        String content = strs.stream().reduce("", (a, b) -> a + b);
        System.out.println(content);
    }

    private static void testGroupingBy() {
        /**
         * groupingBy操作可以将Stream中的元素按照指定的分类器进行分组，返回一个Map对象
         * 其中键为分类器的返回值，值为Stream中符合该分类的元素列表
         */
        List<String> strs = Arrays.asList("hello", "world", "holy", "garden");
        Map<Character, List<String>> res = strs.stream().collect(Collectors.groupingBy(x -> x.charAt(0)));
        System.out.println(res);

        Map<Character, List<String>> res2 = strs.stream().collect(Collectors.groupingBy(x -> x.charAt(0),
                // 指定了下游收集器
                Collectors.mapping(String::toUpperCase, Collectors.toList())));
        System.out.println(res2);

        /**
         * 分区
         * partitioningBy
         */
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6);
        Map<Boolean, List<Integer>> partRes = nums.stream().collect(Collectors.partitioningBy(x -> x % 2 == 0));
        System.out.println(partRes);
        Map<Boolean, Integer> partRes2 = nums.stream().collect(Collectors.partitioningBy(x -> x % 2 == 0,
                // 下游收集器
                Collectors.summingInt(Integer::intValue)));
        System.out.println(partRes2);
    }


}
