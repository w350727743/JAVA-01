package com.example.demo1.week05;

import java.util.Objects;

/**
 * @author:luzaichun
 * @Date:2021/2/6
 * @Time:13:12
 **/
public class Demo4 {
    public static Integer A;
    public static void main(String[] args) {

        MyThread myThread = new MyThread();
        myThread.start();
        while (Objects.isNull(A)){
        }
        System.out.println("返回结果a="+A);
    }
}
