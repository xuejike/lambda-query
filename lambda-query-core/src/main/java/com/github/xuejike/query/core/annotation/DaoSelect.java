package com.github.xuejike.query.core.annotation;

import com.github.xuejike.query.core.criteria.DaoCriteria;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xuejike
 * @date 2020/12/18
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DaoSelect {
    Class<? extends DaoCriteria> daoCls();

}
