package com.github.xuejike.query.core.config;

import com.github.xuejike.query.core.criteria.DaoCriteria;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuejike
 * @date 2020/12/28
 */
public class JkQueryConfig {
    private Map<String, DaoCriteria<?>> daoBeanMap = new ConcurrentHashMap<>();
    private final List<DaoFactory> daoFactoryList = new LinkedList<>();
    private static final JkQueryConfig  instance;
    static {
        instance = new JkQueryConfig();
    }
    public static JkQueryConfig getInstance() {
        return instance;
    }
    public JkQueryConfig addDaoFactory(DaoFactory daoFactory){
        daoFactoryList.add(daoFactory);
        return this;
    }


    public List<DaoFactory> getDaoFactoryList() {
        return daoFactoryList;
    }
}
