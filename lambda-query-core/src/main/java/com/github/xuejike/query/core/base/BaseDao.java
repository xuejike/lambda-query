package com.github.xuejike.query.core.base;

import cn.hutool.core.collection.CollUtil;
import com.github.xuejike.query.core.criteria.DaoCriteria;
import com.github.xuejike.query.core.criteria.InjectionBaseQuery;
import com.github.xuejike.query.core.enums.OrderType;
import com.github.xuejike.query.core.po.BaseConditionsVo;
import com.github.xuejike.query.core.po.FieldInfo;
import com.github.xuejike.query.core.po.LoadRefInfo;
import com.github.xuejike.query.core.po.QueryInfo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuejike
 * @date 2020/12/28
 */
public abstract class BaseDao<T,Q> implements DaoCriteria<T> , InjectionBaseQuery {
    protected BaseWhereQuery baseWhereQuery;
    protected Class<T> entityCls;
    protected BaseConditionsVo baseConditionsVo;

    public BaseDao(Class<T> entityCls) {
        this.entityCls = entityCls;
    }


    /**
     * 构建查询器
     * @return
     */
    public abstract Q buildQuery();

    public void setBaseConditionsVo(BaseConditionsVo baseConditionsVo) {
        this.baseConditionsVo = baseConditionsVo;
    }

    @Override
    public DaoCriteria<T> getDao() {
        return this;
    }

    @Override
    public void injectionBaseWhereQuery(BaseWhereQuery baseWhereQuery) {
        this.baseWhereQuery = baseWhereQuery;
    }


    public Map<FieldInfo, LoadRefInfo<?>> getRefClassMap() {
        HashMap<FieldInfo, LoadRefInfo<?>> map = new HashMap<>();
        map.putAll(baseWhereQuery.getRefClassMap());
        if (baseConditionsVo!=null && CollUtil.isNotEmpty(baseConditionsVo.getRefClassMap())){
            map.putAll(baseConditionsVo.getRefClassMap());
        }
        return map;
    }
}
