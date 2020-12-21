package com.github.xuejike.query.core.po;

import lombok.Data;

import java.util.List;

/**
 * @author xuejike
 * @date 2020/12/21
 */
@Data
public class QueryInfo {
    private List<QueryItem> and;
    private List<QueryInfo> or;
}
