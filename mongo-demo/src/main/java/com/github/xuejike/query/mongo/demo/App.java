package com.github.xuejike.query.mongo.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author xuejike
 * @date 2020/12/21
 */
@SpringBootApplication
@MapperScan("com.github.xuejike.query.mongo.demo.mybatis.mapper")
@ComponentScan(basePackages={"cn.hutool.extra.spring","com.github.xuejike.query.mongo.demo"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
