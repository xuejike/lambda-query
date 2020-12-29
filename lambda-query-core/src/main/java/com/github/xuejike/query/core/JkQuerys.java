package com.github.xuejike.query.core;

import cn.hutool.core.annotation.AnnotationUtil;
import com.github.xuejike.query.core.annotation.DaoSelect;
import com.github.xuejike.query.core.config.DaoFactory;
import com.github.xuejike.query.core.config.JkQueryConfig;
import com.github.xuejike.query.core.criteria.DaoCriteria;

/**
 * @author xuejike
 * @date 2020/12/28
 */
public class JkQuerys {

    public static  <T>JkLambdaQuery<T> lambdaQuery(Class<T> entityCls){
        JkQueryConfig instance = JkQueryConfig.getInstance();
        for (DaoFactory factory : instance.getDaoFactoryList()) {
            DaoSelect annotation = AnnotationUtil.getAnnotation(entityCls, DaoSelect.class);
            if (factory.getDaoCls() == annotation.daoCls()){
                DaoCriteria<T> daoCriteria = factory.createDao(entityCls);
                return new JkLambdaQuery<>(daoCriteria);
            }
        }

        return null;
    }
}
