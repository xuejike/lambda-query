package com.github.xuejike.query.core.criteria;

public interface OrderCriteria<F,R> {
    R orderAsc(F ...fields);
    R orderDesc(F ...fields);
}
