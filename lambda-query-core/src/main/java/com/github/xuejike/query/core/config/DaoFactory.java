package com.github.xuejike.query.core.config;

import com.github.xuejike.query.core.base.BaseDao;
import com.github.xuejike.query.core.criteria.DaoCriteria;

/**
 * @author xuejike
 * @date 2020/12/28
 */
public abstract class DaoFactory {
    protected Class<?> daoCls;

    public DaoFactory(Class<?> daoCls) {
        this.daoCls = daoCls;
        init();
    }
    private void init(){
        JkQueryConfig.getInstance().addDaoFactory(this);
    }

    public Class<?> getDaoCls() {
        return daoCls;
    }

    /**
     * 创建Dao
     * @param entityCls 实体类
     * @param <T>
     * @return
     */
    abstract public <T> BaseDao<T,?> createDao(Class<T> entityCls);
}
