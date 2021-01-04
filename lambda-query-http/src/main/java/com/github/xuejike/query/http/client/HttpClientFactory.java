package com.github.xuejike.query.http.client;

import com.github.xuejike.query.core.base.BaseDao;
import com.github.xuejike.query.core.config.DaoFactory;

/**
 * @author xuejike
 * @date 2020/12/31
 */
public class HttpClientFactory extends DaoFactory {
    public HttpClientFactory() {
        super(HttpClientDao.class);
    }

    @Override
    public <T> BaseDao<T> createDao(Class<T> entityCls) {
        return new HttpClientDao<>(entityCls);
    }
}
