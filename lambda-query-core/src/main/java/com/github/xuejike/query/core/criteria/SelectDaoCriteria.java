package com.github.xuejike.query.core.criteria;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author xuejike
 * @date 2020/12/31
 */
public interface SelectDaoCriteria<T> {


    List<T> list();


    T getFirst();

    Optional<T> getFirstStream();

    IJPage<T> page(IJPage<T> page);

    T findById(Serializable id);

    Optional<T> findByIdStream(Serializable id);
}
