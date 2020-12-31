package com.github.xuejike.query.core.criteria;

/**
 * @author xuejike
 * @date 2020/12/31
 */
public interface MapCriteria {
    <M> SelectDaoCriteria<M> map(Class<M> mapCls);
}
