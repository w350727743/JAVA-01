package com.example.demo1.test;


import org.apache.commons.io.IOUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author:luzaichun
 * @Date:2021/1/13
 * @Time:23:22
 **/
public class MyClassLoader extends ClassLoader {


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("C:\\GitRepository\\demo1\\src\\main\\java\\com\\example\\demo1\\test\\Hello.xlass");
            byte[] bytes = IOUtils.toByteArray(fis);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) ((byte) 255 - bytes[i]);
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.loadClass(name);

    }

    public static void main(String[] args) {
        try {
            Class<?> clazz = new MyClassLoader().findClass("Hello");
            Object o = clazz.newInstance();
            Method hello = clazz.getMethod("hello", null);
            hello.invoke(o, null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
