package com.github.xuejike.jpaquery.demo.controller;

import com.github.xuejike.jpaquery.demo.entity.Dept;
import com.github.xuejike.jpaquery.demo.entity.User;
import com.github.xuejike.query.jpa.lambda.JpaLambdaQuery;
import com.github.xuejike.query.jpa.lambda.JpaQuerys;
import com.github.xuejike.query.jpa.lambda.core.impl.Page;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    EntityManager entityManager;
    @RequestMapping("/index")
    @ResponseBody
    public String index(){
        Session session = (Session) entityManager.getDelegate();
        //正常查询
        JpaLambdaQuery<User> userQuery = new JpaLambdaQuery<>(User.class, session);
        List<User> list = userQuery.eq(User::getUsername, "111")
                .gt(User::getCreateTime, LocalDateTime.now())
                .list();
//Hibernate: select this_.id as id1_1_0_, this_.create_time as create_t2_1_0_,
// this_.dept_id as dept_id3_1_0_, this_.pwd as pwd4_1_0_,
// this_.status as status5_1_0_, this_.username as username6_1_0_ from
// gg_user this_ where this_.username=? and this_.create_time>?


        //加载关联属性

        List<User> list1 = JpaQuerys.lambda(User.class, session)
                .eq(User::getUsername, "123")
                .orEq(User::getUsername, "456")
                .loadJoin(User::getDept).list();

        //关联属性查询
        List<User> list2 = JpaQuerys.lambda(User.class, session)
                .subQuery(User::getDept, lambda -> lambda.eq(Dept::getId, 1)).list();

//        分页查询
        Page<Dept> u1 = new JpaLambdaQuery<Dept>(Dept.class, session)
                .subQuery(Dept::getUserList, User.class, lambda -> lambda.eq(User::getUsername, "u1"))
                .pageList(new Page(1, 10, true));
        return "--";
    }
}
