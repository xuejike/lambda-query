package com.github.xuejike.query.core.base;

import cn.hutool.core.collection.CollUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class BaseSelectQuery<T> {
    private List<String> selectList = new LinkedList<>();
    private List<String> excludeList = new LinkedList<>();
    BaseSelectQuery<T> select(String ... fields){
        selectList.addAll(CollUtil.newArrayList(fields));
        return this;
    }
    BaseSelectQuery<T> excludeFields(String ... fields){
        excludeList.addAll(CollUtil.newArrayList(fields));
        return this;
    }
}
