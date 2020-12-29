package com.github.xuejike.query.mybatisplus;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xuejike.query.core.enums.WhereOperation;
import com.github.xuejike.query.core.po.*;

import java.util.Map;

/**
 * @author xuejike
 * @date 2020/12/28
 */
public class MyBatisPlusBuilder {
    public static<T> QueryWrapper<T> build(QueryInfo queryInfo ){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        for (QueryItem item : queryInfo.getAnd()) {
            buildField(queryWrapper, item);
        }
        for (QueryInfo info : queryInfo.getOr()) {
//            info.getAnd();
        }


        return queryWrapper;
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
                    queryWrapper.notIn(field,value);
                    break;
                case in:
                    queryWrapper.in(field,value);
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
        StringBuilder builder = new StringBuilder(fieldInfo.getField());
        for (FieldInfo info : fieldInfo.getSubList()) {
            builder.append(".").append(info.getField());
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
