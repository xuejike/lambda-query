package com.github.xuejike.query.mongo;

import com.github.xuejike.query.core.JkLambdaQuery;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author xuejike
 * @date 2020/12/21
 */
public class MongoQuerys {
    public static<T> JkLambdaQuery<T> lambda(MongoTemplate mongoTemplate, Class<T> cls){
        MongoDao<T> mongoDao = new MongoDao<>(mongoTemplate, cls);
        return new JkLambdaQuery<>(mongoDao);
    }
}
