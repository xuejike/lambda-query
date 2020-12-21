package com.github.xuejike.query.core;

import com.github.xuejike.query.core.base.BaseNestedWhereQuery;
import com.github.xuejike.query.core.criteria.DaoCriteria;
import com.github.xuejike.query.core.criteria.InjectionBaseQuery;
import com.github.xuejike.query.core.tool.lambda.FieldFunction;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class JkLambdaQuery<T> extends BaseNestedWhereQuery<T, FieldFunction<T,?>, JkLambdaQuery<T>> implements DaoCriteria<T> {
    private DaoCriteria<T> daoCriteria;

    public JkLambdaQuery(DaoCriteria<T> daoCriteria) {
        this.daoCriteria = daoCriteria;
        if (daoCriteria instanceof InjectionBaseQuery){
            ((InjectionBaseQuery) daoCriteria).injectionBaseWhereQuery(this);
        }
    }

    @Override
    public DaoCriteria<T> getDao() {
        return daoCriteria;
    }

}
