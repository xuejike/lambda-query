package com.github.xuejike.query.mongo;

import cn.hutool.core.collection.CollUtil;
import com.github.xuejike.query.core.base.BaseNestedWhereQuery;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class MongoQueryBuilder {
    private BaseNestedWhereQuery baseNestedWhereQuery;

    public MongoQueryBuilder(BaseNestedWhereQuery baseNestedWhereQuery) {
        this.baseNestedWhereQuery = baseNestedWhereQuery;
    }
    public void buildQuery(){
        if (CollUtil.isNotEmpty(baseNestedWhereQuery.getWhereMap())){

        }
    }
}
