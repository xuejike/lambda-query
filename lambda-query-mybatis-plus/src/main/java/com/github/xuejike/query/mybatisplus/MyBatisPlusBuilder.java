package com.github.xuejike.query.mybatisplus;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xuejike.query.core.enums.WhereOperation;
import com.github.xuejike.query.core.exception.LambdaQueryException;
import com.github.xuejike.query.core.po.*;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author xuejike
 * @date 2020/12/28
 */
public class MyBatisPlusBuilder {
    public static<T> QueryWrapper<T> build(QueryInfo queryInfo ){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        build(queryWrapper,queryInfo);


        return queryWrapper;
    }
    public static<T> void build(QueryWrapper<T> queryWrapper,QueryInfo info){
        for (QueryItem item : info.getAnd()) {
            buildField(queryWrapper,item);
        }
        if (CollUtil.isNotEmpty(info.getOr())){
            for (QueryInfo queryInfo : info.getOr()) {
                if (CollUtil.isNotEmpty(queryInfo.getOr()) || CollUtil.isNotEmpty(queryInfo.getAnd())){
                    queryWrapper.or(or->{
                        build(or,queryInfo);
                    });
                }
            }
        }
    }

    private static <T> void buildField(QueryWrapper<T> queryWrapper, QueryItem item) {
        String field = buildField(item.getField());

        for (Map.Entry<WhereOperation, Object> entry : item.getVal().entrySet()) {
            Object value = entry.getValue();
            switch (entry.getKey()){
                case eq:{
                    queryWrapper.eq(field, value);
                    break;
                }
                case lt:
                    queryWrapper.lt(field, value);
                    break;
                case lte:
                    queryWrapper.le(field, value);
                    break;
                case gt:
                    queryWrapper.gt(field,value);
                    break;
                case gte:
                    queryWrapper.ge(field,value);
                    break;
                case between:
                    BetweenObj betweenObj = (BetweenObj) value;
                    queryWrapper.between(field,betweenObj.getFirst(),betweenObj.getSecond());
                    break;
                case ne:
                    queryWrapper.ne(field,value);
                    break;
                case isNull:
                    queryWrapper.isNull(field);
                    break;
                case notNull:
                    queryWrapper.isNotNull(field);
                    break;
                case notIn:
                    if (value instanceof Collection){
                        queryWrapper.notIn(field,(Collection<?>) value);
                    }else{
                        queryWrapper.notIn(field,value);
                    }
                     break;
                case in:
                    if (value instanceof Collection){
                        queryWrapper.in(field,(Collection<?>) value);
                    }else{
                        queryWrapper.in(field,value);
                    }

                    break;
                case like:
                    queryWrapper.like(field,likeValue(value));
                    break;
                default:
                    break;
            }
        }
    }

    public static String buildField(FieldInfo fieldInfo){
        // TODO: 2020/12/30 对注解自定义列处理
        StringBuilder builder = new StringBuilder(fieldInfo.getField());
       if (fieldInfo.getSubList().size()>0){
           throw new LambdaQueryException("mybatis 暂不支持 二级数据查询");
       }

        return builder.toString();
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
