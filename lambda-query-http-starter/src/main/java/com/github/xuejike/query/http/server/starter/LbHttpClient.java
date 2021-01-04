package com.github.xuejike.query.http.server.starter;

import cn.hutool.core.collection.CollUtil;
import com.github.xuejike.query.http.client.HuToolHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

/**
 * @author xuejike
 * @date 2021/1/4
 */

public class LbHttpClient extends HuToolHttpClient {
    ServiceSelect serviceSelect;

    public LbHttpClient(ServiceSelect serviceSelect) {
        this.serviceSelect = serviceSelect;
    }

    @Override
    public String post(String url, Map<String, Object> param, String body) {


        URI uri = serviceSelect.selectUrl(url);
        return super.post(uri.toString(), param, body);
    }
}
