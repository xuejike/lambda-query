package com.github.xuejike.query.http.service;

import cn.hutool.core.lang.Assert;
import com.github.xuejike.query.core.JkLambdaQuery;
import com.github.xuejike.query.core.JkQuerys;
import com.github.xuejike.query.core.base.BaseDao;
import com.github.xuejike.query.core.criteria.DaoCriteria;
import com.github.xuejike.query.core.po.QueryInfo;
import com.github.xuejike.query.core.po.QueryItem;
import com.github.xuejike.query.core.tool.lambda.CascadeField;
import com.github.xuejike.query.http.LambdaQueryHttpConfig;
import com.github.xuejike.query.http.vo.HttpBodyVo;

import java.util.List;

/**
 * @author xuejike
 * @date 2021/1/4
 */
public class HttpServiceParse<T> implements DaoCriteria<T> {
    private String serviceName;
    private HttpBodyVo httpBodyVo;
    private JkLambdaQuery<?> lambdaQuery;


    public HttpServiceParse(String serviceName, HttpBodyVo httpBodyVo) {
        this.serviceName = serviceName;
        this.httpBodyVo = httpBodyVo;
        init();
    }
    private void init(){
        Class<?> serviceEntityCls = LambdaQueryHttpConfig.getInstance().getServiceEntityCls(serviceName);
        Assert.notNull(serviceEntityCls,"未发现服务");
        lambdaQuery = JkQuerys.lambdaQuery(serviceEntityCls);
        BaseDao baseDao = (BaseDao) lambdaQuery.getDao();
        baseDao.setBaseConditionsVo(httpBodyVo);


    }


    @Override
    public DaoCriteria<T> getDao() {
        return (DaoCriteria<T>) lambdaQuery.getDao();
    }
}
