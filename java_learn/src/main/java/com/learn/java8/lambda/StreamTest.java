package com.learn.java8.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
         testMap();
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



}
