package com.github.xuejike.query.core.base;

import com.github.xuejike.query.core.criteria.DaoCriteria;
import com.github.xuejike.query.core.criteria.LoadRefCriteria;
import com.github.xuejike.query.core.criteria.MapCriteria;
import com.github.xuejike.query.core.criteria.SelectDaoCriteria;
import com.github.xuejike.query.core.enums.LoadRefMode;
import com.github.xuejike.query.core.po.FieldInfo;
import com.github.xuejike.query.core.po.LoadRefInfo;
import com.github.xuejike.query.core.tool.lambda.FieldFunction;
import com.github.xuejike.query.core.tool.lambda.LambdaTool;

import java.util.function.Consumer;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class BaseNestedWhereQuery<T,F,C extends BaseWhereQuery<T,F,C>>  extends BaseSimpleWhereQuery<T,F,C> implements LoadRefCriteria<F,C>{

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

    @Override
    public <X> C loadRef(F refField, Class<X> entityCls, FieldFunction<X, ?> targetField, LoadRefMode mode) {
        LoadRefInfo<X> info = new LoadRefInfo<>();
        info.setRefClass(entityCls);
        info.setMode(mode);
        info.setTargetField(buildFieldInfo(targetField));
        refClassMap.put(buildFieldInfo(refField),info);
        return returnObj;
    }


}
