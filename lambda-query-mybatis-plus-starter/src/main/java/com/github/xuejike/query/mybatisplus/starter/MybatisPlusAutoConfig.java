package com.github.xuejike.query.mybatisplus.starter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xuejike.query.mybatisplus.MyBatisPlusDaoFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

/**
 * @author xuejike
 * @date 2020/12/31
 */
@Configuration
public class MybatisPlusAutoConfig {
    @Bean
    public MyBatisPlusDaoFactory myBatisPlusDaoFactory(Collection<BaseMapper> mapperCollection){
        MyBatisPlusDaoFactory myBatisPlusDaoFactory = new MyBatisPlusDaoFactory(mapperCollection);
        return myBatisPlusDaoFactory;
    }
}
