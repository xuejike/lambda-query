package com.github.xuejike.query.jpa.lambda.core;

import com.github.xuejike.query.jpa.lambda.JpaQuery;

import java.util.function.Function;

public interface SubQueryCriteria<P,F> {
    P subQuery(F field, Function subQueryFun);
}
