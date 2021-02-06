package com.example.demo1.week05;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:luzaichun
 * @Date:2021/2/6
 * @Time:13:45
 **/
public class Demo8 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        Integer a = completableFuture.get();
        System.out.println("返回结果a=" + a);
    }
}
