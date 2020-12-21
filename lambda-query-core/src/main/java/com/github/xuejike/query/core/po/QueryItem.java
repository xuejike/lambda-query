package com.github.xuejike.query.core.po;

import com.github.xuejike.query.core.enums.WhereOperation;
import lombok.Data;

import java.util.Map;

/**
 * @author xuejike
 * @date 2020/12/21
 */
@Data
public class QueryItem {
    private FieldInfo field;
    private Map<WhereOperation,Object> val;

}
