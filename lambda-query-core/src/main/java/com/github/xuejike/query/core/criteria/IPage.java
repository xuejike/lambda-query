package com.github.xuejike.query.core.criteria;

import java.util.List;

public interface IPage<T> {


    List<T> getData();

    int getPageNo();

    int getPageSize();

    long getTotal();

    boolean isHaveTotal();

    void setTotal(long total);

    void setData(List<T> data);
}
