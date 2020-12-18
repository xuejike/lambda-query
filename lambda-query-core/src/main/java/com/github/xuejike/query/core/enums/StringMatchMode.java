package com.github.xuejike.query.core.enums;

/**
 * @author xuejike
 * @date 2020/12/16
 */
public enum  StringMatchMode {
    /**
     * 精确匹配
     */
    EXACT,
    /**
     * 前匹配
     */
    START,
    /**
     * 后匹配
     */
    END,
    /**
     * 任意匹配
     */
    ANYWHERE,
    /**
     * 正则匹配
     */
    REGEX
    ;

}
