package com.example.demo1.week05;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:luzaichun
 * @Date:2021/2/6
 * @Time:13:45
 **/
public class Demo7 {
    public static Integer a;
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(8);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        pool.execute(() -> {
            a = 100;
            countDownLatch.countDown();
        });
        countDownLatch.await();
        System.out.println("返回结果a="+a);
    }
}
