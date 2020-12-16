package com.github.xuejike.query.core.criteria;


import com.github.xuejike.query.core.enums.StringMatchMode;

import java.util.Collection;

/**
 * Where相关查询接口
 * @param <P>
 * @param <F>
 */
public interface WhereCriteria<P,F> {

    <V>P ne(F field,V val);
    <V>P eq(F field,V val);
    <V>P gt(F field,V val);
    <V>P gte(F field,V val);
    <V>P lt(F field,V val);
    <V>P lte(F field,V val);
    <V>P like(F field,String val);
    <V>P like(F field, String val, StringMatchMode stringMatchMode);
    <V>P in(F field,V... val);
    <V>P notIn(F field,V... val);
    <V>P in(F field, Collection<V> val);
    <V>P notIn(F field, Collection<V> val);
    P isNull(F field);
    P notNull(F field);
    <V>P between(F field,V start,V end);



}
