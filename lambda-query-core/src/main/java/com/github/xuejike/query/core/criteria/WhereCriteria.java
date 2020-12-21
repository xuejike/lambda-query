package com.github.xuejike.query.core.criteria;


import com.github.xuejike.query.core.enums.StringMatchMode;

import java.util.Collection;

/**
 * Where相关查询接口
 * @param <P>
 * @param <F>
 */
public interface WhereCriteria<P,F> {

    /**
     * 不等于
     * @param field
     * @param val
     * @return
     */
    P ne(F field,Object val);

    /**
     * 不等于
     * @param condition  是否执行的判断
     * @param field
     * @param val
     * @return
     */
    P ne(boolean condition,F field,Object val);

    P eq(F field,Object val);
    P eq(boolean condition,F field,Object val);

    P gt(F field,Object val);
    P gt(boolean condition,F field,Object val);

    P gte(F field,Object val);
    P gte(boolean condition,F field,Object val);

    P lt(F field,Object val);
    P lt(boolean condition,F field,Object val);

    P lte(F field,Object val);
    P lte(boolean condition,F field,Object val);

    P like(F field,String val);
    P like(boolean condition,F field,String val);

    P like(F field, String val, StringMatchMode stringMatchMode);
    P like(boolean condition,F field, String val, StringMatchMode stringMatchMode);

    P in(F field,Object... val);
    P in(boolean condition,F field,Object... val);

    P notIn(F field,Object... val);
    P notIn(boolean condition,F field,Object... val);

    P in(F field, Collection<Object> val);
    P in(boolean condition,F field, Collection<Object> val);

    P notIn(F field, Collection<Object> val);
    P notIn(boolean condition,F field, Collection<Object> val);

    P isNull(F field);
    P isNull(boolean condition,F field);

    P notNull(F field);
    P notNull(boolean condition,F field);

    P between(F field,Object start,Object end);
    P between(boolean condition,F field,Object start,Object end);

}
