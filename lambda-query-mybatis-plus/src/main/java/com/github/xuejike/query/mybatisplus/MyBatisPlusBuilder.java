package com.github.xuejike.query.mybatisplus;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.SimpleCache;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xuejike.query.core.enums.WhereOperation;
import com.github.xuejike.query.core.exception.LambdaQueryException;
import com.github.xuejike.query.core.po.*;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author xuejike
 * @date 2020/12/28
 */
public class MyBatisPlusBuilder {
    static SimpleCache<Class<?>,Map<String,String>> entityMap = new SimpleCache<Class<?>,Map<String,String>>();

    public static<T> QueryWrapper<T> build(Class<?> entityCls,QueryInfo queryInfo ){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        build(entityCls,queryWrapper,queryInfo);


        return queryWrapper;
    }
    public static<T> void build(Class<?> entityCls,QueryWrapper<T> queryWrapper,QueryInfo info){
        for (QueryItem item : info.getAnd()) {
            buildField(entityCls,queryWrapper,item);
        }
        if (CollUtil.isNotEmpty(info.getOr())){
            for (QueryInfo queryInfo : info.getOr()) {
                if (CollUtil.isNotEmpty(queryInfo.getOr()) || CollUtil.isNotEmpty(queryInfo.getAnd())){
                    queryWrapper.or(or->{
                        build(entityCls,or,queryInfo);
                    });
                }
            }
        }
    }

    private static <T> void buildField(Class<?> entityCls,QueryWrapper<T> queryWrapper, QueryItem item) {
        String field = buildField(entityCls,item.getField());

        for (Map.Entry<WhereOperation, Object> entry : item.getVal().entrySet()) {
            Object value = entry.getValue();
            switch (entry.getKey()){
                case eq:{
                    queryWrapper.eq(field, getValue(entityCls,item.getField(),value));
                    break;
                }
                case lt:
                    queryWrapper.lt(field, getValue(entityCls,item.getField(),value));
                    break;
                case lte:
                    queryWrapper.le(field, getValue(entityCls,item.getField(),value));
                    break;
                case gt:
                    queryWrapper.gt(field,getValue(entityCls,item.getField(),value));
                    break;
                case gte:
                    queryWrapper.ge(field,getValue(entityCls,item.getField(),value));
                    break;
                case between:
                    BetweenObj betweenObj = (BetweenObj) value;
                    queryWrapper.between(field,getValue(entityCls,item.getField(),betweenObj.getFirst()),
                            getValue(entityCls,item.getField(),betweenObj.getSecond()));
                    break;
                case ne:
                    queryWrapper.ne(field,getValue(entityCls,item.getField(),value));
                    break;
                case isNull:
                    queryWrapper.isNull(field);
                    break;
                case notNull:
                    queryWrapper.isNotNull(field);
                    break;
                case notIn:
                    if (value instanceof Collection){
                        queryWrapper.notIn(field,getValue(entityCls,item.getField(),getValue(entityCls,item.getField(),(Collection<?>) value)));
                    }else{
                        queryWrapper.notIn(field,getValue(entityCls,item.getField(),value));
                    }
                     break;
                case in:
                    if (value instanceof Collection){
                        queryWrapper.in(field,getValue(entityCls,item.getField(),(Collection<?>) value));
                    }else{
                        queryWrapper.in(field,getValue(entityCls,item.getField(),value));
                    }

                    break;
                case like:
                    queryWrapper.like(field,likeValue(getValue(entityCls,item.getField(),value)));
                    break;
                default:
                    break;
            }
        }
    }

    public static String buildField(Class<?> entityCls,FieldInfo fieldInfo){
        Map<String, String> map = entityMap.get(entityCls);
        if (map == null){
            map = new ConcurrentHashMap<>();
            entityMap.put(entityCls,map);
            Field[] fields = ReflectUtil.getFields(entityCls);
            for (Field field : fields) {
                TableField tableField = AnnotationUtil.getAnnotation(field, TableField.class);
                if (tableField != null && StrUtil.isNotBlank(tableField.value())){
                    map.put(field.getName(),tableField.value());
                }else{
                    map.put(field.getName(),StrUtil.toUnderlineCase(field.getName()));
                }
            }
        }


        StringBuilder builder = new StringBuilder(map.getOrDefault(fieldInfo.getField(),StrUtil.toUnderlineCase(fieldInfo.getField())));
       if (fieldInfo.getSubList().size()>0){
           throw new LambdaQueryException("mybatis 暂不支持 二级数据查询");
       }

        return builder.toString();
    }

    public static Object getValue(Class<?> entityCls,FieldInfo fieldInfo,Object val){
        Field field = ReflectUtil.getField(entityCls, fieldInfo.getField());
        try {
            return  Convert.convert(field.getType(),val);
        }catch (Exception ex){
            return val;
        }

    }
    public static Object getValue(Class<?> entityCls,FieldInfo fieldInfo,Collection<?> val){
        Field field = ReflectUtil.getField(entityCls, fieldInfo.getField());

        return val.stream().map(it-> {
            try {
                return Convert.convert(field.getType(),it);
            }catch (Exception ex){
                return it;
            }
        }).collect(Collectors.toList());
    }
    private static String likeValue(Object val){
        if (val instanceof LikeValObj){
            LikeValObj likeValObj = (LikeValObj) val;
            switch (likeValObj.getMatchMode()){
                case END:
                    return likeValObj.getVal()+"%";
                case START:
                    return "%"+likeValObj.getVal();
                case ANYWHERE:
                    return StrUtil.join("%",likeValObj.getVal().toCharArray());
                case EXACT:
                case REGEX:
                default:
                    return likeValObj.getVal();
            }
        }
        return val.toString();
    }
}
