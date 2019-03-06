package com.github.xuejike.query.jpa.lambda.core;

import com.github.xuejike.query.jpa.lambda.JpaQuery;
import org.hibernate.criterion.MatchMode;

public interface ExampleCriteria<T,P,F> {
    P example(T obj, MatchMode likeModel, F ... excludeProperties);
    P example(T obj, F ... excludeProperties);
    P example(T obj, MatchMode likeModel);
    P example(T obj);
}
