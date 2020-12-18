package com.github.xuejike.query.core.criteria;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public interface UpdateSetCriteria<R,F,V> {
    R setUpdate(F field,V val);
}
