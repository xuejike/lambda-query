package com.github.xuejike.query.mongo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.xuejike.query.core.base.BaseNestedWhereQuery;
import com.github.xuejike.query.core.enums.WhereOperation;
import com.github.xuejike.query.core.po.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

        return query;
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
            switch (entry.getKey()){
                case eq:
                    criteria.is(entry.getValue());
                    break;
                case gt:
                    criteria.gt(entry.getValue());
                    break;
                case gte:
                    criteria.gte(entry.getValue());
                    break;
                case lt:
                    criteria.lt(entry.getValue());
                    break;
                case lte:
                    criteria.lte(entry.getValue());
                    break;
                case in:
                    criteria.in(entry.getValue());
                    break;
                case notIn:
                    criteria.nin(entry.getValue());
                    break;
                case ne:
                    criteria.ne(entry.getValue());
                    break;
                case isNull:
                    criteria.is(null);
                    break;
                case notNull:
                    criteria.exists(true);
                    break;
                case like:
                    criteria.regex(regexVal(entry.getValue()));
                    break;
                case between:
                    BetweenObj obj = (BetweenObj) entry.getValue();
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
