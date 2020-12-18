package com.github.xuejike.query.core;

import com.github.xuejike.query.core.base.BaseNestedWhereQuery;
import com.github.xuejike.query.core.criteria.DaoCriteria;
import com.github.xuejike.query.core.tool.lambda.FieldFunction;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class JkQuery<T> extends BaseNestedWhereQuery<T, String, JkQuery<T>> implements DaoCriteria<T> {
    @Override
    public DaoCriteria<T> getDao() {
        return null;
    }
}
