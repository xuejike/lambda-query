package com.github.xuejike.query.jpa.lambda.tool;

import com.github.xuejike.query.jpa.lambda.core.FieldFunction;
import com.github.xuejike.query.jpa.lambda.exception.JpaLambdaException;

import java.lang.ref.WeakReference;
import java.util.HashMap;
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
            throw new JpaLambdaException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
        }

        if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }

        return name;
    }

    protected static Map<Class, WeakReference<String>> cache = new ConcurrentHashMap<>();
    public static<T> String getName(FieldFunction<T,?> fieldFun){
        Class<? extends FieldFunction> cls = fieldFun.getClass();
        return Optional.ofNullable(cache.get(cls))
                .map(WeakReference::get)
                .orElseGet(()->{
                    SerializedLambda lambda = SerializedLambda.resolve(fieldFun);
                    String methodName = lambda.getImplMethodName();
                    String property = methodToProperty(methodName);
                    WeakReference<String> wr = new WeakReference<>(property);
                    cache.put(cls,wr);
                    return property;
                });

    }
}
