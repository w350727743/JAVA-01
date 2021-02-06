package com.example.demo1.week05;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author:luzaichun
 * @Date:2021/2/6
 * @Time:13:42
 **/
public class Demo6 {
    public static Integer a;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(8);
        pool.execute(() -> {
            a = 100;
        });
        while (Objects.isNull(a)) {
            System.out.println("循环等待。。。。");
        }
        System.out.println("返回结果a=" + a);
    }
}
