package com.github.xuejike.query.mongo;

import com.github.xuejike.query.core.config.DaoFactory;
import com.github.xuejike.query.core.config.JkQueryConfig;
import com.github.xuejike.query.core.criteria.DaoCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author xuejike
 * @date 2020/12/28
 */
public class MongoDaoFactory extends DaoFactory {
    MongoTemplate mongoTemplate;


    public MongoDaoFactory(MongoTemplate mongoTemplate) {
        super(MongoDao.class);
        this.mongoTemplate = mongoTemplate;
        init();
    }
    public void init(){
        JkQueryConfig.getInstance().addDaoFactory(this);
    }


    @Override
    public <T> DaoCriteria<T> createDao(Class<T> entityCls) {
        return new MongoDao<>(mongoTemplate,entityCls);
    }
}
