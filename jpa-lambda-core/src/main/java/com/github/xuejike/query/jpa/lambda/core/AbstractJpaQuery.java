package com.github.xuejike.query.jpa.lambda.core;

import com.github.xuejike.query.jpa.lambda.core.impl.Page;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractJpaQuery<T> {
    protected Session session;
    protected Class<T> entityCls;
    protected String subQueryName;
    protected List<Criterion> whereCriterionList;
    protected List<Projection> selectProjectionList;
    protected List<Order> orderList;
    protected List<String> fetchList;
    protected List<AbstractJpaQuery> subQueryList;


    public AbstractJpaQuery(Class<T> entityCls,Session session) {
        this.entityCls = entityCls;
        this.session = session;
        this.whereCriterionList = new LinkedList<>();
        this.selectProjectionList = new LinkedList<>();
        this.orderList = new LinkedList<>();
        this.fetchList = new LinkedList<>();
        this.subQueryList = new LinkedList<>();
    }

    public AbstractJpaQuery(AbstractJpaQuery<T> query) {
        this.entityCls = query.entityCls;
        this.session = query.session;
        this.whereCriterionList = query.whereCriterionList;
        this.selectProjectionList = query.selectProjectionList;
        this.orderList = query.orderList;
        this.fetchList = query.fetchList;
        this.subQueryList = query.subQueryList;
    }

    public Criteria createCriteria(){
       return createCriteria(false);
    }
    public Criteria createCriteria(boolean countCriteria){
        Criteria criteria = session.createCriteria(entityCls);
        return createCriteria(criteria,countCriteria);
    }
    public Criteria createCriteria(Criteria criteria ,boolean countCriteria){
        //设置条件关系
        for (Criterion criterion : whereCriterionList) {
            criteria.add(criterion);
        }
        //设置查询字段
        if (selectProjectionList.size() > 0){
            ProjectionList list = Projections.projectionList();
            for (Projection projection : selectProjectionList) {
                list.add(projection);
            }
            criteria.setProjection(list);
        }
        for (AbstractJpaQuery subQuery : subQueryList) {
            Criteria subCriteria = criteria.createCriteria(subQuery.getSubQueryName());
            subQuery.createCriteria(subCriteria,false);

        }

        if (!countCriteria){
            //设置排序
            for (Order order : orderList) {
                criteria.addOrder(order);
            }
            //加载关联属性
            for (String fetch : fetchList) {
                criteria.setFetchMode(fetch, FetchMode.JOIN);
            }

        }
        return criteria;
    }



    public List<T> list(){
        return createCriteria().list();
    }
    public List getObjList(){
        return createCriteria().list();
    }
    public Optional<T> first(){
        Page<T> page = pageList(new Page(1, 1));
        return page.getData().stream().findFirst();
    }

    /**
     * 分页取数据
     * @param iPage
     * @return
     */
    public Page<T> pageList(IPage iPage){
        Page<T> page = new Page<>();

        List list = createCriteria().setFirstResult((iPage.getPageNo() - 1) * iPage.getPageSize())
                .setMaxResults(iPage.getPageSize()).list();
        page.setData(list);
        page.setPageNo(iPage.getPageNo());
        page.setPageSize(iPage.getPageSize());
        if (iPage.isHaveTotal()){
            Long count = (Long) createCriteria(true)
                    .setProjection(Projections.rowCount())
                    .uniqueResult();
            page.setTotal(count);
        }

        return page;
    }
    public <E>List<E> getObjList(Class<E> dto){
        return createCriteria().setResultTransformer(Transformers.aliasToBean(dto)).list();
    }


    public Session getSession() {
        return session;
    }

    public Class<T> getEntityCls() {
        return entityCls;
    }

    public List<Criterion> getWhereCriterionList() {
        return whereCriterionList;
    }

    public List<Projection> getSelectProjectionList() {
        return selectProjectionList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public String getSubQueryName() {
        return subQueryName;
    }

    public void setSubQueryName(String subQueryName) {
        this.subQueryName = subQueryName;
    }
}
