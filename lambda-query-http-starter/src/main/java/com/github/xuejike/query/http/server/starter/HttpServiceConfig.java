package com.github.xuejike.query.http.server.starter;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.github.xuejike.query.core.po.JPage;
import com.github.xuejike.query.http.LambdaQueryHttpConfig;
import com.github.xuejike.query.http.service.HttpServiceParse;
import com.github.xuejike.query.http.vo.HttpBodyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * @author xuejike
 * @date 2021/1/4
 */
@Configuration
@Slf4j
@Import(JkQueryHttpProperties.class)
public class HttpServiceConfig {
    @Autowired
    JkQueryHttpProperties properties;
    private String lambdaPath;
    @PostConstruct
    protected void init(){
        LambdaQueryHttpConfig.getInstance().setServerBasePath(properties.getHttpServicePath());
    }
    @Bean
    public RouterFunction<ServerResponse> initServer(){
        RouterFunctions.Builder route = RouterFunctions.route();
        lambdaPath = properties.getHttpServicePath();
        route.POST(lambdaPath + "/{service}/list", serverRequest -> {
            String serviceName = serverRequest.pathVariable("service");
            HttpBodyVo body = readBody(serverRequest);
            HttpServiceParse serviceParse = new HttpServiceParse(serviceName, body);
            List list = serviceParse.list();
            return ServerResponse.accepted().body(list);
        });
        route.POST(lambdaPath + "/{service}/first", serverRequest -> {
            String serviceName = serverRequest.pathVariable("service");
            HttpBodyVo body = readBody(serverRequest);
            HttpServiceParse serviceParse = new HttpServiceParse(serviceName, body);
            Object list = serviceParse.getFirst();
            return ServerResponse.accepted().body(list);
        });
        route.POST(lambdaPath + "/{service}/count", serverRequest -> {
            String serviceName = serverRequest.pathVariable("service");
            HttpBodyVo body = readBody(serverRequest);
            HttpServiceParse serviceParse = new HttpServiceParse(serviceName, body);
            Object list = serviceParse.count();
            return ServerResponse.accepted().body(list);
        });
        route.POST(lambdaPath + "/{service}/page", serverRequest -> {
            String serviceName = serverRequest.pathVariable("service");
            HttpBodyVo body = readBody(serverRequest);
            int pageNo = Convert.toInt(serverRequest.param("pageNo").orElse("0"));
            int pageSize = Convert.toInt(serverRequest.param("pageSize").orElse("10"));
            Boolean haveTotal = serverRequest.param("haveTotal").map(it->Convert.toBool(it,false)).orElse(false);

            HttpServiceParse serviceParse = new HttpServiceParse(serviceName, body);
            Object list = serviceParse.page(new JPage(pageNo,pageSize,haveTotal));
            return ServerResponse.accepted().body(list);
        });
        route.POST(lambdaPath + "/{service}/findById", serverRequest -> {
            String serviceName = serverRequest.pathVariable("service");
            HttpBodyVo body = readBody(serverRequest);
            HttpServiceParse serviceParse = new HttpServiceParse(serviceName, body);
            String id = serverRequest.param("id").orElse(null);
            Object list = serviceParse.findById(id);
            return ServerResponse.accepted().body(list);
        });
        return route.build();
    }


    public static HttpBodyVo readBody(ServerRequest request) throws ServletException, IOException {
        String body = request.body(String.class);
        HttpBodyVo httpBodyVo = JSON.parseObject(body, HttpBodyVo.class);
        return httpBodyVo;
    }
}
