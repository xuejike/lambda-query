package com.github.xuejike.query.core;

import cn.hutool.core.annotation.AnnotationUtil;
import com.github.xuejike.query.core.annotation.DaoSelect;
import com.github.xuejike.query.core.base.BaseDao;
import com.github.xuejike.query.core.config.DaoFactory;
import com.github.xuejike.query.core.config.JkQueryConfig;
import com.github.xuejike.query.core.exception.LambdaQueryException;

/**
 * @author xuejike
 * @date 2020/12/28
 */
public class JQuerys {

    public static  <T> JLambdaQuery<T> lambdaQuery(Class<T> entityCls){
        JkQueryConfig instance = JkQueryConfig.getInstance();
        for (DaoFactory factory : instance.getDaoFactoryList()) {
            DaoSelect annotation = AnnotationUtil.getAnnotation(entityCls, DaoSelect.class);
            if (factory.getDaoCls() == annotation.daoCls()){
                BaseDao<T> daoCriteria = factory.createDao(entityCls);
                return new JLambdaQuery<>(daoCriteria);
            }
        }

        throw new LambdaQueryException("not find dao {}",entityCls.getName());
    }
}
