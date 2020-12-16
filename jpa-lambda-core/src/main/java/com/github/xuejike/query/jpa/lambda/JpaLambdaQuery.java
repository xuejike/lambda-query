package com.github.xuejike.query.jpa.lambda;

import com.github.xuejike.query.core.criteria.ExampleCriteria;
import com.github.xuejike.query.core.criteria.OrAndWhereCriteria;
import com.github.xuejike.query.core.criteria.OrderCriteria;
import com.github.xuejike.query.core.enums.StringMatchMode;
import com.github.xuejike.query.core.exception.LambdaQueryException;
import com.github.xuejike.query.core.tool.lambda.FieldFunction;
import com.github.xuejike.query.core.criteria.WhereCriteria;
import com.github.xuejike.query.jpa.lambda.core.*;
import com.github.xuejike.query.core.tool.lambda.LambdaTool;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class JpaLambdaQuery<T> extends AbstractJpaQuery<T>
        implements
        WhereCriteria<JpaLambdaQuery<T>, FieldFunction<T,?>>,
        OrderCriteria<JpaLambdaQuery<T>, FieldFunction<T,?>>,
        LoadJoinCriteria<JpaLambdaQuery<T>, FieldFunction<T,?>>,
        ExampleCriteria<T,JpaLambdaQuery<T>, FieldFunction<T,?>>,
        OrAndWhereCriteria<JpaLambdaQuery<T>, FieldFunction<T,?>> {
    private JpaQuery<T> jpaQuery;



    public JpaLambdaQuery(JpaQuery<T> jpaQuery) {
        super(jpaQuery);
        this.jpaQuery = jpaQuery;
    }

    public JpaLambdaQuery(Class<T> entityCls,Session session) {
        this(new JpaQuery<T>(entityCls,session));
    }

    protected String getFieldName(FieldFunction<T, ?> fieldFun){

        String name = LambdaTool.getName(fieldFun);
        return name;
    }

    protected String[] getFieldNames(FieldFunction<T, ?> ...fieldFun){
        return Optional.ofNullable(fieldFun)
                .filter(f->f.length>0)
                .map(f->{
                    String[] fieldNames = new String[fieldFun.length];
                    for (int i = 0; i < fieldFun.length; i++) {
                        fieldNames[i] = getFieldName(fieldFun[i]);
                    }
                    return fieldNames;
                }).orElse(null);
    }
    @Override
    public <V> JpaLambdaQuery<T> ne(FieldFunction<T, ?> field, V val) {
        jpaQuery.ne(getFieldName(field),val);
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> eq(FieldFunction<T, ?> field, V val) {
        jpaQuery.eq(getFieldName(field),val);
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> gt(FieldFunction<T, ?> field, V val) {
        jpaQuery.gt(getFieldName(field),val);
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> gte(FieldFunction<T, ?> field, V val) {
        jpaQuery.gte(getFieldName(field),val);
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> lt(FieldFunction<T, ?> field, V val) {
        jpaQuery.lt(getFieldName(field),val);
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> lte(FieldFunction<T, ?> field, V val) {
        jpaQuery.lte(getFieldName(field),val);
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> like(FieldFunction<T, ?> field, String val) {
        jpaQuery.like(getFieldName(field),val);
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> like(FieldFunction<T, ?> field, String val, StringMatchMode stringMatchMode) {
        jpaQuery.like(getFieldName(field),val, stringMatchMode);
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> in(FieldFunction<T, ?> field, V... val) {
        jpaQuery.in(getFieldName(field),val);
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> notIn(FieldFunction<T, ?> field, V... val) {
        jpaQuery.notIn(getFieldName(field),val);
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> in(FieldFunction<T, ?> field, Collection<V> val) {
        jpaQuery.in(getFieldName(field),val);
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> notIn(FieldFunction<T, ?> field, Collection<V> val) {
        jpaQuery.notIn(getFieldName(field),val);
        return this;
    }

    @Override
    public JpaLambdaQuery<T> isNull(FieldFunction<T, ?> field) {
        jpaQuery.isNull(getFieldName(field));
        return this;
    }

    @Override
    public JpaLambdaQuery<T> notNull(FieldFunction<T, ?> field) {
        jpaQuery.notNull(getFieldName(field));
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> between(FieldFunction<T, ?> field, V start, V end) {
        jpaQuery.between(getFieldName(field),start,end);
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> orNe(FieldFunction<T, ?> field, V val) {
        jpaQuery.orNe(getFieldName(field),val);
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> orEq(FieldFunction<T, ?> field, V val) {
        jpaQuery.orEq(getFieldName(field),val);
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> orGt(FieldFunction<T, ?> field, V val) {
        jpaQuery.orGt(getFieldName(field),val);
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> orGte(FieldFunction<T, ?> field, V val) {
        jpaQuery.orGte(getFieldName(field),val);
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> orLt(FieldFunction<T, ?> field, V val) {
        jpaQuery.orLt(getFieldName(field),val);
        return this;
    }

    @Override
    public JpaLambdaQuery<T> orIsNull(FieldFunction<T, ?> field) {
        jpaQuery.orIsNull(getFieldName(field));
        return this;
    }

    @Override
    public JpaLambdaQuery<T> orNotNull(FieldFunction<T, ?> field) {
        jpaQuery.orNotNull(getFieldName(field));
        return this;
    }

    @Override
    public <V> JpaLambdaQuery<T> orBetween(FieldFunction<T, ?> field, V start, V end) {
        jpaQuery.orBetween(getFieldName(field),start,end);
        return this;
    }

    @Override
    public JpaLambdaQuery<T> or(Function<WhereCriteria<JpaLambdaQuery<T>, FieldFunction<T, ?>>, WhereCriteria<JpaLambdaQuery<T>, FieldFunction<T, ?>>> orWhere) {
        Criterion[] criteria = nestCriteria(orWhere);
        jpaQuery.or(criteria);
        return this;
    }

    @Override
    public JpaLambdaQuery<T> and(Function<WhereCriteria<JpaLambdaQuery<T>, FieldFunction<T, ?>>, WhereCriteria<JpaLambdaQuery<T>, FieldFunction<T, ?>>> andWhere) {
        Criterion[] criteria = nestCriteria(andWhere);
        jpaQuery.and(criteria);
        return this;
    }


    private Criterion[] nestCriteria(Function<WhereCriteria<JpaLambdaQuery<T>, FieldFunction<T,?>>, WhereCriteria<JpaLambdaQuery<T>, FieldFunction<T,?>>> andWhere) {
        WhereCriteria<JpaLambdaQuery<T>, FieldFunction<T, ?>> apply = andWhere.apply(new JpaLambdaQuery<>(entityCls, session));
        List<Criterion> criterionList = ((JpaLambdaQuery) apply).getWhereCriterionList();
        Criterion[] criteria = new Criterion[criterionList.size()];
        criterionList.toArray(criteria);
        return criteria;
    }

    @Override
    public JpaLambdaQuery<T> orderAsc(FieldFunction<T, ?>... fields) {
        jpaQuery.orderAsc(getFieldNames(fields));
        return this;
    }

    @Override
    public JpaLambdaQuery<T> orderDesc(FieldFunction<T, ?>... fields) {
        jpaQuery.orderDesc(getFieldNames(fields));
        return this;
    }

    @Override
    public JpaLambdaQuery<T> loadJoin(FieldFunction<T, ?> field) {
        jpaQuery.loadJoin(getFieldName(field));
        return this;
    }

    public <P>JpaLambdaQuery<T> subQuery(FieldFunction<T, ?> field,Class<P> subEntity,
                                         Function<JpaLambdaQuery<P>,
                                                 JpaLambdaQuery<P>> subQueryFun){
        String fieldName = getFieldName(field);
        subQuery(fieldName,subEntity, subQueryFun);
        return this;
    }

    private <P> void subQuery(String fieldName,Class<P> subEntity, Function<JpaLambdaQuery<P>, JpaLambdaQuery<P>> subQueryFun) {
        JpaLambdaQuery<P> lambdaQuery = new JpaLambdaQuery(subEntity, session);
        lambdaQuery.setSubQueryName(fieldName);
        JpaLambdaQuery<P> apply = subQueryFun.apply(lambdaQuery);
        subQueryList.add(apply);
    }

    public <P>JpaLambdaQuery<T> subQuery(FieldFunction<T, P> field,
                                         Function<JpaLambdaQuery<P>,
                                                 JpaLambdaQuery<P>> subQueryFun){
        String fieldName = getFieldName(field);

        try {
            Class<P> type = (Class<P>) entityCls.getField(fieldName).getType();
            subQuery(fieldName,type,subQueryFun);
            return this;
        } catch (NoSuchFieldException e) {
            throw new LambdaQueryException("获取属性失败",e);
        }

    }

    @Override
    public JpaLambdaQuery<T> example(T obj, StringMatchMode likeModel, FieldFunction<T, ?>... excludeProperties) {
        jpaQuery.example(obj, likeModel, getFieldNames(excludeProperties));
        return this;
    }

    @Override
    public JpaLambdaQuery<T> example(T obj, StringMatchMode likeModel) {
        example(obj,likeModel,(FieldFunction)null);
        return this;
    }

    @Override
    public JpaLambdaQuery<T> example(T obj) {
        example(obj,(StringMatchMode)null,(FieldFunction)null);
        return this;
    }

    @Override
    public JpaLambdaQuery<T> example(T obj, FieldFunction<T, ?>... excludeProperties) {
        example(obj,null,excludeProperties);
        return this;
    }
}
