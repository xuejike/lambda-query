# lambda-query 1.0 版本
[![](https://travis-ci.org/xuejike/lambda-query.svg?branch=master)](https://github.com/xuejike/lambda-query)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.xuejike/lambda-query/badge.svg)](https://mvnrepository.com/artifact/com.github.xuejike)
## 介绍
LambdaQuery 将支持通过Lambda进行多种数据源的查询 实现java端的简单数据的统一查询
## 功能规划
### 总体功能规划
* [x] 实现基础查询功能
* [x] 实现LoadRef功能,通过selectIn的方式进行数据表join, 实现跨服务,分布式数据库,跨不同数据库之间的join
* [ ] 增加redisCache功能,实现在loadRef的时候 可以通过配置项进行设置是否通过cache 进行加载
### 1. MongoDb
* [x] 实现基础的Lambda查询(已完成)
* [ ] 实现 新增,删除,更新等基础操作 
* [x] 新增LoadJoin功能,通过selectIn的方式进行数据表join

### 2. mybatis-plus
* [x] 接入mybatis-plus的查询接口(已完成)
* [ ] 接入mybatis-plus 的新增,删除,更新操作
* [x] 新增LoadJoin功能,通过selectIn的方式进行数据表join
* [ ] 新增Cache功能,采用redis 进行数据缓存,配合selectIn方式避免join查询直接访问数据库
* [ ] 新增Map接口,实现selectIn后数据合并问题

### 3. HttpServer&HttpClient
* [x] 新增Http模式,实现微服务之间 关联数据join 显示问题
* [x] 扩展全功能查询,实现微服务之间的HTTP接口数据查询功能
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
<!--Mongo 查询器依赖-->
      <dependency>
            <groupId>com.github.xuejike</groupId>
            <artifactId>lambda-query-mongo-starter</artifactId>
            <version>${jkquery.version}</version>
        </dependency>
<!--MybatisPlus 查询器依赖-->

    <dependency>
    <groupId>com.github.xuejike</groupId>
    <artifactId>lambda-query-mybatis-plus-starter</artifactId>
    <version>${jkquery.version}</version>
    </dependency>

```
2. 手动配置

引入依赖
```xml
<!--Mongo 查询器依赖-->
      <dependency>
            <groupId>com.github.xuejike</groupId>
            <artifactId>lambda-query-mongo</artifactId>
            <version>${jkquery.version}</version>
        </dependency>

        <!--MybatisPlus 查询器依赖-->

    <dependency>
        <groupId>com.github.xuejike</groupId>
        <artifactId>lambda-query-mybatis-plus</artifactId>
        <version>${jkquery.version}</version>
    </dependency>

```

初始化工厂类
```java
// MongoDB 查询器初始化
   @Bean
    public MongoDaoFactory mongoDaoFactory(MongoTemplate mongoTemplate){
            return new MongoDaoFactory(mongoTemplate);
    }

// MybatisPlus 查询器初始化
    @Bean
    public MyBatisPlusDaoFactory myBatisPlusDaoFactory(Collection<BaseMapper> mapperCollection){
            MyBatisPlusDaoFactory myBatisPlusDaoFactory = new MyBatisPlusDaoFactory(mapperCollection);
            return myBatisPlusDaoFactory;
    }
```
### 2.Mongo查询器使用

#### 1. 创建实体类

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

#### 2. 进行查询
##### 基础查询
```java
/**
 * == 查询
 */
@Test
public void testEq(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class).eq(TestDoc::getName, "name_1").list();
        Assertions.assertEquals(list.size(),1);
        TestDoc testDoc = list.get(0);
        Assertions.assertEquals(testDoc.getTitle(),"title_1");
        }

/**
 * 大于号查询
 *
 */
@Test
public void testGt(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class).gt(TestDoc::getNum,3 ).list();
        Assertions.assertEquals(list.size(),1);
        TestDoc testDoc = list.get(0);
        Assertions.assertEquals(testDoc.getTitle(),"title_4");
        }

/**
 * 大于等于查询
 */
@Test
public void testGte(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class).gte(TestDoc::getNum,4 ).list();
        Assertions.assertEquals(list.size(),1);
        TestDoc testDoc = list.get(0);
        Assertions.assertEquals(testDoc.getTitle(),"title_4");
        }

/**
 * 小于号查询
 */
@Test
public void testLt(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class).lt(TestDoc::getNum,1 ).list();
        Assertions.assertEquals(list.size(),1);
        TestDoc testDoc = list.get(0);
        Assertions.assertEquals(testDoc.getTitle(),"title_0");
        }

/**
 * 小于等于查询
 */
@Test
public void testLte(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class).lte(TestDoc::getNum,0 ).list();
        Assertions.assertEquals(list.size(),1);
        TestDoc testDoc = list.get(0);
        Assertions.assertEquals(testDoc.getTitle(),"title_0");
        }

/**
 * In 查询
 */
@Test
public void testIn(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class)
        .in(TestDoc::getTitle,"title_0" ).list();
        Assertions.assertEquals(list.size(),1);
        TestDoc testDoc = list.get(0);
        Assertions.assertEquals(testDoc.getTitle(),"title_0");
        }

/**
 * NotIn 查询
 */
@Test
public void testNotIn(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class)
        .notIn(TestDoc::getTitle,"title_0","title_1","title_2","title_3" ).list();
        Assertions.assertEquals(list.size(),1);
        TestDoc testDoc = list.get(0);
        Assertions.assertEquals(testDoc.getTitle(),"title_4");
        }

/**
 * or 查询
 */
@Test
public void testOr(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class).or().eq(TestDoc::getNum, 0).or().eq(TestDoc::getNum, 1).list();
        Assertions.assertEquals(list.size(),2);

        list = JQuerys.lambdaQuery(TestDoc.class).or(it->{
        it.eq(TestDoc::getNum,0).eq(TestDoc::getName,"name_0");
        }).or(it->{
        it.eq(TestDoc::getNum,1).eq(TestDoc::getName,"name_1");
        }).list();
        Assertions.assertEquals(list.size(),2);


        }

/**
 * 排序查询
 */
@Test
public void testOrder(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class).in(TestDoc::getNum, 1, 2).orderAsc(TestDoc::getNum).list();
        Assertions.assertEquals(list.size(),2);
        Assertions.assertEquals(list.get(0).getNum(),1);
        Assertions.assertEquals(list.get(1).getNum(),2);

        list = JQuerys.lambdaQuery(TestDoc.class).in(TestDoc::getNum, 1, 2).orderDesc(TestDoc::getNum).list();
        Assertions.assertEquals(list.size(),2);
        Assertions.assertEquals(list.get(0).getNum(),2);
        Assertions.assertEquals(list.get(1).getNum(),1);
        }

/**
 * 二级字段查询
 */
@Test
public void testSubField(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class).eq(of().subList(TestDoc::getToc).sub(TestDoc.Title::getTitle), "sub_title_0_0").list();
        Assertions.assertEquals(list.size(),1);
        Assertions.assertEquals(list.get(0).getNum(),0);
        }
public CascadeField<TestDoc,TestDoc> of(){
        return new CascadeField<>();
        }

```
##### loadJoin 查询
采用selectIn 和并行计算方式进行 join查询合并

```java
    @GetMapping("list")
    public Object testList(){
        JLambdaQuery<HttpEntity> query = JQuerys.lambdaQuery(HttpEntity.class);
        Object list = query.or().eq(HttpEntity::getName,"name1").or()
                .eq(HttpEntity::getName,"name2")
        // 属性 u2Id 关联实体 U2 的id字段,并映射结果到U1Vo
                .loadRef(HttpEntity::getU2Id, U2.class,U2::getId).map(U1Vo.class).list();
        return list;
    }

```
```java
@Data
public class U1Vo {
    private Long id;
    private String name;
    private String type;
    @SetRefValue("u2")
    private Long u2Id;
    @RefValue("#u2.name")
    private String u2Name;
}


```

### 3.MybatisPlus查询器使用

mybatis-plus 实体初始化

```java
@Data
@TableName("u1")
@MyBatisPlusDaoSelect
public class U1 {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String type;

}


```

