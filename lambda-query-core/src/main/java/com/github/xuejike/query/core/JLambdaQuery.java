package com.github.xuejike.query.core;

import com.github.xuejike.query.core.base.BaseDao;
import com.github.xuejike.query.core.base.BaseNestedWhereQuery;
import com.github.xuejike.query.core.base.MapDao;
import com.github.xuejike.query.core.criteria.*;
import com.github.xuejike.query.core.enums.OrderType;
import com.github.xuejike.query.core.tool.lambda.FieldFunction;
import com.github.xuejike.query.core.tool.lambda.LambdaTool;

import java.util.Arrays;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class JLambdaQuery<T> extends BaseNestedWhereQuery<T, FieldFunction<T,?>, JLambdaQuery<T>> implements DaoCriteria<T> , OrderCriteria<FieldFunction<T,?>, JLambdaQuery<T>>, MapCriteria {
    private BaseDao<T> daoCriteria;

    public JLambdaQuery(BaseDao<T> daoCriteria) {
        this.daoCriteria = daoCriteria;
        if (daoCriteria != null){
            ((InjectionBaseQuery) daoCriteria).injectionBaseWhereQuery(this);
        }
    }

    @Override
    public DaoCriteria<T> getDao() {
        return daoCriteria;
    }


    @Override
    public JLambdaQuery<T> orderAsc(FieldFunction<T, ?>... fields) {
        Arrays.stream(fields).map(LambdaTool::getFieldInfo).forEach(it->{
            orderMap.put(it, OrderType.asc);
        });

        return returnObj;
    }

    @Override
    public JLambdaQuery<T> orderDesc(FieldFunction<T, ?>... fields) {
        Arrays.stream(fields).map(LambdaTool::getFieldInfo).forEach(it->{
            orderMap.put(it, OrderType.desc);
        });

        return returnObj;
    }

    @Override
    public <M> SelectDaoCriteria<M> map(Class<M> mapCls) {
        return new MapDao<T,M>(daoCriteria,mapCls);
    }
}
