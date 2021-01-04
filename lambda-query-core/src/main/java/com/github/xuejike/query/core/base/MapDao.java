package com.github.xuejike.query.core.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ReflectUtil;
import com.github.xuejike.query.core.JkQuerys;
import com.github.xuejike.query.core.criteria.DaoCriteria;
import com.github.xuejike.query.core.criteria.GetDaoCriteria;
import com.github.xuejike.query.core.criteria.IJPage;
import com.github.xuejike.query.core.criteria.SelectDaoCriteria;
import com.github.xuejike.query.core.po.FieldInfo;
import com.github.xuejike.query.core.po.JPage;
import com.github.xuejike.query.core.po.LoadRefInfo;
import com.github.xuejike.query.core.tool.ELParseTool;
import com.github.xuejike.query.core.tool.lambda.CascadeField;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xuejike
 * @date 2020/12/31
 */
public class MapDao<T,R> implements SelectDaoCriteria<R>, GetDaoCriteria<T> {

    private BaseDao<T> daoCriteria;
    private Class<R> resultCls;

    public MapDao(BaseDao<T> daoCriteria, Class<R> resultCls) {
        this.daoCriteria = daoCriteria;
        this.resultCls = resultCls;
    }

    @Override
    public DaoCriteria<T> getDao() {
        return daoCriteria;
    }

    @Override
    public List<R> list() {
        List<T> list = getDao().list();
        List<R> collect = loadRefMap(list);

        return collect;
    }

    private List<R> loadRefMap(List<T> list) {
        Map<FieldInfo, LoadRefInfo<?>> refClassMap = daoCriteria.getRefClassMap();
        HashMap<String, Map<Object, ?>> refMap = new HashMap<>(refClassMap.size());
        for (Map.Entry<FieldInfo, LoadRefInfo<?>> entry : refClassMap.entrySet()) {
            Set<Object> refIdList = list.stream().map(it -> ReflectUtil.getFieldValue(it, entry.getKey().getField())).collect(Collectors.toSet());
            List<?> refList = JkQuerys.lambdaQuery(entry.getValue().getRefClass())
                    .in(new CascadeField().subFieldName(entry.getValue().getTargetField().getField()), refIdList)
                    .list();
            Map<Object, ?> map = refList.stream().collect(Collectors.toMap(it -> ReflectUtil.getFieldValue(it, entry.getValue().getTargetField().getField()), it -> it));
            refMap.put(entry.getKey().getField(),map);
        }


        List<R> collect = list.parallelStream().map(item -> {
            R r = BeanUtil.copyProperties(item, resultCls);

            Map<String, Object> varMap = refMap.entrySet().parallelStream()
                    .collect(Collectors.toMap(
                            entry -> ELParseTool.getSetRefValue(resultCls, entry.getKey()),
                            entry -> entry.getValue().get(ReflectUtil.getFieldValue(item, entry.getKey()))));

            Map<Field, String> fieldStringMap = ELParseTool.getRefValues(resultCls);
            fieldStringMap.entrySet().parallelStream().forEach(entry -> {
                ReflectUtil.setFieldValue(r, entry.getKey(), ELParseTool.parseEl(entry.getValue(), varMap, entry.getKey().getType()));
            });

            return r;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public R getFirst() {
        T first = getDao().getFirst();
        if (first == null){
            return null;
        }
        List<R> rs = loadRefMap(ListUtil.of(first));
        return rs.get(0);
    }

    @Override
    public Optional<R> getFirstStream() {
        return Optional.ofNullable(getFirst());
    }

    @Override
    public IJPage<R> page(IJPage<R> page) {
        IJPage<T> rPage = getDao().page(new JPage<>(page.getPageNo(), page.getPageSize(), page.isHaveTotal()));
        List<R> rs = loadRefMap(rPage.getData());
        page.setData(rs);
        page.setTotal(rPage.getTotal());
        return page;
    }

    @Override
    public R findById(Serializable id) {
        T rs = getDao().findById(id);
        if (rs == null){
            return null;
        }
        List<R> list = loadRefMap(ListUtil.of(rs));
        return list.get(0);
    }

    @Override
    public Optional<R> findByIdStream(Serializable id) {
        return Optional.ofNullable(findById(id));
    }
}
