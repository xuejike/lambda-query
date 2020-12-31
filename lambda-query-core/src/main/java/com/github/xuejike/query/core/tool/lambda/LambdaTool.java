package com.github.xuejike.query.core.tool.lambda;


import com.github.xuejike.query.core.enums.FieldType;
import com.github.xuejike.query.core.exception.LambdaQueryException;
import com.github.xuejike.query.core.po.FieldInfo;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class LambdaTool {
    public static String methodToProperty(String name) {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else if (name.startsWith("get") || name.startsWith("set")) {
            name = name.substring(3);
        } else {
            throw new LambdaQueryException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
        }

        if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }

        return name;
    }

    protected static Map<Class, WeakReference<FieldInfo>> cache = new ConcurrentHashMap<>();

    public static<T> FieldInfo getFieldInfo(FieldFunction<T,?> fieldFun){
        Class<? extends FieldFunction> cls = fieldFun.getClass();
        return Optional.ofNullable(cache.get(cls))
                .map(WeakReference::get)
                .orElseGet(()->{
                    SerializedLambda lambda = SerializedLambda.resolve(fieldFun);
                    FieldInfo fieldInfo = new FieldInfo();
                    String methodName = lambda.getImplMethodName();
                    String property = methodToProperty(methodName);
                    fieldInfo.setField(property);
                    Method method = null;
                    try {
                        method = lambda.getImplClass().getMethod(methodName);
                        Class<?> returnType = method.getReturnType();
                        if (Collection.class.isAssignableFrom(returnType) || returnType.isArray()){
                            fieldInfo.setType(FieldType.LIST);
                        }

                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }


                    WeakReference<FieldInfo> wr = new WeakReference<>(fieldInfo);
                    cache.put(cls,wr);
                    return fieldInfo;
                });

    }
}
