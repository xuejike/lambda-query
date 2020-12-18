package com.github.xuejike.query.core.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.log.Log;
import com.github.xuejike.query.core.criteria.*;
import com.github.xuejike.query.core.enums.StringMatchMode;
import com.github.xuejike.query.core.enums.WhereOperation;
import com.github.xuejike.query.core.po.BetweenObj;

import com.github.xuejike.query.core.po.LikeValObj;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuejike
 * @date 2020/12/16
 */

public class BaseWhereQuery<T,F,C extends BaseWhereQuery<T,F,C>> implements  WhereCriteria<C,F>{
    protected Map<F, Map<WhereOperation,Object>> whereMap = new ConcurrentHashMap<>();
    protected List<BaseWhereQuery<T,F,C>> orList = new LinkedList<>();
    protected C returnObj = (C)this;

    public BaseWhereQuery() {
    }

    public BaseWhereQuery(C returnObj) {
        this.returnObj = returnObj;
    }

    @Override
    public C ne(F field, Object val) {
        putWhere(WhereOperation.ne,field,val);
        return returnObj;
    }

    @Override
    public C ne(boolean condition, F field, Object val) {
        doIt(condition,()->ne(field,val));
        return returnObj;
    }

    @Override
    public C eq(F field, Object val) {
        putWhere(WhereOperation.eq,field,val);
        return returnObj;
    }


    @Override
    public C eq(boolean condition, F field, Object val) {
        doIt(condition,()->eq(field,val));
        return returnObj;
    }

    @Override
    public C gt(F field, Object val) {
        putWhere(WhereOperation.gt,field,val);
        return returnObj;
    }

    @Override
    public C gt(boolean condition, F field, Object val) {
        doIt(condition,()->gt(field,val));
        return returnObj;
    }

    @Override
    public C gte(F field, Object val) {
        putWhere(WhereOperation.gte,field,val);
        return returnObj;
    }

    @Override
    public C gte(boolean condition, F field, Object val) {
        doIt(condition,()->gte(field,val));
        return returnObj;
    }

    @Override
    public C lt(F field, Object val) {
        putWhere(WhereOperation.lt,field,val);
        return returnObj;
    }

    @Override
    public C lt(boolean condition, F field, Object val) {
        doIt(condition,()->lt(field,val));
        return returnObj;
    }

    @Override
    public C lte(F field, Object val) {
        putWhere(WhereOperation.lte,field,val);
        return returnObj;
    }

    @Override
    public C lte(boolean condition, F field, Object val) {
        doIt(condition,()->lte(field,val));
        return returnObj;
    }

    @Override
    public C like(F field, String val) {
        putWhere(WhereOperation.like,field,val);
        return returnObj;
    }

    @Override
    public C like(boolean condition, F field, String val) {
        doIt(condition,()->like(field,val,StringMatchMode.EXACT));
        return returnObj;
    }

    @Override
    public C like(F field, String val, StringMatchMode stringMatchMode) {
        putWhere(WhereOperation.like,field,new LikeValObj(val,stringMatchMode));
        return returnObj;
    }

    @Override
    public C like(boolean condition, F field, String val, StringMatchMode stringMatchMode) {
        doIt(condition,()->like(field, val, stringMatchMode));
        return returnObj;
    }

    @Override
    public C in(F field, Object... val) {
        putWhere(WhereOperation.in,field, CollUtil.newArrayList(val));
        return returnObj;
    }

    @Override
    public C in(boolean condition, F field, Object... val) {
        doIt(condition,()->in(field,val));
        return returnObj;
    }

    @Override
    public C notIn(F field, Object... val) {
        putWhere(WhereOperation.notIn,field,CollUtil.newArrayList(val));
        return returnObj;
    }

    @Override
    public C notIn(boolean condition, F field, Object... val) {
        doIt(condition,()->notIn(field,val));
        return returnObj;
    }

    @Override
    public C in(F field, Collection<Object> val) {
        putWhere(WhereOperation.in,field,val);
        return returnObj;
    }

    @Override
    public C in(boolean condition, F field, Collection<Object> val) {
        doIt(condition,()->in(field,val));
        return returnObj;
    }

    @Override
    public C notIn(F field, Collection<Object> val) {
        putWhere(WhereOperation.notIn,field,val);
        return returnObj;
    }

    @Override
    public C notIn(boolean condition, F field, Collection<Object> val) {
        doIt(condition,()->notIn(field,val));
        return returnObj;
    }

    @Override
    public C isNull(F field) {
        putWhere(WhereOperation.isNull,field,null);
        return returnObj;
    }

    @Override
    public C isNull(boolean condition, F field) {
        doIt(condition,()->isNull(field));

        return returnObj;
    }

    @Override
    public C notNull(F field) {
        putWhere(WhereOperation.notNull,field,null);
        return returnObj;
    }

    @Override
    public C notNull(boolean condition, F field) {
        doIt(condition,()->notNull(field));
        return returnObj;
    }

    @Override
    public C between(F field, Object start, Object end) {
        putWhere(WhereOperation.between,field,new BetweenObj(start,end));
        return returnObj;
    }

    @Override
    public C between(boolean condition, F field, Object start, Object end) {
        doIt(condition,()->between(field,start,end));
        return returnObj;
    }



    public boolean isEmpty(){
        return CollUtil.isEmpty(whereMap) && CollUtil.isEmpty(orList);
    }
    public boolean isNotEmpty(){
        return !isEmpty();
    }
    protected void doIt(boolean condition,Runnable run){
        if (condition){
            run.run();
        }
    }
    protected void putWhere(WhereOperation whereOperation,F field,Object val){
        Map<WhereOperation, Object> fieldMap = getFieldMap(field);
        if (fieldMap.containsKey(whereOperation)){
            Log.get().warn("请勿重复添加相同条件字段,条件将会被覆盖,{}-{}",field,whereOperation);
        }
        fieldMap.put(whereOperation,val);
    }
    protected Map<WhereOperation,Object> getFieldMap(F field){
        Map<WhereOperation, Object> map = whereMap.getOrDefault(field, new ConcurrentHashMap<>());
        if (!whereMap.containsKey(field)){
            whereMap.put(field,map);
        }
        return map;
    }

    public Map<F, Map<WhereOperation, Object>> getWhereMap() {
        return whereMap;
    }

    public List<BaseWhereQuery<T, F, C>> getOrList() {
        return orList;
    }
}
