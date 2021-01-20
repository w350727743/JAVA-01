package com.example.demo1.week02;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * @author:luzaichun
 * @Date:2021/1/20
 * @Time:23:17
 **/
public class HttpClientDemo {
    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8081/test");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            byte[] bytes = IOUtils.toByteArray(response.getEntity().getContent());
            System.out.println("返回：" + new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
