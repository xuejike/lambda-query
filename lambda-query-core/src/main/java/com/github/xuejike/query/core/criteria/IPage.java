package com.github.xuejike.query.core.criteria;

import java.util.List;

public interface IPage<T> {


    abstract List<T> getData();

    int getPageNo();

    int getPageSize();

    long getTotal();

    boolean isHaveTotal();
}
