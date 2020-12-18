package com.github.xuejike.query.core.criteria;

import java.util.function.Consumer;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public interface OrCriteria<R,P,F> {

    R or(Consumer<P> or);
    WhereCriteria or();
}
