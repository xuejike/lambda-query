package com.github.xuejike.query.http.service;

import cn.hutool.core.lang.Assert;
import com.github.xuejike.query.core.JLambdaQuery;
import com.github.xuejike.query.core.JQuerys;
import com.github.xuejike.query.core.base.BaseDao;
import com.github.xuejike.query.core.criteria.DaoCriteria;
import com.github.xuejike.query.http.LambdaQueryHttpConfig;
import com.github.xuejike.query.http.vo.HttpBodyVo;

/**
 * @author xuejike
 * @date 2021/1/4
 */
public class HttpServiceParse<T> implements DaoCriteria<T> {
    private String serviceName;
    private HttpBodyVo httpBodyVo;
    private JLambdaQuery<?> lambdaQuery;


    public HttpServiceParse(String serviceName, HttpBodyVo httpBodyVo) {
        this.serviceName = serviceName;
        this.httpBodyVo = httpBodyVo;
        init();
    }
    private void init(){
        Class<?> serviceEntityCls = LambdaQueryHttpConfig.getInstance().getServiceEntityCls(serviceName);
        Assert.notNull(serviceEntityCls,"未发现服务");
        lambdaQuery = JQuerys.lambdaQuery(serviceEntityCls);
        BaseDao baseDao = (BaseDao) lambdaQuery.getDao();
        baseDao.setBaseConditionsVo(httpBodyVo);


    }


    @Override
    public DaoCriteria<T> getDao() {
        return (DaoCriteria<T>) lambdaQuery.getDao();
    }
}
