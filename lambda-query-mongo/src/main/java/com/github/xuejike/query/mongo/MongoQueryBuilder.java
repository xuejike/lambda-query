package com.github.xuejike.query.mongo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.github.xuejike.query.core.base.BaseNestedWhereQuery;
import com.github.xuejike.query.core.enums.WhereOperation;
import com.github.xuejike.query.core.po.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class MongoQueryBuilder {


    public static Query builder(QueryInfo queryInfo) {
        Query query = new Query();

        List<QueryItem> and = queryInfo.getAnd();
        for (QueryItem item : and) {
            Criteria where = Criteria.where(buildField(item.getField()));
            updateCriteria(where,item.getVal());
            query.addCriteria(where);
        }
        if (CollUtil.isNotEmpty(queryInfo.getOr())){
            Criteria criteria = new Criteria();
            Criteria[] criteriaArray = queryInfo.getOr().stream().map(MongoQueryBuilder::orBuildItem).toArray(Criteria[]::new);
            query.addCriteria(criteria.orOperator(criteriaArray));

        }

        return query;
    }


    protected static Criteria orBuildItem(QueryInfo queryInfo){
        if (queryInfo.getAnd().isEmpty() && queryInfo.getOr().isEmpty()){
            return null;
        }
        ArrayList<Criteria> list = new ArrayList<>(queryInfo.getAnd().size() + queryInfo.getOr().size());
        Criteria criteria = new Criteria();

        List<QueryItem> and = queryInfo.getAnd();
        for (QueryItem item : and) {
            Criteria where = Criteria.where(buildField(item.getField()));
            updateCriteria(where,item.getVal());
            list.add(where);
        }

        List<Criteria> orList = queryInfo.getOr().stream().map(MongoQueryBuilder::orBuildItem).collect(Collectors.toList());
        list.addAll(orList);
        criteria.orOperator(list.toArray(new Criteria[0]));
        return criteria;
    }
    public static String buildField(FieldInfo fieldInfo){
        StringBuilder builder = new StringBuilder(fieldInfo.getField());
        for (FieldInfo info : fieldInfo.getSubList()) {
            builder.append(".").append(info.getField());
        }

        return builder.toString();
    }
    protected static void updateCriteria(Criteria criteria, Map<WhereOperation,Object> valMap){
        for (Map.Entry<WhereOperation, Object> entry : valMap.entrySet()) {
            Object value = entry.getValue();
            switch (entry.getKey()){
                case eq:
                    criteria.is(value);
                    break;
                case gt:
                    criteria.gt(value);
                    break;
                case gte:
                    criteria.gte(value);
                    break;
                case lt:
                    criteria.lt(value);
                    break;
                case lte:
                    criteria.lte(value);
                    break;
                case in:
                    if (value instanceof Collection){
                        criteria.in((Collection)value);
                    }else{
                        criteria.in(value);
                    }

                    break;
                case notIn:
                    if (value instanceof Collection){
                        criteria.nin((Collection)value);
                    }else{
                        criteria.nin(value);
                    }

                    break;
                case ne:
                    criteria.ne(value);
                    break;
                case isNull:
                    criteria.is(null);
                    break;
                case notNull:
                    criteria.exists(true);
                    break;
                case like:
                    criteria.regex(regexVal(value));
                    break;
                case between:
                    BetweenObj obj = (BetweenObj) value;
                    criteria.gt(obj.getFirst());
                    criteria.lt(obj.getSecond());
                    break;
                default:
                    break;
            }
        }

    }

    private static String regexVal(Object val){
        if (val instanceof LikeValObj){
            LikeValObj likeValObj = (LikeValObj) val;
            switch (likeValObj.getMatchMode()){
                case END:
                    return likeValObj.getVal()+"*";
                case START:
                    return "*"+likeValObj.getVal();
                case ANYWHERE:
                    return StrUtil.join("*",likeValObj.getVal().toCharArray());
                case EXACT:
                case REGEX:
                default:
                    return likeValObj.getVal();
            }
        }
        return val.toString();
    }

}
