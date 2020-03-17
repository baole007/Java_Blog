package com.baol.project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Test {
    //Lambda 表达式
    public static void main(String[] args) {
        Date date = new Date();
        // java 8新函数  2020-01-01
        LocalDate date1 = LocalDate.now();
        //不会更改date1  只会新创建一个

        date1.plusDays(1);
        // java 8新函数  2020-01-01T12:01:01
        LocalDateTime dateTime = LocalDateTime.now();


        //以前实现Thread方法 中的抽象方法
        //启动一个线程执行任务
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread");
            }
        }).start();
        //用Lambda表达式之后
        new Thread(()->{System.out.println("Thread");}).start();

        List<String> strs = Arrays.asList("bbbbbb","aaaa","vvvvvvvvvv","f");
        Collections.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length()-o2.length();
            }
        });

        //用Lambda表达式   特点
        // 1.参数类型自动推断  下面没有声明 a  b什么类型 自动推断成string 类型
        // 2.代码量少 简洁

        Collections.sort(strs,(a,b)->a.length()-b.length());
        System.out.println(strs);

        //3.更容易并行
        // 下面要说的就是  函数式接口 ：只有一个抽象方法(Object的方法除外)的接口 是函数式接口



    }

}
