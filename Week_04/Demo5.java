package com.example.demo1.week05;

import java.util.Objects;

/**
 * @author:luzaichun
 * @Date:2021/2/6
 * @Time:13:12
 **/
public class Demo5 {
    public static Integer a;
    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();
        new Thread(runnable).start();
        while (Objects.isNull(a)){

        }
        System.out.println("返回结果a="+a);
    }
}
