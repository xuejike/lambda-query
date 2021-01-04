package com.github.xuejike.query.http.client;

import java.util.Map;

/**
 * @author xuejike
 * @date 2020/12/31
 */
public interface HttpClient {
    String post(String url, Map<String,Object> param, String body);
}
