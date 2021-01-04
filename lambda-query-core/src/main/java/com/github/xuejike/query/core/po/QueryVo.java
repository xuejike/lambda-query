package com.github.xuejike.query.core.po;

import com.github.xuejike.query.core.enums.OrderType;
import com.github.xuejike.query.core.enums.WhereOperation;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuejike
 * @date 2020/12/31
 */
public class QueryVo {
    protected QueryInfo where;
    protected List<FieldInfo> selectList = new LinkedList<>();
    protected List<FieldInfo> excludeList = new LinkedList<>();
    protected Map<FieldInfo, OrderType> orderMap = new ConcurrentHashMap<>();
    protected Map<FieldInfo, LoadRefInfo<?>> refClassMap = new ConcurrentHashMap<>();

    public QueryInfo getWhere() {
        return where;
    }

    public void setWhere(QueryInfo where) {
        this.where = where;
    }

    public List<FieldInfo> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<FieldInfo> selectList) {
        this.selectList = selectList;
    }

    public List<FieldInfo> getExcludeList() {
        return excludeList;
    }

    public void setExcludeList(List<FieldInfo> excludeList) {
        this.excludeList = excludeList;
    }

    public Map<FieldInfo, OrderType> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map<FieldInfo, OrderType> orderMap) {
        this.orderMap = orderMap;
    }

    public Map<FieldInfo, LoadRefInfo<?>> getRefClassMap() {
        return refClassMap;
    }

    public void setRefClassMap(Map<FieldInfo, LoadRefInfo<?>> refClassMap) {
        this.refClassMap = refClassMap;
    }
}
