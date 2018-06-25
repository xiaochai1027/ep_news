package com.jdk8;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

/**
 * author fangchen
 * date  2018/4/24 下午5:27
 */
public class Lambda {

    public static void main(String[] args) {
//        Runnable runnable = () -> System.out.println("hello");
//        Thread d = new Thread(runnable);
//        d.start();

//        BinaryOperator<Integer> o = (x, y) -> x + y;
//        System.out.println(o.apply(1,2));

//        List<String> strs = new ArrayList<>();
//        strs.add("1");
//        strs.add("2");
//        strs.add("3");
//        strs.add("1");
//        System.out.println(strs.stream().filter(str -> str.equals("2")).count());
        List<String> list = new ArrayList<>();
        list.add("3");
        list.add("2");
        list.add("1");
        TreeSet set = list.stream().sorted((y,x) -> y.compareTo(x) ).collect(Collectors.toCollection(TreeSet::new));
//        System.out.println(list);
//        System.out.println(set);


//        System.out.println(method(list.stream()));

        System.out.println(joining(list.stream()));


    }

    public static Map<String, List<Integer>> method(Stream<Integer> stringStream) {
        return stringStream.collect(groupingBy(str -> {
            if (str.equals(1)) {
                return "只是存1";
            } else {
                return "只是存2/3";
            }
        }));
    }

    public static String joining(Stream<String> list) {
        return list.map(String::toString).collect(Collectors.joining(",", "[", "]"));
    }
}
