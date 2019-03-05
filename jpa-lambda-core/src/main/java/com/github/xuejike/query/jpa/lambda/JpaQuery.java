package com.github.xuejike.query.jpa.lambda;

import com.github.xuejike.query.jpa.lambda.core.*;
import org.hibernate.Session;
import org.hibernate.criterion.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class JpaQuery<T> extends AbstractJpaQuery<T> implements
        WhereCriteria<JpaQuery<T>,String>,
        OrAndWhereCriteria<JpaQuery<T>,String>,
        OrderCriteria<JpaQuery<T>,String>,
        LoadJoinCriteria<JpaQuery<T>,String>,
        SelectCriteria<JpaQuery<T>,String> {


    public JpaQuery(Class<T> entityCls,Session session) {
        super(entityCls,session);
    }

    public JpaQuery(AbstractJpaQuery<T> query) {
        super(query);
    }

    public JpaLambdaQuery<T> lambda(){
        JpaLambdaQuery<T> query = new JpaLambdaQuery<T>(this);
        return query;
    }
    public JpaQuery<T> orderAsc(String ...fields){
        for (String field : fields) {
            orderList.add(Order.asc(field));
        }
        return this;
    }
    public JpaQuery<T> orderDesc(String ...fields){
        for (String field : fields) {
            orderList.add(Order.desc(field));
        }
        return this;
    }
    public JpaQuery<T> groupBy(String field){
        PropertyProjection groupProperty = Projections.groupProperty(field);
        selectProjectionList.add(groupProperty);
        return this;

    }
    @Override
    public JpaQuery<T> distinct(String field, String alias) {

        Projection property = Projections.distinct(Projections.property(field));
        if (alias != null){
            property = ((PropertyProjection) property).as(alias);
        }
        selectProjectionList.add(property);
        return this;
    }

    @Override
    public JpaQuery<T> count(String field, String alias) {
        Projection property = Projections.count(field);
        if (alias != null){
            property = ((CountProjection) property).as(alias);
        }
        selectProjectionList.add(property);
        return this;
    }

    public JpaQuery<T> select(String field,String alias){
        Projection property = Projections.property(field);
        if (alias != null){
            property = ((PropertyProjection) property).as(alias);
        }
        selectProjectionList.add(property);
        return this;
    }


    @Override
     public JpaQuery<T> max(String field, String alias) {
        Projection property = Projections.max(field);
        if (alias != null){
            property = ((SimpleProjection) property).as(alias);
        }
        selectProjectionList.add(property);
        return this;
    }

    @Override
    public JpaQuery<T> min(String field, String alias) {
        Projection property = Projections.min(field);
        if (alias != null){
            property = ((SimpleProjection) property).as(alias);
        }
        selectProjectionList.add(property);
        return this;
    }

    @Override
    public JpaQuery<T> avg(String field, String alias) {
        Projection property = Projections.avg(field);
        if (alias != null){
            property = ((SimpleProjection) property).as(alias);
        }
        selectProjectionList.add(property);
        return this;
    }

    @Override
    public JpaQuery<T> sum(String field, String alias) {
        Projection property = Projections.sum(field);
        if (alias != null){
            property = ((SimpleProjection) property).as(alias);
        }
        selectProjectionList.add(property);
        return this;
    }

    @Override
    public JpaQuery<T> countDistinct(String field, String alias) {
        Projection property = Projections.countDistinct(field);
        if (alias != null){
            property = ((SimpleProjection) property).as(alias);
        }
        selectProjectionList.add(property);
        return this;
    }


    @Override
    public <V> JpaQuery<T> ne(String field, V val) {
        whereCriterionList.add(Restrictions.ne(field, val));
        return this;
    }

    @Override
    public <V>JpaQuery<T> orNe(String field, V val){
        or(Restrictions.ne(field, val));
        return this;
    }

    @Override
    public <V> JpaQuery<T> eq(String field, V val) {
        whereCriterionList.add(Restrictions.eq(field, val));
        return this;
    }
    @Override
    public <V> JpaQuery<T> orEq(String field, V val) {
        or(Restrictions.eq(field, val));
        return this;
    }
    @Override
    public <V> JpaQuery<T> gt(String field, V val) {
        whereCriterionList.add(Restrictions.gt(field, val));
        return this;
    }
    @Override
    public <V> JpaQuery<T> orGt(String field, V val) {
        or(Restrictions.gt(field, val));
        return this;
    }

    @Override
    public <V> JpaQuery<T> gte(String field, V val) {
        whereCriterionList.add(Restrictions.ge(field, val));
        return this;
    }
    @Override
    public <V> JpaQuery<T> orGte(String field, V val) {
        or(Restrictions.ge(field, val));
        return this;
    }

    @Override
    public <V> JpaQuery<T> lt(String field, V val) {

        whereCriterionList.add(Restrictions.lt(field, val));
        return this;
    }
    @Override
    public <V> JpaQuery<T> orLt(String field, V val) {

        or(Restrictions.lt(field, val));
        return this;
    }
    @Override
    public <V> JpaQuery<T> lte(String field, V val) {
        whereCriterionList.add(Restrictions.le(field, val));
        return this;
    }

    @Override
    public <V> JpaQuery<T> in(String field, V... val) {
        Optional.ofNullable(val)
                .filter(v->v.length>0)
                .ifPresent(v-> whereCriterionList
                        .add(Restrictions.in(field, val)));
        return this;
    }

    @Override
    public <V> JpaQuery<T> notIn(String field, V... val) {
        Optional.ofNullable(val)
                .filter(v->v.length>0)
                .ifPresent(v-> whereCriterionList
                        .add(Restrictions.not(Restrictions.in(field, val))));
        return this;
    }

    @Override
    public <V> JpaQuery<T> in(String field, Collection<V> val) {
        Optional.ofNullable(val)
                .filter(v->v.size()>0)
                .ifPresent(v-> whereCriterionList
                        .add(Restrictions.in(field, val)));

        return this;
    }

    @Override
    public <V> JpaQuery<T> notIn(String field, Collection<V> val) {
        Optional.ofNullable(val)
                .filter(v->v.size()>0)
                .ifPresent(v-> whereCriterionList
                        .add(Restrictions.not(Restrictions.in(field, val))));

        return this;
    }

    @Override
    public <V> JpaQuery<T> like(String field, String val) {
        return like(field, val,MatchMode.ANYWHERE);
    }

    @Override
    public <V> JpaQuery<T> like(String field, String val, MatchMode matchMode) {
        Optional.ofNullable(val)
                .ifPresent(v-> whereCriterionList.add(Restrictions.like(field, v, matchMode)));
        return this;
    }

    @Override
    public JpaQuery<T> isNull(String field) {
        whereCriterionList.add(Restrictions.isNull(field));
        return this;
    }
    @Override
    public JpaQuery<T> orIsNull(String field) {
        or(Restrictions.isNull(field));
        return this;
    }
    @Override
    public JpaQuery<T> notNull(String field) {
        whereCriterionList.add(Restrictions.isNotNull(field));
        return this;
    }
    @Override
    public JpaQuery<T> orNotNull(String field) {
        or(Restrictions.isNotNull(field));
        return this;
    }
    @Override
    public <V> JpaQuery<T> between(String field, V start, V end) {
        whereCriterionList.add(Restrictions.between(field,start,end));
        return this;
    }
    @Override
    public <V> JpaQuery<T> orBetween(String field, V start, V end) {
        or(Restrictions.between(field,start,end));
        return this;
    }
    @Override
    public JpaQuery<T> or(Function<
            WhereCriteria<JpaQuery<T>, String>,
            WhereCriteria<JpaQuery<T>, String>> orWhere){
        Criterion[] criteria = nestCriteria(orWhere);
        or(criteria);
        return this;
    }

    @Override
    public JpaQuery<T> and(Function<
            WhereCriteria<JpaQuery<T>, String>,
            WhereCriteria<JpaQuery<T>, String>> andWhere){
        Criterion[] criteria = nestCriteria(andWhere);
        and(criteria);
        return this;
    }

    private Criterion[] nestCriteria(Function<WhereCriteria<JpaQuery<T>, String>, WhereCriteria<JpaQuery<T>, String>> andWhere) {
        WhereCriteria<JpaQuery<T>, String> apply = andWhere.apply(new JpaQuery<T>(entityCls, session));
        List<Criterion> criterionList = ((JpaQuery) apply).getWhereCriterionList();
        Criterion[] criteria = new Criterion[criterionList.size()];
        criterionList.toArray(criteria);
        return criteria;
    }

    protected JpaQuery<T> and(Criterion... criteria){

        Conjunction and = Restrictions.and(criteria);
        if (getWhereCriterionList().size() > 0){
            Criterion lastCriterion = getWhereCriterionList().remove(getWhereCriterionList().size() - 1);
            LogicalExpression andLast = Restrictions.and(lastCriterion, and);
            getWhereCriterionList().add(andLast);
        }else{
            getWhereCriterionList().add(and);
        }
        return this;
    }
    protected JpaQuery<T> or(Criterion... criteria){

        Conjunction and = Restrictions.and(criteria);
        if (getWhereCriterionList().size() > 0){
            Criterion lastCriterion = getWhereCriterionList().remove(getWhereCriterionList().size() - 1);
            LogicalExpression or = Restrictions.or(lastCriterion, and);
            getWhereCriterionList().add(or);
        }else{
            getWhereCriterionList().add(and);
        }
        return this;
    }

    @Override
    public JpaQuery<T> loadJoin(String field) {
        fetchList.add(field);
        return this;
    }

    public JpaQuery<T> subQuery(String field,Function<JpaQuery<?>,JpaQuery<?>> subQueryFun){
        JpaQuery<Object> jpaQuery = new JpaQuery<>(null, session);
        jpaQuery.setSubQueryName(field);
        JpaQuery<?> apply = subQueryFun.apply(jpaQuery);
        subQueryList.add(apply);
        return this;
    }


}
