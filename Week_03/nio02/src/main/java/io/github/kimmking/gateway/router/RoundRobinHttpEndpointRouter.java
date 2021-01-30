package io.github.kimmking.gateway.router;

import java.util.List;

/**
 * @author:luzaichun
 * @Date:2021/1/27
 * @Time:19:41
 **/
public class RoundRobinHttpEndpointRouter implements HttpEndpointRouter {
    private int currentIndex = -1;
    @Override
    public String route(List<String> urls) {
        synchronized (RoundRobinHttpEndpointRouter.class){
            currentIndex++;
            //超过了url数量索引归0
            if (currentIndex>(urls.size()-1)){
                currentIndex=0;
            }
        }
        return urls.get(currentIndex);
    }
}
