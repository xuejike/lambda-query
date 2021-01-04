package com.github.xuejike.query.core.po;

import com.github.xuejike.query.core.enums.OrderType;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuejike
 * @date 2020/12/31
 */
@Data
public class BaseConditionsVo {
    protected QueryInfo where =new QueryInfo() ;
    protected List<FieldInfo> selectList = new LinkedList<>();
    protected List<FieldInfo> excludeList = new LinkedList<>();
    protected Map<FieldInfo, OrderType> orderMap = new ConcurrentHashMap<>();
    protected Map<FieldInfo, LoadRefInfo<?>> refClassMap = new ConcurrentHashMap<>();

}
