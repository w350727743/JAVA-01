package com.example.demo1.week05;

import java.util.concurrent.Callable;

/**
 * @author:luzaichun
 * @Date:2021/2/6
 * @Time:13:01
 **/
public class MyCallable implements Callable {
    @Override
    public Object call() throws Exception {
        System.out.println("into call method");
        return 100;
    }

}
