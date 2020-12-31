package com.github.xuejike.query.core;

import com.github.xuejike.query.core.base.BaseNestedWhereQuery;
import com.github.xuejike.query.core.criteria.DaoCriteria;
import com.github.xuejike.query.core.criteria.InjectionBaseQuery;
import com.github.xuejike.query.core.criteria.OrderCriteria;
import com.github.xuejike.query.core.enums.OrderType;
import com.github.xuejike.query.core.po.FieldInfo;
import com.github.xuejike.query.core.tool.lambda.FieldFunction;
import com.github.xuejike.query.core.tool.lambda.LambdaTool;

import java.util.Arrays;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class JkLambdaQuery<T> extends BaseNestedWhereQuery<T, FieldFunction<T,?>, JkLambdaQuery<T>> implements DaoCriteria<T> , OrderCriteria<FieldFunction<T,?>, JkLambdaQuery<T>> {
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


    @Override
    public JkLambdaQuery<T> orderAsc(FieldFunction<T, ?>... fields) {
        Arrays.stream(fields).map(LambdaTool::getFieldInfo).forEach(it->{
            orderMap.put(it, OrderType.asc);
        });

        return returnObj;
    }

    @Override
    public JkLambdaQuery<T> orderDesc(FieldFunction<T, ?>... fields) {
        Arrays.stream(fields).map(LambdaTool::getFieldInfo).forEach(it->{
            orderMap.put(it, OrderType.desc);
        });

        return returnObj;
    }
}
