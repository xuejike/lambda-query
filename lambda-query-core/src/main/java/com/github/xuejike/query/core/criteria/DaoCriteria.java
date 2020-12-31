package com.github.xuejike.query.core.criteria;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public interface DaoCriteria<T> extends SelectDaoCriteria<T>, GetDaoCriteria<T> {

    /**
     * 列表查询
     * @return
     */
    @Override
    default List<T> list(){
        return getDao().list();
    }
    @Override
    default T getFirst(){
        return getDao().getFirst();
    }
    @Override
    default Optional<T> getFirstStream(){
        return Optional.ofNullable(getFirst());
    }
    default Long count(){
        return getDao().count();
    }
    /**
     * 分页查询
     * @param page
     * @return
     */
    @Override
    default IJPage<T> page(IJPage<T> page){
        return getDao().page(page);
    }

    /**
     * 通过ID进行查询
     * @param id
     * @return
     */
    @Override
    default T findById(Serializable id){
        return getDao().findById(id);
    }

    @Override
    default Optional<T> findByIdStream(Serializable id){
        return Optional.ofNullable(findById(id));
    }
    /**
     * 根据ID进行更新
     * @param entity
     * @return
     */
    default boolean updateById(T entity){
        return getDao().updateById(entity);
    }

    /**
     * 插入
     * @param entity
     * @return
     */
    default T insert(T entity){
        return getDao().insert(entity);
    }

    default Long updateFindAll(){
        return getDao().updateFindAll();
    }

    /**
     * 根据Id进行删除
     * @param id
     * @return
     */
    default boolean removeById(Serializable id){
        return getDao().removeById(id);
    }

    /**
     * 根据条件查询并进行删除
     * @return
     */
    default long removeQueryAll(){
        return getDao().removeQueryAll();
    }

}
