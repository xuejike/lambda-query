package com.github.xuejike.query.core.base;

import java.util.function.Consumer;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class BaseNestedWhereQuery<T,F,C extends BaseWhereQuery<T,F,C>>  extends BaseSimpleWhereQuery<T,F,C>{
     public C or(Consumer<BaseNestedWhereQuery<T,F,C>> or){
         BaseNestedWhereQuery<T, F, C> query = new BaseNestedWhereQuery<T, F, C>();
         or.accept(query);
         if (query.isNotEmpty()){
             orList.add(query);
         }
         return returnObj;
     }
}
