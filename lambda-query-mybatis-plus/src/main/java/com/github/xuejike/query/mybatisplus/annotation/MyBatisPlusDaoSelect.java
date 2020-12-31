package com.github.xuejike.query.mybatisplus.annotation;

import com.github.xuejike.query.core.annotation.DaoSelect;
import com.github.xuejike.query.mybatisplus.MyBatisPlusDao;

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
@DaoSelect(daoCls = MyBatisPlusDao.class)
public @interface MyBatisPlusDaoSelect {


}
