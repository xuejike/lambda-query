package com.github.xuejike.query.core.base;

import cn.hutool.core.collection.ListUtil;
import com.github.xuejike.query.core.tool.lambda.FieldFunction;

import java.util.List;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class BaseQuery<T> extends BaseNestedWhereQuery<T, FieldFunction<T,?>,BaseQuery<T>>{
    private Class<T> cls;

    public BaseQuery(Class<T> cls) {
        this.cls = cls;
    }
    public List<T> findAll(){
        return ListUtil.empty();
    }
}
