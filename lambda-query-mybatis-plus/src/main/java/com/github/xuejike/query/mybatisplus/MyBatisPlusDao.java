package com.github.xuejike.query.mybatisplus;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xuejike.query.core.base.BaseDao;
import com.github.xuejike.query.core.criteria.DaoCriteria;
import com.github.xuejike.query.core.criteria.IJPage;
import com.github.xuejike.query.core.enums.OrderType;
import com.github.xuejike.query.core.po.FieldInfo;
import com.github.xuejike.query.core.po.QueryInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xuejike
 * @date 2020/12/28
 */
public class MyBatisPlusDao<T> extends BaseDao<T> {
    BaseMapper<T> baseMapper;


    public MyBatisPlusDao(BaseMapper<T> baseMapper, Class<T> entityCls) {
        super(entityCls);
        this.baseMapper = baseMapper;

    }
    protected QueryWrapper<T> buildQuery(){
        QueryInfo queryInfo = baseWhereQuery.buildQueryInfo();
        QueryWrapper<T> build = MyBatisPlusBuilder.build(queryInfo);
        if (CollUtil.isNotEmpty(baseWhereQuery.getSelectList())){
            List<FieldInfo> selectList = baseWhereQuery.getSelectList();
            build.select(selectList.stream().map(MyBatisPlusBuilder::buildField).toArray(String[]::new));
        }
        if (CollUtil.isNotEmpty(baseWhereQuery.getExcludeList())){
            List<FieldInfo> excludeList = baseWhereQuery.getExcludeList();
            // TODO: 2020/12/30 排除
        }
        if (CollUtil.isNotEmpty(baseWhereQuery.getOrderMap())){
            Map<FieldInfo, OrderType> orderMap = baseWhereQuery.getOrderMap();
            orderMap.entrySet().stream().forEach(it->{
                if (it.getValue() == OrderType.desc){
                    build.orderByDesc(MyBatisPlusBuilder.buildField(it.getKey()));
                }else{
                    build.orderByAsc(MyBatisPlusBuilder.buildField(it.getKey()));
                }
            });
        }
        return build;
    }
    @Override
    public DaoCriteria<T> getDao() {
        return this;
    }

    @Override
    public List<T> list() {
        QueryWrapper<T> query = buildQuery();
        return baseMapper.selectList(query) ;
    }

    @Override
    public Long count() {
        QueryWrapper<T> query = buildQuery();
        return Long.valueOf(baseMapper.selectCount(query));
    }

    @Override
    public IJPage<T> page(IJPage<T> page) {
        QueryWrapper<T> query = buildQuery();
        Page<T> tPage = baseMapper.selectPage(new Page<>(page.getPageNo(), page.getPageSize(), page.isHaveTotal()), query);
        page.setData(tPage.getRecords());
        page.setTotal(tPage.getTotal());
        return page;
    }

    @Override
    public T findById(Serializable id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean updateById(T entity) {
        int i = baseMapper.updateById(entity);
        if (i > 0){
            return true;
        }
        return false;
    }

    @Override
    public T insert(T entity) {
        int insert = baseMapper.insert(entity);
        return entity;
    }

    @Override
    public Long updateFindAll() {
//        baseMapper.update()
        return null;
    }

    @Override
    public boolean removeById(Serializable id) {
        int i = baseMapper.deleteById(id);
        if (i > 0){
            return true;
        }
        return false;
    }

    @Override
    public long removeQueryAll() {
        QueryWrapper<T> query = buildQuery();
        int delete = baseMapper.delete(query);
        return delete;
    }


}
