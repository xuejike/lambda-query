package com.github.xuejike.query.core.tool.lambda;

import java.io.Serializable;

public interface FieldFunction<T,R> extends Serializable {
    R apply(T entity);
}
