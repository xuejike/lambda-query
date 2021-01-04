package com.github.xuejike.query.http.annotation;

import com.github.xuejike.query.core.annotation.DaoSelect;
import com.github.xuejike.query.http.client.HttpClientDao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xuejike
 * @date 2020/12/31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@DaoSelect(daoCls = HttpClientDao.class)
public @interface HttpDaoSelect {
    String path() default "";
    String serverAddress() default "";

}
