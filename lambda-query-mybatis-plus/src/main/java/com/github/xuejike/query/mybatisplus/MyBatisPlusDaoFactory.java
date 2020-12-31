package com.github.xuejike.query.mybatisplus;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.TypeUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xuejike.query.core.base.BaseDao;
import com.github.xuejike.query.core.config.DaoFactory;
import com.github.xuejike.query.core.config.JkQueryConfig;
import com.github.xuejike.query.core.criteria.DaoCriteria;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuejike
 * @date 2020/12/28
 */
public class MyBatisPlusDaoFactory extends DaoFactory {
    private Map<Class<?>,BaseMapper<?>> mapperMap = new ConcurrentHashMap<>();
    private List<BaseMapper> mapperList;

    public MyBatisPlusDaoFactory(Collection<BaseMapper> mapperList) {
        super(MyBatisPlusDao.class);
        this.mapperList = CollUtil.newArrayList(mapperList);
        init();

    }
    public void init(){
        JkQueryConfig.getInstance().addDaoFactory(this);
    }


    @Override
    public <T> BaseDao<T> createDao(Class<T> entityCls) {
        if (!mapperMap.containsKey(entityCls)){
            for (BaseMapper mapper : mapperList) {
                Type[] genericInterfaces = mapper.getClass().getGenericInterfaces();
                Type type = genericInterfaces[0];
                Type argument = TypeUtil.getTypeArgument(type);
                if (argument.getTypeName().equals(entityCls.getTypeName())){
                    mapperMap.put(entityCls,mapper);
                }
            }
        }

        BaseMapper<T> mapper = (BaseMapper<T>) mapperMap.get(entityCls);
        return new MyBatisPlusDao<T>(mapper,entityCls);

    }
}
