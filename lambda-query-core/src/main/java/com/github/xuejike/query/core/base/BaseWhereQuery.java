package com.github.xuejike.query.core.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.log.Log;
import com.github.xuejike.query.core.criteria.*;
import com.github.xuejike.query.core.enums.OrderType;
import com.github.xuejike.query.core.enums.StringMatchMode;
import com.github.xuejike.query.core.enums.WhereOperation;
import com.github.xuejike.query.core.exception.LambdaQueryException;
import com.github.xuejike.query.core.po.*;

import com.github.xuejike.query.core.tool.lambda.CascadeField;
import com.github.xuejike.query.core.tool.lambda.FieldFunction;
import com.github.xuejike.query.core.tool.lambda.LambdaTool;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author xuejike
 * @date 2020/12/16
 */

public class BaseWhereQuery<T,F,C extends BaseWhereQuery<T,F,C>> implements  WhereCriteria<C,F>,SelectCriteria<C,F>{
    protected Map<FieldInfo, Map<WhereOperation,Object>> whereMap = new ConcurrentHashMap<>();
    protected List<BaseWhereQuery<T,F,?>> orList = new LinkedList<>();
    protected List<FieldInfo> selectList = new LinkedList<>();
    protected List<FieldInfo> excludeList = new LinkedList<>();
    protected Map<FieldInfo, OrderType> orderMap = new ConcurrentHashMap<>();
    protected Map<FieldInfo,LoadRefInfo<?>> refClassMap = new ConcurrentHashMap<>();

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
        return CollUtil.isEmpty(whereMap) && CollUtil.isEmpty(orList) && CollUtil.isEmpty(selectList) && CollUtil.isEmpty(excludeList) && CollUtil.isEmpty(orderMap);
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
        Map<WhereOperation, Object> fieldMap = null;
        fieldMap = getFieldMap(buildFieldInfo(field));

        if (fieldMap.containsKey(whereOperation)){
            Log.get().warn("请勿重复添加相同条件字段,条件将会被覆盖,{}-{}",field,whereOperation);
        }
        fieldMap.put(whereOperation,val);
    }
    protected FieldInfo buildFieldInfo(Object field){
        if (field instanceof FieldFunction && !(field instanceof CascadeField)){
            return LambdaTool.getFieldInfo(((FieldFunction<?, ?>) field));
        }else if (field instanceof String){
            return new FieldInfo(((String) field));
        }else if (field instanceof CascadeField){
            return ((CascadeField<?, ?>) field).getFieldInfo();
        }else{
            throw new LambdaQueryException("field type is error");
        }
    }
    protected Map<WhereOperation,Object> getFieldMap(FieldInfo field){
        Map<WhereOperation, Object> map = whereMap.getOrDefault(field, new ConcurrentHashMap<>());
        if (!whereMap.containsKey(field)){
            whereMap.put(field,map);
        }
        return map;
    }

    public Map<FieldInfo, Map<WhereOperation, Object>> getWhereMap() {
        return whereMap;
    }

    public List<BaseWhereQuery<T, F, ?>> getOrList() {
        return orList;
    }


    public QueryInfo buildQueryInfo(){
        List<QueryItem> andList = whereMap.entrySet().stream().map(it -> {
            Object key = it.getKey();
            QueryItem queryItem = new QueryItem();
            FieldInfo fieldInfo = null;
            fieldInfo = parseFieldInfo(key);
            queryItem.setField(fieldInfo);
            queryItem.setVal(it.getValue());
            return queryItem;


        }).collect(Collectors.toList());



        List<QueryInfo> collect = orList.stream().map(BaseWhereQuery::buildQueryInfo).collect(Collectors.toList());

        QueryInfo queryInfo = new QueryInfo();
        queryInfo.setAnd(andList);
        queryInfo.setOr(collect);
        return queryInfo;
    }

    public Map<FieldInfo, OrderType> getOrderMap() {
        return orderMap;
    }

    @Override
    public C select(F... fields) {
        List<FieldInfo> list = Arrays.stream(fields).map(this::parseFieldInfo).collect(Collectors.toList());
        this.selectList.addAll(list);
        return returnObj;
    }

    @Override
    public C exclude(F... fields) {
        List<FieldInfo> list = Arrays.stream(fields).map(this::parseFieldInfo).collect(Collectors.toList());
        this.excludeList.addAll(list);
        return returnObj;
    }

    public List<FieldInfo> getSelectList() {
        return selectList;
    }

    public List<FieldInfo> getExcludeList() {
        return excludeList;
    }

    private FieldInfo parseFieldInfo(Object key) {
        FieldInfo fieldInfo;
        if (key instanceof String) {
            fieldInfo = new FieldInfo((String) key);
        } else if (key instanceof CascadeField) {
            fieldInfo = ((CascadeField<?, ?>) key).getFieldInfo();
        } else if (key instanceof FieldFunction) {
            fieldInfo = LambdaTool.getFieldInfo(((FieldFunction) key));
        } else if (key instanceof FieldInfo) {
            fieldInfo =(FieldInfo) key;
        } else {
            throw new LambdaQueryException("字段类型不正确");
        }
        return fieldInfo;
    }

    public Map<FieldInfo, LoadRefInfo<?>> getRefClassMap() {
        return refClassMap;
    }
}
