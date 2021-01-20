package com.example.demo1.week02;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author:luzaichun
 * @Date:2021/1/20
 * @Time:23:18
 **/
public class OkHttpDemo {
    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url("http://127.0.0.1:8081/test")
                .build();
        Call call = okHttpClient.newCall(request);
        String result = null;
        try {
            Response reponse = call.execute();
            result = reponse.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("返回：" + result);
    }
}
