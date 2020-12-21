package com.github.xuejike.query.core.criteria;

public interface SelectCriteria<R,F> {
    /**
     * 选择需要提取的字段
     * @param fields
     * @return
     */
    R select(F ... fields);

    /**
     * 选择排除的字段
     * @param fields
     * @return
     */
    R exclude(F ... fields);
}
