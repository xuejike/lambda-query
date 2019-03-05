package com.github.xuejike.query.jpa.lambda;

import org.hibernate.Session;

import javax.persistence.EntityManager;

public class JpaQuerys {

    protected static Session session;

    public static void setSession(Session session) {
        JpaQuerys.session = session;
    }
    public static void setEntityManager(EntityManager entityManager){
        JpaQuerys.session = (Session)entityManager.getDelegate();
    }

    public static <T> JpaQuery<T> query(Class<T> entityCls, Session session){
        return new JpaQuery<T>(entityCls,session);
    }
    public static <T> JpaQuery<T> query(Class<T> entityCls){
        return new JpaQuery<T>(entityCls,session);
    }
    public static <T> JpaQuery<T> query(Class<T> entityCls, EntityManager entityManager){
        return query(entityCls, (Session) entityManager.getDelegate());
    }

    public static <T> JpaLambdaQuery<T> lambda(Class<T> entityCls,EntityManager entityManager){
        return lambda(entityCls, (Session) entityManager.getDelegate());
    }
    public static <T> JpaLambdaQuery<T> lambda(Class<T> entityCls,Session session){
        return new JpaLambdaQuery<>(entityCls,session);
    }
    public static <T> JpaLambdaQuery<T> lambda(Class<T> entityCls){
        return new JpaLambdaQuery<>(entityCls,session);
    }
}
