package com.github.xuejike.query.jpa.lambda.core;

import com.github.xuejike.query.jpa.lambda.JpaQuery;

import java.util.function.Function;

public interface OrAndWhereCriteria<P,F>  {

    <V> P orNe(F field, V val);

    <V> P orEq(F field, V val);

    <V> P orGt(F field, V val);

    <V> P orGte(F field, V val);

    <V> P orLt(F field, V val);

    P orIsNull(F field);

    P orNotNull(F field);

    <V> P orBetween(F field, V start, V end);

    P or(Function<
            WhereCriteria<P, F>,
            WhereCriteria<P, F>> orWhere);

    P and(Function<
            WhereCriteria<P, F>,
            WhereCriteria<P, F>> andWhere);
}
