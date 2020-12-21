package com.github.xuejike.query.core.tool.lambda;

import cn.hutool.core.collection.CollUtil;
import com.github.xuejike.query.core.enums.FieldType;
import com.github.xuejike.query.core.po.FieldInfo;

import java.util.LinkedList;
import java.util.List;

/**
 * lambda链式属性多级
 * @author xuejike
 * @date 2020/12/18
 */
public class CascadeField<P,T> implements FieldFunction<T,String>{

    private FieldInfo fieldInfo;

    public CascadeField() {

    }
    public CascadeField(CascadeField<?,?> cascadeField){
        fieldInfo = cascadeField.fieldInfo;
    }

    public<S> CascadeField<S,T> sub(FieldFunction<P,S> field){
        addSetFieldInfo(new FieldInfo(field));
        return new CascadeField<S,T>(this);
    }
    public<S> CascadeField<S,T> subList(FieldFunction<P,List<S>> field){
        addSetFieldInfo(new FieldInfo(field,FieldType.LIST));
        return new CascadeField<S,T>(this);
    }
    public<S> CascadeField<S,T> subClass(FieldFunction<P,?> field, Class<S> cls){
        addSetFieldInfo(new FieldInfo(field));
        return new CascadeField<S,T>(this);
    }
    public  CascadeField<String,T> subFieldName(String sub){
        addSetFieldInfo(new FieldInfo(sub));
        return new CascadeField<String,T>(this);
    }
    public  CascadeField<String,T> subFieldName(String sub, FieldType fieldType){
        addSetFieldInfo(new FieldInfo(sub,fieldType));
        return new CascadeField<String,T>(this);
    }
    private CascadeField<P,T> addSetFieldInfo(FieldInfo info){

        if (this.fieldInfo == null){
            this.fieldInfo = info;
        }else{
            this.fieldInfo.getSubList().add(info);
        }
        return this;
    }
    @Override
    public String apply(T entity) {
        //
        return null;
    }

    public FieldInfo getFieldInfo() {
        return fieldInfo;
    }
}
