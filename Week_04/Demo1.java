package com.example.demo1.week05;

import java.util.concurrent.*;

/**
 * @author:luzaichun
 * @Date:2021/2/6
 * @Time:12:56
 **/
public class Demo1 {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(8);
        Future future = pool.submit(new MyCallable());
        Object o = null;
        try {
            o = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("返回结果="+o.toString());
        pool.shutdown();
    }

}
