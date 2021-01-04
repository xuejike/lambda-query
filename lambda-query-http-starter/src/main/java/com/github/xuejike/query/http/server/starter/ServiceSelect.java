package com.github.xuejike.query.http.server.starter;

import cn.hutool.core.util.URLUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.ServiceInstanceChooser;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author xuejike
 * @date 2020/12/25
 */
@Slf4j
public class ServiceSelect {
    @Autowired
    ServiceInstanceChooser loadBalancer;

    public URI selectUrl(String authUrl) {
        URI uri = URLUtil.toURI(authUrl);
        if ("lb".equals(uri.getScheme())){
            ServiceInstance choose = loadBalancer.choose(uri.getHost());
            if (choose == null){
                log.error("");
                return null;
            }else{
                uri = UriComponentsBuilder.fromUri(uri)
                        .scheme("http")
                        .host(choose.getHost())
                        .port(choose.getPort()).build().toUri();
            }
        }
        return uri;
    }
}
