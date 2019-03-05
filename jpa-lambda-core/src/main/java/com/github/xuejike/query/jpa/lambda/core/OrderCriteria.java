package com.github.xuejike.query.jpa.lambda.core;

public interface OrderCriteria<T,F> {
    T orderAsc(F ...fields);
    T orderDesc(F ...fields);
}
