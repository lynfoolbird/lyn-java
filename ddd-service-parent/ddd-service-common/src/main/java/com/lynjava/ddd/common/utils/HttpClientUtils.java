package com.lynjava.ddd.common.utils;

import com.lynjava.ddd.common.model.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.util.TimeValue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * HTTP请求工具类
 */
@Slf4j
public final class HttpClientUtils {
    private static CloseableHttpClient httpClient = null;

    private static CookieStore cookieStore = null;

    private static BasicCredentialsProvider basicCredentialsProvider = null;

    static {
        // 注册访问协议相关的 Socket 工厂
        SSLConnectionSocketFactory sslConnectionSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
        try {
            // 忽略证书校验
            sslConnectionSocketFactory = SSLConnectionSocketFactoryBuilder.create()
                    .setSslContext(SSLContextBuilder.create().loadTrustMaterial(TrustAllStrategy.INSTANCE).build())
                    .setHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
        } catch (Exception e) {
            log.error("exception:", e);
        }
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslConnectionSocketFactory)
                .build();
        // Http 连接池
//        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = PoolingHttpClientConnectionManagerBuilder.create()
//                .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create().setSslContext(SSLContextBuilder.create()
//                        .loadTrustMaterial(TrustAllStrategy.INSTANCE).build()).setHostnameVerifier(NoopHostnameVerifier.INSTANCE).build())
//                .build();
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
        poolingHttpClientConnectionManager.setDefaultSocketConfig(SocketConfig.custom()
                .setSoTimeout(30, TimeUnit.SECONDS)
                .setTcpNoDelay(true).build()
        );
        poolingHttpClientConnectionManager.setMaxTotal(200);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(200);
        poolingHttpClientConnectionManager.setValidateAfterInactivity(TimeValue.ofMinutes(5));
        // Http 请求配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000, TimeUnit.MILLISECONDS)
                .setResponseTimeout(5000, TimeUnit.MILLISECONDS)
                .setConnectionRequestTimeout(5000, TimeUnit.MILLISECONDS)
                .build();
        // 设置 Cookie
        cookieStore = new BasicCookieStore();
        // 设置 Basic Auth 对象
        basicCredentialsProvider = new BasicCredentialsProvider();
        // 创建监听器，在 JVM 停止或重启时，关闭连接池释放掉连接
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                log.info("执行关闭 HttpClient");
                httpClient.close();
                log.info("已经关闭 HttpClient");
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }));
        // 创建 HttpClient 对象
        httpClient = HttpClients.custom()
                // 设置 Cookie
                .setDefaultCookieStore(cookieStore)
                // 设置 Basic Auth
                .setDefaultCredentialsProvider(basicCredentialsProvider)
                // 设置 HttpClient 请求参数
                .setDefaultRequestConfig(requestConfig)
                // 设置连接池
                .setConnectionManager(poolingHttpClientConnectionManager)
                // 设置定时清理连接池中过期的连接
                .evictExpiredConnections()
                .evictIdleConnections(TimeValue.ofMinutes(3))
                .build();
    }

   private HttpClientUtils(){}

   public static HttpResult doGet(String url, Map<String, Object> headers) {
       // 创建 HttpGet 对象
       HttpGet httpGet = new HttpGet(url);
       // 请求头
       headers.entrySet().stream().forEach(
               entry -> httpGet.addHeader(entry.getKey(), entry.getValue())
       );
       try (CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity()) {
           // 响应码
           int code = response.getCode();
           // 响应体
           String data = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
           return HttpResult.builder().success(Boolean.TRUE)
                   .code(code)
                   .data(data)
                   .build();
       } catch (IOException | ParseException e) {
           e.printStackTrace();
           return HttpResult.builder().success(Boolean.FALSE)
                   .code(500)
                   .message(e.getMessage())
                   .build();
       }
    }

    // POST请求  json
    public static HttpResult doPost(String url, Map<String, Object> headers, String json) {
        // 创建 HttpPost 对象
        HttpPost httpPost = new HttpPost(url);
        // 设置 请求头
        httpPost.addHeader("Content-Type", ContentType.APPLICATION_JSON);
        headers.entrySet().stream().forEach(
                entry -> httpPost.addHeader(entry.getKey(), entry.getValue())
        );
        // 设置请求体
        StringEntity stringEntity = new StringEntity(json, StandardCharsets.UTF_8);
        httpPost.setEntity(stringEntity);
        System.out.println("requestUrl:" + url + ",headers:" + headers + ",body:"+ json);
        try (CloseableHttpResponse response = httpClient.execute(httpPost);
             HttpEntity entity = response.getEntity()
        ) {
            // 响应码
            int code = response.getCode();
            // 响应体
            String data = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            // 销毁流
            EntityUtils.consume(stringEntity);
            System.out.println("result is " + response.toString());
            return HttpResult.builder().success(Boolean.TRUE)
                    .code(code)
                    .data(data)
                    .build();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return HttpResult.builder().success(Boolean.FALSE)
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }

    public static String doPut(){
        return "doPut";
    }

    public static String doPatch(){
        return "doPatch";
    }
    public static String doDelete(){
        return "doDelete";
    }
}
