package com.github.xuejike.query.core.base;

import com.github.xuejike.query.core.criteria.DaoCriteria;
import com.github.xuejike.query.core.criteria.InjectionBaseQuery;

/**
 * @author xuejike
 * @date 2020/12/28
 */
public abstract class BaseDao<T> implements DaoCriteria<T> , InjectionBaseQuery {
    protected BaseWhereQuery baseWhereQuery;
    protected Class<T> entityCls;

    public BaseDao(Class<T> entityCls) {
        this.entityCls = entityCls;
    }

    @Override
    public DaoCriteria<T> getDao() {
        return this;
    }

    @Override
    public void injectionBaseWhereQuery(BaseWhereQuery baseWhereQuery) {
        this.baseWhereQuery = baseWhereQuery;
    }
}
