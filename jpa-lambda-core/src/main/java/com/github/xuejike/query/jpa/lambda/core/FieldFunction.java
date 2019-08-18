package com.github.xuejike.query.jpa.lambda.core;

import java.io.Serializable;

public interface FieldFunction<T,R> extends Serializable {
    R apply(T entity);
}
