package com.github.xuejike.query.mybatisplus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xuejike.query.core.base.BaseDao;
import com.github.xuejike.query.core.criteria.DaoCriteria;
import com.github.xuejike.query.core.criteria.IPage;
import com.github.xuejike.query.core.po.QueryInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuejike
 * @date 2020/12/28
 */
public class MyBatisPlusDao<T> extends BaseDao<T> {
    BaseMapper<T> baseMapper;


    public MyBatisPlusDao(BaseMapper<T> baseMapper, Class<T> entityCls) {
        super(entityCls);
        this.baseMapper = baseMapper;

    }
    protected void buildQuery(){
        QueryInfo queryInfo = baseWhereQuery.buildQueryInfo();

    }
    @Override
    public DaoCriteria<T> getDao() {
        return this;
    }

    @Override
    public List<T> list() {

        return null ;
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public IPage<T> page(IPage<T> page) {
        return null;
    }

    @Override
    public T findById(Serializable id) {
        return null;
    }

    @Override
    public T updateById(T entity) {
        return null;
    }

    @Override
    public Long updateFindAll() {
        return null;
    }

    @Override
    public boolean removeById(Serializable id) {
        return false;
    }

    @Override
    public long removeQueryAll() {
        return 0;
    }

    @Override
    public long executeUpdate(Object query, Object... param) {
        return 0;
    }

    @Override
    public List<?> execute(Object query, Object... param) {
        return null;
    }
}
