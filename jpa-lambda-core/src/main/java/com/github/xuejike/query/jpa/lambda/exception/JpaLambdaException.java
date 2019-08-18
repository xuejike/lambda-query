package com.github.xuejike.query.jpa.lambda.exception;

public class JpaLambdaException extends RuntimeException {
    public JpaLambdaException(String message) {
        super(message);
    }

    public JpaLambdaException(String message, Throwable cause) {
        super(message, cause);
    }
}
