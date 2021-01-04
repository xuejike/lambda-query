package com.github.xuejike.query.http.client;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Map;

/**
 * @author xuejike
 * @date 2020/12/31
 */

@Slf4j
public class HuToolHttpClient implements HttpClient{
    @Override
    public String post(String url, Map<String,Object> param, String body) {
        URI uri = URLUtil.toURI(url);
        String newUrl = uri.normalize().toString();
        log.info("http -> {} ", newUrl);
        log.info("    param->{} ", JSON.toJSONString(param));
        log.info("    body->{} ",body);
        String post = HttpUtil.post(newUrl, body);
        return post;
    }
}
