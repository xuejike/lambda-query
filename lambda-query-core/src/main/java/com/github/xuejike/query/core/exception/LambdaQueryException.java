package com.github.xuejike.query.core.exception;

import cn.hutool.core.util.StrUtil;

public class LambdaQueryException extends RuntimeException {
    public LambdaQueryException(String message,Object... args) {
        super(StrUtil.format(message,args));
    }

    public LambdaQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
