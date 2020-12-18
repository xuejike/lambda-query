package com.github.xuejike.query.core.po;

import com.github.xuejike.query.core.enums.FieldType;
import com.github.xuejike.query.core.tool.lambda.FieldFunction;
import lombok.Data;

/**
 * @author xuejike
 * @date 2020/12/18
 */
@Data
public class FieldInfo {
    private String field;
    private FieldFunction<?,?> fieldFunction;
    private FieldType type = FieldType.OBJECT;



    public FieldInfo(String field) {
        this.field = field;
    }

    public FieldInfo(FieldFunction<?, ?> fieldFunction) {
        this.fieldFunction = fieldFunction;
    }

    public FieldInfo(String field, FieldType type) {
        this.field = field;
        this.type = type;
    }

    public FieldInfo(FieldFunction<?, ?> fieldFunction, FieldType type) {
        this.fieldFunction = fieldFunction;
        this.type = type;
    }

}
