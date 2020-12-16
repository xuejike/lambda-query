package com.github.xuejike.query.core.criteria;


import com.github.xuejike.query.core.enums.StringMatchMode;

public interface ExampleCriteria<T,P,F> {
    P example(T obj, StringMatchMode likeModel, F ... excludeProperties);
    P example(T obj, F ... excludeProperties);
    P example(T obj, StringMatchMode likeModel);
    P example(T obj);
}
