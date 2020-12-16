package com.github.xuejike.query.core;

import com.github.xuejike.query.core.criteria.*;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author xuejike
 * @date 2020/12/16
 */
public abstract class LambdaQuery<Q,T>  implements
        WhereCriteria<Q,String>,
        OrAndWhereCriteria<Q,String>,
        OrderCriteria<Q,String>,
        ExampleCriteria<T,Q,String>,
        SelectCriteria<Q,String> {
}
