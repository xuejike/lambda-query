package com.github.xuejike.query.http;

import com.github.xuejike.query.http.client.HttpClient;
import com.github.xuejike.query.http.client.HuToolHttpClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuejike
 * @date 2020/12/31
 */
public class LambdaQueryHttpConfig {
    private String serverBasePath="/lambda/";

    private Map<String,Class<?>> serviceMap = new ConcurrentHashMap<>();
    private HttpClient httpClient = new HuToolHttpClient();
    public static LambdaQueryHttpConfig instance = new LambdaQueryHttpConfig();

    public static LambdaQueryHttpConfig getInstance() {
        return instance;
    }
    public LambdaQueryHttpConfig registerService(String serviceName,Class<?> entityCls){
        serviceMap.put(serviceName,entityCls);
        return this;
    }
    public Class<?> getServiceEntityCls(String serviceName){
        return serviceMap.get(serviceName);
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public String getServerBasePath() {
        return serverBasePath;
    }

    public void setServerBasePath(String serverBasePath) {
        this.serverBasePath = serverBasePath;
    }
}
