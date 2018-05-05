package org.lemon.yhj.http;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * HTTP异步请求池
 */
@Service
public class HttpAsyncClientPool implements InitializingBean, DisposableBean {

    private static final Logger log               = LoggerFactory.getLogger(HttpAsyncClientPool.class);

    /**
     * 连接超时
     */
    public static final int          CONNECT_TIMEOUT   = 30 * 1000;

    /**
     * 长链接空闲时间
     */
    public static final int          KEEPALIVE_TIMEOUT = 30 * 1000;

    /**
     * 处理超时
     */
    public static final int          SOCKET_TIMEOUT    = 30 * 1000;

    /**
     * 最大总连接值
     */
    public static final int          MAX_CONNECT       = 5000;

    /**
     * 每个路由最大连接{并发}值
     */
    public static final int          MAX_ROUTE_CONNECT = 200;

    /**当前连接数*/
    private AtomicInteger nowConnect;

    private CloseableHttpAsyncClient httpClient;

    /**
     * 执行HTTP异步请求
     * 
     * @param request
     * @param callback
     */
    public void execute(HttpUriRequest request, FutureCallback<HttpResponse> callback) {
        if (!httpClient.isRunning()) {
            start();
        }
        httpClient.execute(request, new CallbackProcesser(callback));
        nowConnect.addAndGet(1);
    }

    /***
         * @Description: decrNowConnect 减少当前连接数信号量
         *
         * @Param: []
         * @Return: int
         * @throws:
         * @author: youhaijun
         * @Date:   2017/8/16
         */
    public int decrNowConnect()
    {
        return nowConnect.decrementAndGet();
    }

    /**
     * 提交HTTP异步请求
     * 
     * @param request
     * @param callback
     */
    public void submit(final HttpUriRequest request, final FutureCallback<HttpResponse> callback) {
        execute(request, callback);
    }

    /**
     * 自定义回调
     */
    private class CallbackProcesser implements FutureCallback<HttpResponse> {

        private FutureCallback<HttpResponse> callback;

        public CallbackProcesser( FutureCallback<HttpResponse> callback) {
            this.callback = callback;
        }

        @Override
        public void completed(HttpResponse result) {
                callback.completed(result);
        }

        @Override
        public void failed(Exception ex) {
                callback.failed(ex);
        }

        @Override
        public void cancelled() {
                callback.cancelled();
        }

    }

    /**
     * 销毁关闭
     */
    @Override
    public void destroy() throws Exception {
        if (httpClient != null && httpClient.isRunning()) {
            httpClient.close();
            log.info("HttpAsyncClientPool closed");
        }
    }

    /**
     * 初始化启动
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        start();
        nowConnect = new AtomicInteger(0);
    }

    /**
     * 启动HTTP服务
     */
    private synchronized void start() {
        if (httpClient != null && httpClient.isRunning()) {
            return;
        }
        RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpClient = HttpAsyncClients.custom().setDefaultRequestConfig(config).setMaxConnTotal(MAX_CONNECT).setMaxConnPerRoute(MAX_ROUTE_CONNECT).setKeepAliveStrategy(getKeepAliveStrategy()).build();
        httpClient.start();
        log.info("HttpAsyncClientPool started");
    }

    /**
     * 长链接空闲时间
     */
    private DefaultConnectionKeepAliveStrategy getKeepAliveStrategy() {
        return new DefaultConnectionKeepAliveStrategy() {

            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                long duration = super.getKeepAliveDuration(response, context);
                if (duration == -1) {
                    return KEEPALIVE_TIMEOUT;
                }
                return duration;
            }
        };
    }

}
