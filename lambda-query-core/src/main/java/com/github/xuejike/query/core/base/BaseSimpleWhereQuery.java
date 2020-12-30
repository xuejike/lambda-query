package com.github.xuejike.query.core.base;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class BaseSimpleWhereQuery<T,F,C extends BaseWhereQuery>  extends BaseWhereQuery<T,F,C>{


    public BaseSimpleWhereQuery() {
    }

    public BaseSimpleWhereQuery(C returnObj) {
        super(returnObj);
    }

    public BaseWhereQuery<T,F,C> or(){
        BaseWhereQuery<T, F, C> query = new BaseWhereQuery<>(returnObj);
        orList.add(query);
        return query;
    }
}
