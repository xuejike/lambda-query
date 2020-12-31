package com.github.xuejike.query.mongo.annotation;

import com.github.xuejike.query.core.annotation.DaoSelect;
import com.github.xuejike.query.mongo.MongoDao;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xuejike
 * @date 2020/12/28
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@DaoSelect(daoCls = MongoDao.class)
public @interface MongoDaoSelect {
    String name() default "";
}
