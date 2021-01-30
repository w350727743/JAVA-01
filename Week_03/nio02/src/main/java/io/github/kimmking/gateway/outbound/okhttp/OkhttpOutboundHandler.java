package io.github.kimmking.gateway.outbound.okhttp;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.outbound.OutboundHandler;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.github.kimmking.gateway.router.RoundRobinHttpEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpUtil;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class OkhttpOutboundHandler implements OutboundHandler {

    private List<String> urls;
    private HttpEndpointRouter router;
    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8,
            10,
            5000,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(1024),
            r -> {
                Thread thread = new Thread();
                thread.setName("OkHttpOutboundHandler");
                return thread;
            });

    public OkhttpOutboundHandler(List<String> urls) {
        this.urls = urls;
        this.router = new RoundRobinHttpEndpointRouter();
    }


    @Override
    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx, HttpRequestFilter filter) {
        //走路由策略
        String requestUrl = router.route(urls) + fullRequest.uri();
        //走过滤规则
        filter.filter(fullRequest, ctx);
        //请求后台
        //sendRquestBack(fullRequest, ctx, requestUrl);
        threadPoolExecutor.submit(() -> sendRquestBack(fullRequest, ctx, requestUrl));
    }

    private void sendRquestBack(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        DefaultFullHttpResponse response= null;
        try {
            Response req = call.execute();
            String str = req.body().string();
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(str.getBytes()));
            response.headers().set("Content-Type", "text/plain;charset=UTF-8");
            response.headers().setInt("Content-Length",str.length());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }

    }
}
