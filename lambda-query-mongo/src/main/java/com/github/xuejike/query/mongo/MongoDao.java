package com.github.xuejike.query.mongo;

import cn.hutool.core.collection.CollUtil;
import com.github.xuejike.query.core.base.BaseDao;
import com.github.xuejike.query.core.base.BaseWhereQuery;
import com.github.xuejike.query.core.criteria.DaoCriteria;
import com.github.xuejike.query.core.criteria.IJPage;
import com.github.xuejike.query.core.enums.OrderType;
import com.github.xuejike.query.core.po.FieldInfo;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

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
        Query builder = new Query();
        if (baseConditionsVo != null){
            MongoQueryBuilder.builder(builder,baseConditionsVo.getWhere());
            if (CollUtil.isNotEmpty(baseConditionsVo.getSelectList())){
                List<FieldInfo> list = baseConditionsVo.getSelectList();
                builderSelectedField(builder, list);
            }
            if (CollUtil.isNotEmpty(baseConditionsVo.getExcludeList())){
                List<FieldInfo> list = baseConditionsVo.getExcludeList();
                builderExclude(builder, list);
            }
            if (CollUtil.isNotEmpty(baseConditionsVo.getOrderMap())){
                Map<FieldInfo, OrderType> orderMap = baseConditionsVo.getOrderMap();
                builderOrder(builder, orderMap);
            }
        }
        MongoQueryBuilder.builder(builder,baseWhereQuery.buildQueryInfo());


        if (CollUtil.isNotEmpty(baseWhereQuery.getSelectList())){
            List<FieldInfo> list = baseWhereQuery.getSelectList();
            builderSelectedField(builder,list);
        }
        if (CollUtil.isNotEmpty(baseWhereQuery.getExcludeList())){
            List<FieldInfo> list = baseWhereQuery.getExcludeList();
            builderExclude(builder, list);
        }
        if (CollUtil.isNotEmpty(baseWhereQuery.getOrderMap())){
            Map<FieldInfo, OrderType> orderMap = baseWhereQuery.getOrderMap();
            builderOrder(builder, orderMap);
        }
        return builder;

    }

    private void builderOrder(Query builder, Map<FieldInfo, OrderType> orderMap) {
        List<Sort.Order> orderList = orderMap.entrySet().stream().map(it -> {
            if (it.getValue() == OrderType.desc) {
                return Sort.Order.desc(MongoQueryBuilder.buildField(it.getKey()));
            } else {
                return Sort.Order.asc(MongoQueryBuilder.buildField(it.getKey()));
            }
        }).collect(Collectors.toList());
        builder.with(Sort.by(orderList));
    }

    private void builderExclude(Query builder, List<FieldInfo> list) {
        Field fields = builder.fields();
        for (FieldInfo info : list) {
            fields.exclude(MongoQueryBuilder.buildField(info));
        }
    }

    private void builderSelectedField(Query builder, List<FieldInfo> list) {
        Field fields = builder.fields();

        for (FieldInfo info : list) {
            fields.include(MongoQueryBuilder.buildField(info));
        }
    }

    @Override
    public DaoCriteria<T> getDao() {
        return this;
    }


    @Override
    public T getFirst() {
        Query query = buildQuery();
        query.limit(1);
        List<T> list = mongoTemplate.find(query, entityCls);
        if (list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }

    }

    @Override
    public List<T> list() {
        return mongoTemplate.find(buildQuery(),entityCls);

    }

    @Override
    public Long count() {
        return mongoTemplate.count(buildQuery(),entityCls);
    }

    @Override
    public IJPage<T> page(IJPage<T> page) {
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
    public boolean updateById(T entity) {
        T save = mongoTemplate.save(entity);
        return true;
    }

    @Override
    public T insert(T entity) {
        T insert = mongoTemplate.insert(entity);
        return insert;
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
    public void injectionBaseWhereQuery(BaseWhereQuery baseWhereQuery) {
        this.baseWhereQuery = baseWhereQuery;
    }
}
