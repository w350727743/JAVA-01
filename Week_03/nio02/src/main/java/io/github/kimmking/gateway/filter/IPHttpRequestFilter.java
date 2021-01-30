package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author:luzaichun
 * @Date:2021/1/30
 * @Time:7:11
 **/
@Slf4j
public class IPHttpRequestFilter implements HttpRequestFilter {

    private List<String> blackIP = Arrays.asList("127.0.0.2");



    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        HttpHeaders headers = fullRequest.headers();
        Optional<Map.Entry<String, String>> optional = headers.entries()
                .stream()
                .filter(s -> "Host".equals(s.getKey()))
                .findFirst();
        String clientIP="";
        if(optional.isPresent()){
            clientIP = optional.get().getValue().split(":")[0];
        }
        if (blackIP.contains(clientIP)) {
            log.error("黑名单用户请求。IP={}",clientIP);
            ctx.close();
            throw new RuntimeException("黑名单用户请求");
        }
    }

}
