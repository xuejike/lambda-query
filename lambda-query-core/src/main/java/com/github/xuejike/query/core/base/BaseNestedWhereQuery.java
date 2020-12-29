package com.github.xuejike.query.core.base;

import java.util.function.Consumer;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class BaseNestedWhereQuery<T,F,C>  extends BaseSimpleWhereQuery<T,F,C>{

    public BaseNestedWhereQuery() {

    }

    public BaseNestedWhereQuery(C returnObj) {
        super(returnObj);
    }

    public<R extends BaseNestedWhereQuery<T,F,R> > C or(Consumer<BaseNestedWhereQuery<T,F,R>> or){
        BaseNestedWhereQuery<T,F,R> query = new BaseNestedWhereQuery<T,F,R>();
        or.accept(query);
         if (query.isNotEmpty()){
             orList.add(query);
         }
         return returnObj;
     }
}
