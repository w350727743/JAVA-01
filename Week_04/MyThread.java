package com.example.demo1.week05;

/**
 * @author:luzaichun
 * @Date:2021/2/6
 * @Time:13:12
 **/
public class MyThread extends Thread {

    @Override
    public void run() {
        super.run();
        System.out.println("子线程代码执行");
        Demo4.A = 100;
    }
}
