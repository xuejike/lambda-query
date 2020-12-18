package com.github.xuejike.query.core.base;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class BaseSimpleWhereQuery<T,F,C extends BaseWhereQuery<T,F,C>>  extends BaseWhereQuery<T,F,C>{
    public BaseWhereQuery<T,F,C> or(){
        BaseWhereQuery<T, F, C> query = new BaseWhereQuery<>(returnObj);
        orList.add(query);
        return query;
    }
}
