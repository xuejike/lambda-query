package com.github.xuejike.query.mongo;

import com.github.xuejike.query.core.base.BaseNestedWhereQuery;
import com.github.xuejike.query.core.criteria.DaoCriteria;
import com.github.xuejike.query.core.criteria.IPage;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class MongoDao<T> implements DaoCriteria<T> {
    MongoTemplate mongoTemplate;
    private BaseNestedWhereQuery baseNestedWhereQuery;
    private Class<T> entityCls;

    public MongoDao(MongoTemplate mongoTemplate, BaseNestedWhereQuery baseNestedWhereQuery, Class<T> entityCls) {
        this.mongoTemplate = mongoTemplate;
        this.baseNestedWhereQuery = baseNestedWhereQuery;
        this.entityCls = entityCls;
    }

    @Override
    public DaoCriteria<T> getDao() {
        return this;
    }

    @Override
    public List<T> list() {
        return mongoTemplate.findAll(entityCls);
    }

    @Override
    public IPage<T> page(IPage<T> page) {

        return null ;
    }

    @Override
    public T findById(Serializable id) {
        return null;
    }

    @Override
    public T updateById(Object entity) {
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
    public long removeFindAll() {
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
