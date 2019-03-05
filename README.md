# jpa-lambda-query

#### 介绍
基于spring-jpa的Lambda查询方式，能够实现通过Lambda进行条件查询，关联属性加载，关联属性的子查询

#### 安装教程

1. 使用Spring-starter进行安装
2. 手动安装

#### 使用说明

1. 简单的查询语句
```java
    Session session = (Session) entityManager.getDelegate();
        //正常查询
        JpaLambdaQuery<User> userQuery = new JpaLambdaQuery<>(User.class, session);
        List<User> list = userQuery.eq(User::getUsername, "111")
                .gt(User::getCreateTime, LocalDateTime.now())
                .list();
```
2. 嵌套的Or And查询语句
```java
 List<User> list1 = JpaQuerys.lambda(User.class, entityManager)
                .eq(User::getUsername, "123")
                .orEq(User::getUsername, "456")
                .or(or->or.eq(User::getUsername,"lll").eq(User::getPwd,"456"))
                .list();
```
3. 分页查询
```java
     Page<User> userPage = JpaQuerys
                .lambda(User.class, entityManager)
                .pageList(new Page(1, 10));
```

4. 加载关联数据
```java
        List<User> list1 = JpaQuerys.lambda(User.class, entityManager)
                .eq(User::getUsername, "123")
                .loadJoin(User::getDept).list();
```
5. 使用关联属性进行关联查询
```java
        List<User> list2 = JpaQuerys.lambda(User.class, entityManager)
                .subQuery(User::getDept, lambda -> lambda.eq(Dept::getId, 1)).list();
```
