package com.example.demo1.week05;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author:luzaichun
 * @Date:2021/2/6
 * @Time:13:45
 **/
public class Demo9 {
    public static Integer a;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture.runAsync(() -> {
            a = 100;
        });
        while (Objects.isNull(a)) {
            System.out.println("循环等待。。。");
        }
        System.out.println("返回结果a=" + a);
    }
}
