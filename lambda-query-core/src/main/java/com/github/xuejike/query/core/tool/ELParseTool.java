package com.github.xuejike.query.core.tool;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LFUCache;
import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.SimpleCache;
import cn.hutool.core.lang.func.Func0;
import cn.hutool.core.util.ReflectUtil;
import com.github.xuejike.query.core.annotation.RefValue;
import com.github.xuejike.query.core.annotation.SetRefValue;
import com.github.xuejike.query.core.exception.LambdaQueryException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuejike
 * @date 2020/12/31
 */
public class ELParseTool {
    static ExpressionParser spelExpressionParser = new SpelExpressionParser();

    static LFUCache<String, Expression> expCache = CacheUtil.newLFUCache(300);
    static SimpleCache<String,String> setRefValueCache = new SimpleCache<>();
    static SimpleCache<Class<?>,Map<Field,String>> refValueCache = new SimpleCache<>();

    public static<T> T parseEl(String expStr, Map<String,Object> varMap,Class<T> returnCls){
        EvaluationContext context = new StandardEvaluationContext();
        for (Map.Entry<String, Object> entry : varMap.entrySet()) {
            context.setVariable(entry.getKey(),entry.getValue());
        }

        Expression expression = expCache.get(expStr, () -> spelExpressionParser.parseExpression(expStr));
        return expression.getValue(context,returnCls);
    }



    public static String getSetRefValue(Class<?> voCls,String fieldName){
        return setRefValueCache.get(voCls.getName() + "_" + fieldName, () -> {
            Field field = ReflectUtil.getField(voCls, fieldName);
            SetRefValue annotation = AnnotationUtil.getAnnotation(field, SetRefValue.class);
            if (annotation == null){
                throw new LambdaQueryException("{} -> {} 未设置 @SetRefValue",voCls.getName(),fieldName);
            }
            return annotation.value();
        });
    }
    public static Map<Field,String> getRefValues(Class<?> voCls){
        return refValueCache.get(voCls, () -> {
            Field[] fields = ReflectUtil.getFields(voCls);
            ConcurrentHashMap<Field, String> map = new ConcurrentHashMap<>(fields.length);
            for (Field field : fields) {
                RefValue refValue = AnnotationUtil.getAnnotation(field, RefValue.class);
                if (refValue != null){
                    field.setAccessible(true);
                    map.put(field,refValue.value());
                }
            }

            return map;
        });
    }

    public static void parseRefValue(Class<?> voCls){
        Field[] fields = ReflectUtil.getFields(voCls);
        Map<String, String> fieldElMap = new ConcurrentHashMap<>(5);
        for (Field field : fields) {
            RefValue value = AnnotationUtil.getAnnotation(field, RefValue.class);
            if (value != null){
                fieldElMap.put(field.getName(),value.value());
            }
        }
    }


}
