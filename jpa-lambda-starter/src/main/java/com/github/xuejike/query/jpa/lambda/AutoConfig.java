package com.github.xuejike.query.jpa.lambda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class AutoConfig {

    @Autowired
    public void initJpaLambdaQuery(  @Autowired EntityManager entityManager){
        JpaQuerys.setEntityManager(entityManager);
    }
}
