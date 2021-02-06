package com.example.demo1.week05;

/**
 * @author:luzaichun
 * @Date:2021/2/6
 * @Time:13:39
 **/
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("进入子线程方法");
        Demo5.a = 100;
    }
}
