package com.github.xuejike.query.mongo.starter;

import com.github.xuejike.query.mongo.MongoDaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author xuejike
 * @date 2020/12/31
 */
@Configuration
public class MongoQueryAutoConfig {
    @Bean
    public MongoDaoFactory mongoDaoFactory(MongoTemplate mongoTemplate){
        return new MongoDaoFactory(mongoTemplate);
    }
}
