package com.github.xuejike.query.core.po;

import com.github.xuejike.query.core.enums.LoadRefMode;
import com.github.xuejike.query.core.tool.lambda.FieldFunction;
import lombok.Data;

/**
 * @author xuejike
 * @date 2020/12/31
 */
@Data
public class LoadRefInfo<T> {
    private Class<T> refClass;
    private LoadRefMode mode;
    private FieldInfo targetField;
}
