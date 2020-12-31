package com.github.xuejike.query.core.interceptor;

import com.github.xuejike.query.core.base.BaseNestedWhereQuery;

/**
 * @author xuejike
 * @date 2020/12/31
 */
public interface LambdaQueryInterceptor {
    /**
     * 查询拦截器
     * @param query
     */
    default void selectInterceptor(BaseNestedWhereQuery query){

    }

}
