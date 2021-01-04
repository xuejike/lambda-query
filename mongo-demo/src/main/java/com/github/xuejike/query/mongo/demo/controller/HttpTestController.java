package com.github.xuejike.query.mongo.demo.controller;

import com.github.xuejike.query.core.JkLambdaQuery;
import com.github.xuejike.query.core.JkQuerys;
import com.github.xuejike.query.http.LambdaQueryHttpConfig;
import com.github.xuejike.query.http.client.HttpClientFactory;
import com.github.xuejike.query.http.server.starter.HttpServiceConfig;
import com.github.xuejike.query.mongo.demo.http.HttpEntity;
import com.github.xuejike.query.mongo.demo.mybatis.entity.U1;
import com.github.xuejike.query.mongo.demo.mybatis.entity.U2;
import com.github.xuejike.query.mongo.demo.vo.U1Vo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author xuejike
 * @date 2021/1/4
 */
@RestController
@RequestMapping("/")
public class HttpTestController {
    static {

    }
    @PostConstruct
    public void init(){
        LambdaQueryHttpConfig.getInstance().registerService("u1", U1.class);
        HttpClientFactory httpClientFactory = new HttpClientFactory();
    }
    @GetMapping("list")
    public Object testList(){
        JkLambdaQuery<HttpEntity> query = JkQuerys.lambdaQuery(HttpEntity.class);
        Object list = query.or().eq(HttpEntity::getName,"name1").or()
                .eq(HttpEntity::getName,"name2")
                .loadRef(HttpEntity::getU2Id, U2.class,U2::getId).map(U1Vo.class).list();
        return list;
    }
}
