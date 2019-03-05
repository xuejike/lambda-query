package com.github.xuejike.query.jpa.lambda.core;

public interface FieldFunction<T,R> {
    R apply(T entity);
}
