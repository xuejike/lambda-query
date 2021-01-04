package com.github.xuejike.query.mongo.demo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author xuejike
 * @date 2021/1/4
 */
public enum U1Status implements IEnum<Long> {
    T1,T2;

    @Override
    public Long getValue() {
        return Long.valueOf(this.ordinal());
    }
}
