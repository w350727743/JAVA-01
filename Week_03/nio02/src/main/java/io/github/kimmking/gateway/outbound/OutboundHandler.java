package io.github.kimmking.gateway.outbound;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author:luzaichun
 * @Date:2021/1/30
 * @Time:23:10
 **/
public interface OutboundHandler {
    void handle(FullHttpRequest fullRequest,ChannelHandlerContext ctx, HttpRequestFilter filter);
}
