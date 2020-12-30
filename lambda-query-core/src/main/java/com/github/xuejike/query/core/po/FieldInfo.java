package com.github.xuejike.query.core.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.xuejike.query.core.enums.FieldType;
import com.github.xuejike.query.core.tool.lambda.FieldFunction;
import com.github.xuejike.query.core.tool.lambda.LambdaTool;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xuejike
 * @date 2020/12/18
 */
@Data
public class FieldInfo implements Serializable {
    private String field;
    @JSONField(serialize = false)
    private FieldFunction<?,?> fieldFunction;
    private FieldType type = FieldType.OBJECT;
    private List<FieldInfo> subList = new LinkedList<>();


    public FieldInfo() {
    }

    public FieldInfo(String field) {
        this.field = field;
    }

    public FieldInfo(FieldFunction<?, ?> fieldFunction) {
        this.fieldFunction = fieldFunction;
        buildInfo(fieldFunction);
    }

    public FieldInfo(String field, FieldType type) {
        this.field = field;
        this.type = type;
    }

    public FieldInfo(FieldFunction<?, ?> fieldFunction, FieldType type) {
        this.fieldFunction = fieldFunction;
        buildInfo(fieldFunction);

    }

    private void buildInfo(FieldFunction<?, ?> fieldFunction) {
        FieldInfo info = LambdaTool.getFieldInfo(fieldFunction);
        this.field = info.field;
        this.type = info.type;
    }


}
