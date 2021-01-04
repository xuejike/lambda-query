package com.github.xuejike.query.http.client;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.xuejike.query.core.base.BaseDao;
import com.github.xuejike.query.core.criteria.IJPage;
import com.github.xuejike.query.core.exception.LambdaQueryException;
import com.github.xuejike.query.http.LambdaQueryHttpConfig;
import com.github.xuejike.query.http.annotation.HttpDaoSelect;
import com.github.xuejike.query.http.vo.HttpBodyVo;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuejike
 * @date 2020/12/31
 */
public class HttpClientDao<T> extends BaseDao<T> {

    private final String server;

    public HttpClientDao(Class<T> entityCls) {
        super(entityCls);
        HttpDaoSelect daoSelect = AnnotationUtil.getAnnotation(entityCls, HttpDaoSelect.class);
        server = daoSelect.serverAddress()+"/"+daoSelect.path();

    }


    protected String httpPost(String method, Map<String,Object> param){
        HttpClient httpClient = LambdaQueryHttpConfig.getInstance().getHttpClient();
        String post = httpClient.post(server + "/" + method, param, JSON.toJSONString(buildHttpBody()));
        return post;
    }

    protected HttpBodyVo buildHttpBody(){
        HttpBodyVo bodyVo = new HttpBodyVo();
        bodyVo.setExcludeList(baseWhereQuery.getExcludeList());
        bodyVo.setSelectList(baseWhereQuery.getSelectList());
        bodyVo.setOrderMap(baseWhereQuery.getOrderMap());
        bodyVo.setWhere(baseWhereQuery.buildQueryInfo());

        return bodyVo;
    }

    @Override
    public List<T> list() {

        String list = httpPost("list", new HashMap<>(0));
        return JSON.parseArray(list,entityCls);
    }

    @Override
    public T getFirst() {
        String list = httpPost("first", new HashMap<>(0));
        return JSON.parseObject(list,entityCls);
    }


    @Override
    public Long count() {
        String list = httpPost("count", new HashMap<>(0));
        return Convert.toLong(list);
    }

    @Override
    public IJPage<T> page(IJPage<T> page) {
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("pageNo",page.getPageNo());
        map.put("pageSize",page.getPageSize());
        map.put("haveTotal",page.isHaveTotal());
        String list = httpPost("page",map);

        return JSON.parseObject(list,new TypeReference<IJPage<T>>(){});
    }

    @Override
    public T findById(Serializable id) {
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("id",id);


        String list = httpPost("findById",map);

        return JSON.parseObject(list,entityCls);
    }


    @Override
    public boolean updateById(T entity) {
        throw new LambdaQueryException("http 不支持 updateById");
    }

    @Override
    public T insert(T entity) {
        throw new LambdaQueryException("http 不支持 insert");
    }

    @Override
    public Long updateFindAll() {
        throw new LambdaQueryException("http 不支持 updateFindAll");
    }

    @Override
    public boolean removeById(Serializable id) {
        throw new LambdaQueryException("http 不支持 removeById");
    }

    @Override
    public long removeQueryAll() {
        throw new LambdaQueryException("http 不支持 removeQueryAll");
    }
}
