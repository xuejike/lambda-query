package com.github.xuejike.query.http.client;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

/**
 * @author xuejike
 * @date 2020/12/31
 */

@Slf4j
public class HuToolHttpClient implements HttpClient{
    @Override
    public String post(String url, Map<String,Object> param, String body) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        Optional.ofNullable(param).filter(CollUtil::isNotEmpty).flatMap(it->{
            it.forEach((key, value) -> urlBuilder.append(key).append("=").append(value));
            return Optional.empty();
        });
        URI uri = URLUtil.toURI(urlBuilder.toString());
        String newUrl = uri.normalize().toString();
        log.info("http POST -> {} ", newUrl);
        log.info("    body->{} ",body);
        String post = HttpUtil.post(newUrl, body);
        return post;
    }
}
