package com.github.xuejike.query.jpa.lambda.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by xuejike on 2017/3/10.
 */
public class GeterSeterMethodInterceptor implements MethodInterceptor {
    protected String lastPropertyName;
    protected Class lastPropertyClass;

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object val = methodProxy.invokeSuper(o, objects);
        String methodName = method.getName();

        if (PropertyNameTool.isSeter(methodName)){
            // 是 seter 方法
            String property = PropertyNameTool.getProperty(methodName);

            lastPropertyName=property;

            lastPropertyClass=method.getParameterTypes()[0];

            execSeterMethod(o,method,property,objects[0]);
        }else if (PropertyNameTool.isGeter(methodName)){
            // 是geter 方法
            String property = PropertyNameTool.getProperty(methodName);
            lastPropertyName=property;
            lastPropertyClass=method.getReturnType();
           return execGeterMethod(o,method,property,val);
        }else{
            // 都不是
            notIsGeterSeter(o,method,objects,val);
        }

        return val;
    }

    public  Object execGeterMethod(Object obj, Method method, String property, Object val){
        return val;
    }
    public  void execSeterMethod(Object obj,Method method,String property,Object val){

    }
    public void notIsGeterSeter(Object obj,Method method,Object[] params,Object retVal){

    }

    public String getLastPropertyName() {
        return lastPropertyName;
    }

    public void setLastPropertyName(String lastPropertyName) {
        this.lastPropertyName = lastPropertyName;
    }

    public Class getLastPropertyClass() {
        return lastPropertyClass;
    }

    public void setLastPropertyClass(Class lastPropertyClass) {
        this.lastPropertyClass = lastPropertyClass;
    }
}
