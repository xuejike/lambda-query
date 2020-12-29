package com.github.xuejike.query.mongo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.github.xuejike.query.core.base.BaseDao;
import com.github.xuejike.query.core.base.BaseNestedWhereQuery;
import com.github.xuejike.query.core.base.BaseWhereQuery;
import com.github.xuejike.query.core.criteria.DaoCriteria;
import com.github.xuejike.query.core.criteria.IPage;
import com.github.xuejike.query.core.criteria.InjectionBaseQuery;
import com.github.xuejike.query.core.po.FieldInfo;
import com.github.xuejike.query.core.po.Page;
import com.github.xuejike.query.core.po.QueryInfo;
import com.github.xuejike.query.core.po.QueryItem;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class MongoDao<T>  extends BaseDao<T> {
    MongoTemplate mongoTemplate;



    public MongoDao(MongoTemplate mongoTemplate, Class<T> entityCls) {
        super(entityCls);
        this.mongoTemplate = mongoTemplate;

    }


    private Query buildQuery(){
        Query builder = MongoQueryBuilder.builder(baseWhereQuery.buildQueryInfo());
        if (CollUtil.isNotEmpty(baseWhereQuery.getSelectList())){
            Field fields = builder.fields();
            List<FieldInfo> list = baseWhereQuery.getSelectList();
            for (FieldInfo info : list) {
                fields.include(MongoQueryBuilder.buildField(info));
            }
        }
        if (CollUtil.isNotEmpty(baseWhereQuery.getExcludeList())){
            Field fields = builder.fields();
            List<FieldInfo> list = baseWhereQuery.getExcludeList();
            for (FieldInfo info : list) {
                fields.exclude(MongoQueryBuilder.buildField(info));
            }
        }
        return builder;

    }

    @Override
    public DaoCriteria<T> getDao() {
        return this;
    }



    @Override
    public List<T> list() {
        if (baseWhereQuery.isEmpty()){
            return mongoTemplate.findAll(entityCls);
        }else{
            return mongoTemplate.find(buildQuery(),entityCls);
        }
    }

    @Override
    public Long count() {
        return mongoTemplate.count(buildQuery(),entityCls);
    }

    @Override
    public IPage<T> page(IPage<T> page) {
        Query query = buildQuery();
         if (page.isHaveTotal()){
            long count = mongoTemplate.count(query, entityCls);
             page.setTotal(count);
        }
        query.skip(page.getPageNo()*page.getPageSize());
        query.limit(page.getPageSize());
        List<T> ts = mongoTemplate.find(query, entityCls);
        page.setData(ts);

        return page;
    }

    @Override
    public T findById(Serializable id) {
        return mongoTemplate.findById(id,entityCls);
    }

    @Override
    public T updateById(T entity) {
        return mongoTemplate.save(entity);
    }

    @Override
    public Long updateFindAll() {
        Query query = buildQuery();
        Update update = new Update();

        return mongoTemplate.updateMulti(query,update,entityCls).getModifiedCount();
    }

    @Override
    public boolean removeById(Serializable id) {
        Query query = new Query(Criteria.where("id").is(id));
        DeleteResult remove = mongoTemplate.remove(query, entityCls);
        return remove.getDeletedCount()>0;
    }

    @Override
    public long removeQueryAll() {
        Query query = buildQuery();
        return mongoTemplate.remove(query,entityCls).getDeletedCount();
    }

    @Override
    public long executeUpdate(Object query, Object... param) {
        if (query instanceof Document){
            Document document = mongoTemplate.executeCommand(((Document) query));
        }else if (query instanceof String){
            Document document = mongoTemplate.executeCommand(((String) query));
        }
        return 0L;
    }

    @Override
    public List<?> execute(Object query, Object... param) {
        if (query instanceof Document){
            Document document = mongoTemplate.executeCommand(((Document) query));
        }else if (query instanceof String){
            Document document = mongoTemplate.executeCommand(((String) query));
        }
        return null;
    }

    @Override
    public void injectionBaseWhereQuery(BaseWhereQuery baseWhereQuery) {
        this.baseWhereQuery = baseWhereQuery;
    }
}
