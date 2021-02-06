package com.example.demo1.week05;

/**
 * @author:luzaichun
 * @Date:2021/2/6
 * @Time:13:08
 **/
public class Demo3 {
    private static Integer a =0;
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            a=100;
        }).start();
        Thread.sleep(100);
        if (a==100){
            System.out.println("返回结果a="+a);
        }
    }
}
