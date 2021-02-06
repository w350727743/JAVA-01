package com.example.demo1.week05;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author:luzaichun
 * @Date:2021/2/6
 * @Time:13:04
 **/
public class Demo2 {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(8);
        Future<Integer> future = pool.submit(() -> {
            System.out.println("进入线程方法");
            return 100;
        });
        Integer result = null;
        try {
            result = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("返回结果=" + result);
        pool.shutdown();
    }
}
