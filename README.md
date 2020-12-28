# lambda-query 1.0 版本
[![](https://jitpack.io/v/xuejike/jpa-lambda-query.svg)](https://jitpack.io/#xuejike/jpa-lambda-query)


## 介绍
LambdaQuery 将支持通过Lambda进行多种数据源的查询 实现java端的简单数据的统一查询
## 功能规划
### 1. MongoDb
* [x] 实现基础的Lambda查询(已完成)
* [ ] 实现 新增,删除,更新等基础操作[开发中]

### 2. mybatis-plus
* [ ] 接入mybatis-plus的查询接口
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



### 3.持久化操作

* 新增
* 删除
* 更新
* 条件更新

## 扩展其他查询


