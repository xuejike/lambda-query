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

    private final List<FieldInfo> parentList = new LinkedList<>();

    public CascadeField() {

    }
    public CascadeField(CascadeField<?,?> cascadeField){
        parentList.addAll(cascadeField.parentList);
    }
    public CascadeField(CascadeField<?,?> cascadeField,FieldInfo... fieldInfo){
        parentList.addAll(cascadeField.parentList);
        CollUtil.addAll(parentList,fieldInfo);
    }

    public<S> CascadeField<S,T> sub(FieldFunction<P,S> field){
        return new CascadeField<S,T>(this,new FieldInfo(field));
    }
    public<S> CascadeField<S,T> subList(FieldFunction<P,List<S>> field){
        return new CascadeField<S,T>(this,new FieldInfo(field, FieldType.LIST));
    }
    public<S> CascadeField<S,T> subClass(FieldFunction<P,?> field, Class<S> cls){
        return new CascadeField<S,T>(this,new FieldInfo(field));
    }
    public  CascadeField<String,T> subFieldName(String sub){
        return new CascadeField<String,T>(this,new FieldInfo(sub));
    }
    public  CascadeField<String,T> subFieldName(String sub, FieldType fieldType){
        return new CascadeField<String,T>(this,new FieldInfo(sub,fieldType));
    }
    @Override
    public String apply(T entity) {

        return null;
    }


}
