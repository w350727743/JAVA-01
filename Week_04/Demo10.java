package com.example.demo1.week05;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author:luzaichun
 * @Date:2021/2/6
 * @Time:14:15
 **/
public class Demo10 {
    public static Integer a;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(8);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        pool.execute(() -> {
            a = 100;
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("返回结果a=" + a);

    }
}
