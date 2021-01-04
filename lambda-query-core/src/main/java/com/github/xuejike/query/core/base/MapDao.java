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
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xuejike
 * @date 2020/12/31
 */
@Slf4j
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
        log.debug("loadRefMap 耗时统计:加载引用对象{}:",refClassMap.size());
        long begin = System.currentTimeMillis();
        Map<String, ? extends Map<Object, ?>> refMap = refClassMap.entrySet().parallelStream().collect(Collectors
                .toMap(it -> it.getKey().getField(), entry -> {
            Set<Object> refIdList = list.stream().map(it -> ReflectUtil.getFieldValue(it, entry.getKey().getField())).collect(Collectors.toSet());
            List<?> refList = JkQuerys.lambdaQuery(entry.getValue().getRefClass())
                    .in(new CascadeField().subFieldName(entry.getValue().getTargetField().getField()), refIdList)
                    .list();
            Map<Object, ?> map = refList.stream().collect(Collectors.toMap(it -> ReflectUtil.getFieldValue(it, entry.getValue().getTargetField().getField()), it -> it));
                    log.debug("loadRefMap 耗时统计:关联属性{},加载耗时:{}",entry.getKey().getField(),System.currentTimeMillis()-begin);
            return map;
        }));
        log.debug("loadRefMap 耗时统计:关联属性加载总耗时:{}",System.currentTimeMillis()-begin);
        long elBegin = System.currentTimeMillis();
        List<R> collect = list.parallelStream().map(item -> {
            R r = BeanUtil.copyProperties(item, resultCls);
            HashMap<String, Object> varMap = new HashMap<>();
            for (Map.Entry<String, ? extends Map<Object, ?>> entry : refMap.entrySet()) {
                String varName = ELParseTool.getSetRefValue(resultCls, entry.getKey());
                Object varValue = entry.getValue().get(ReflectUtil.getFieldValue(item, entry.getKey()));
                varMap.put(varName,varValue);
            }
            Map<Field, String> fieldStringMap = ELParseTool.getRefValues(resultCls);
            fieldStringMap.entrySet().parallelStream().forEach(entry -> {
                ReflectUtil.setFieldValue(r, entry.getKey(), ELParseTool.parseEl(entry.getValue(), varMap, entry.getKey().getType()));
            });

            return r;
        }).collect(Collectors.toList());
        log.debug("loadRefMap 耗时统计:合并数据总耗时:{}",System.currentTimeMillis()-elBegin);
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
