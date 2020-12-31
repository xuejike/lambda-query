package com.github.xuejike.query.core.criteria;

import com.github.xuejike.query.core.enums.LoadRefMode;
import com.github.xuejike.query.core.tool.lambda.FieldFunction;

/**
 * @author xuejike
 * @date 2020/12/31
 */
public interface LoadRefCriteria<F,R>{
    <X> R loadRef(F refField, Class<X> entityCls, FieldFunction<X,?> targetField, LoadRefMode mode);
    default<X> R loadRef(F refField, Class<X> entityCls, FieldFunction<X,?> targetField){
        return loadRef(refField, entityCls,targetField,LoadRefMode.noCache);
    }

}
