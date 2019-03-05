package com.github.xuejike.query.jpa.lambda.core.impl;

import com.github.xuejike.query.jpa.lambda.core.IPage;

import java.util.List;


public class Page<T> implements IPage<T> {
    private List<T> data;
    private int pageNo;
    private int pageSize;
    private long total;
    private boolean haveTotal;

    public Page() {
    }

    public Page(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Page(int pageNo, int pageSize, boolean haveTotal) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.haveTotal = haveTotal;
    }

    @Override
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public boolean isHaveTotal() {
        return haveTotal;
    }

    public void setHaveTotal(boolean haveTotal) {
        this.haveTotal = haveTotal;
    }
}
