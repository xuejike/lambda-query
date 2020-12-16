package com.github.xuejike.query.core.exception;

public class LambdaQueryException extends RuntimeException {
    public LambdaQueryException(String message) {
        super(message);
    }

    public LambdaQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
