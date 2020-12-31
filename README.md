# lambda-query 1.0 版本
[![](https://travis-ci.org/xuejike/lambda-query.svg?branch=master)](https://github.com/xuejike/lambda-query)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.xuejike/lambda-query/badge.svg)](https://mvnrepository.com/artifact/com.github.xuejike)
## 介绍
LambdaQuery 将支持通过Lambda进行多种数据源的查询 实现java端的简单数据的统一查询
## 功能规划
### 1. MongoDb
* [x] 实现基础的Lambda查询(已完成)
* [ ] 实现 新增,删除,更新等基础操作[开发中]

### 2. mybatis-plus
* [x] 接入mybatis-plus的查询接口(已完成)
* [ ] 接入mybatis-plus 的新增,删除,更新操作
* [ ] 新增LoadJoin功能,通过selectIn的方式进行数据表join
* [ ] 新增Cache功能,采用redis 进行数据缓存,配合selectIn方式避免join查询直接访问数据库
* [ ] 新增Map接口,实现selectIn后数据合并问题

### 3. HttpServer&HttpClient
* [ ] 新增Http模式,实现微服务之间 关联数据join 显示问题
* [ ] 扩展全功能查询,实现微服务之间的HTTP接口数据查询功能
* [ ] 扩展 Http 自定义查询字段和结果集字段,可以实现灵活空值查询字段与条件
* [ ] 新增拦截器,实现对查询,新增,修改操作的全局拦截,可以实现后端租户拦截,http接口的权限拦截限制


## 功能支持
### 1. 基础查询条件

* 相等,eq
* 不等,ne
* 空值,isNull
* 大于,gt
* 大于等于,gte  
* 小于,lt
* 小于等于,lte
* 包含,in
* 不包含,notIn
* 之间 ,between

### 2. 结果查询

* 通过ID查询
* 查询列表
* 分页查询
* count 查询



## 使用说明
### 1.依赖引入
1. springBoot自动配置
```xml
      <dependency>
            <groupId>com.github.xuejike</groupId>
            <artifactId>lambda-query-mongo-starter</artifactId>
            <version>${jkquery.version}</version>
        </dependency>
```
2. 手动配置

引入依赖
```xml
      <dependency>
            <groupId>com.github.xuejike</groupId>
            <artifactId>lambda-query-mongo</artifactId>
            <version>${jkquery.version}</version>
        </dependency>
```

初始化工厂类
```java

MongoDaoFactory mongoDaoFactory =   new MongoDaoFactory(mongoTemplate);

```
### 2.Mongo查询器使用

1. 创建实体类

```java
@Document("demo_doc")
@Data
// 采用mongo进行查询
@MongoDaoSelect
public class TestDoc {
    @MongoId
    private String id;
    private String name;
    private String title;
    private Long num;
    private List<Title> toc;
    @Data
    public static class Title{
        private String title;
        private String desc;

    }
}

```

2. 进行查询
```java
        JkLambdaQuery<TestDoc> lambda = JkQuerys.lambdaQuery(TestDoc.class);


```


### 3.MybatisPlus查询器使用


