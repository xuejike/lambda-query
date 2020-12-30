import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.TypeUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xuejike.query.core.JkLambdaQuery;

import com.github.xuejike.query.core.JkQuerys;
import com.github.xuejike.query.core.tool.lambda.CascadeField;
import com.github.xuejike.query.mongo.MongoDao;


import com.github.xuejike.query.mongo.MongoDaoFactory;
import com.github.xuejike.query.mongo.demo.App;
import com.github.xuejike.query.mongo.demo.data.TestDoc;
import com.github.xuejike.query.mongo.demo.mybatis.entity.U1;
import com.github.xuejike.query.mongo.demo.mybatis.mapper.U1Mapper;
import com.github.xuejike.query.mybatisplus.MyBatisPlusDaoFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author xuejike
 * @date 2020/12/21
 */
@SpringBootTest(classes = App.class)
@ContextConfiguration
@Slf4j
@Rollback(value = false)
public class TestMain {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    U1Mapper u1Mapper;
    @BeforeEach
    public void before(){
        new MyBatisPlusDaoFactory(SpringUtil.getBeansOfType(BaseMapper.class).values());
        new MongoDaoFactory(mongoTemplate);
    }

    @Test
    public void save(){
        for (long i = 0; i < 5; i++) {
            TestDoc testDoc = new TestDoc();
            testDoc.setName("name_"+i);
            testDoc.setTitle("title_"+i);
            testDoc.setNum(i);
            testDoc.setToc(new LinkedList<>());
            for (long j = 0; j < 5; j++) {
                TestDoc.Title title = new TestDoc.Title();
                title.setTitle("sub_title_"+i+"_"+j);
                title.setDesc("sub_desc_"+i+"_"+j);
                testDoc.getToc().add(title);
            }
            mongoTemplate.save(testDoc);
        }
    }

    @Test
    public void Test(){



        JkLambdaQuery<TestDoc> lambda = JkQuerys.lambdaQuery(TestDoc.class);

//        Query query = new Query();
//        query.addCriteria(new Criteria().orOperator(Criteria.where("name").is("999"))
//
//        );
//        List<TestDoc> list = mongoTemplate.find(query, TestDoc.class);
        List<TestDoc> list = lambda
                .orderDesc(TestDoc::getNum)
                .select(TestDoc::getNum)
                .list();

        System.out.println(JSON.toJSONString(list));
    }
    public CascadeField<TestDoc,TestDoc> of(){
        return new CascadeField<>();
    }

    @Test
    public void testAddU1(){

        U1 u1 = new U1();
        u1.setType("555");
        u1.setName("name2");
        u1Mapper.insert(u1);

    }
    @Test
    public void queryU1(){
        List<U1> list = JkQuerys.lambdaQuery(U1.class)
                .eq(U1::getId, 1)
                .or().eq(U1::getId,2)
                .or(or->
                        or.eq(U1::getId,1).eq(U1::getName,"name1").eq(U1::getName,"666")
                )
                .list();
        System.out.println(JSON.toJSONString(list));
    }
}
