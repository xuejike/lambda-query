package com.github.xuejike.query.core.criteria;

public interface OrderCriteria<T,F> {
    T orderAsc(F ...fields);
    T orderDesc(F ...fields);
}
