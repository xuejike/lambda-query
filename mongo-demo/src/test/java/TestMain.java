import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xuejike.query.core.JLambdaQuery;

import com.github.xuejike.query.core.JQuerys;
import com.github.xuejike.query.core.tool.lambda.CascadeField;


import com.github.xuejike.query.mongo.MongoDaoFactory;
import com.github.xuejike.query.mongo.demo.App;
import com.github.xuejike.query.mongo.demo.data.TestDoc;
import com.github.xuejike.query.mongo.demo.mybatis.entity.U1;
import com.github.xuejike.query.mybatisplus.MyBatisPlusDaoFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xuejike
 * @date 2020/12/21
 */
@SpringBootTest(classes = App.class)
@ContextConfiguration
@Slf4j
@Rollback(value = true)
public class TestMain {
    @Autowired
    MongoTemplate mongoTemplate;


    @BeforeEach
    public void before(){
        new MyBatisPlusDaoFactory(SpringUtil.getBeansOfType(BaseMapper.class).values());
        new MongoDaoFactory(mongoTemplate);
    }

//    @BeforeEach
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



        JLambdaQuery<TestDoc> lambda = JQuerys.lambdaQuery(TestDoc.class);

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

    /**
     * == 查询
     */
    @Test
    public void testEq(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class).eq(TestDoc::getName, "name_1").list();
        Assertions.assertEquals(list.size(),1);
        TestDoc testDoc = list.get(0);
        Assertions.assertEquals(testDoc.getTitle(),"title_1");
    }

    /**
     * 大于号查询
     *
     */
    @Test
    public void testGt(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class).gt(TestDoc::getNum,3 ).list();
        Assertions.assertEquals(list.size(),1);
        TestDoc testDoc = list.get(0);
        Assertions.assertEquals(testDoc.getTitle(),"title_4");
    }

    /**
     * 大于等于查询
     */
    @Test
    public void testGte(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class).gte(TestDoc::getNum,4 ).list();
        Assertions.assertEquals(list.size(),1);
        TestDoc testDoc = list.get(0);
        Assertions.assertEquals(testDoc.getTitle(),"title_4");
    }

    /**
     * 小于号查询
     */
    @Test
    public void testLt(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class).lt(TestDoc::getNum,1 ).list();
        Assertions.assertEquals(list.size(),1);
        TestDoc testDoc = list.get(0);
        Assertions.assertEquals(testDoc.getTitle(),"title_0");
    }

    /**
     * 小于等于查询
     */
    @Test
    public void testLte(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class).lte(TestDoc::getNum,0 ).list();
        Assertions.assertEquals(list.size(),1);
        TestDoc testDoc = list.get(0);
        Assertions.assertEquals(testDoc.getTitle(),"title_0");
    }

    /**
     * In 查询
     */
    @Test
    public void testIn(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class)
                .in(TestDoc::getTitle,"title_0" ).list();
        Assertions.assertEquals(list.size(),1);
        TestDoc testDoc = list.get(0);
        Assertions.assertEquals(testDoc.getTitle(),"title_0");
    }

    /**
     * NotIn 查询
     */
    @Test
    public void testNotIn(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class)
                .notIn(TestDoc::getTitle,"title_0","title_1","title_2","title_3" ).list();
        Assertions.assertEquals(list.size(),1);
        TestDoc testDoc = list.get(0);
        Assertions.assertEquals(testDoc.getTitle(),"title_4");
    }

    /**
     * or 查询
     */
    @Test
    public void testOr(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class).or().eq(TestDoc::getNum, 0).or().eq(TestDoc::getNum, 1).list();
        Assertions.assertEquals(list.size(),2);

        list = JQuerys.lambdaQuery(TestDoc.class).or(it->{
            it.eq(TestDoc::getNum,0).eq(TestDoc::getName,"name_0");
        }).or(it->{
            it.eq(TestDoc::getNum,1).eq(TestDoc::getName,"name_1");
        }).list();
        Assertions.assertEquals(list.size(),2);


    }

    /**
     * 排序查询
     */
    @Test
    public void testOrder(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class).in(TestDoc::getNum, 1, 2).orderAsc(TestDoc::getNum).list();
        Assertions.assertEquals(list.size(),2);
        Assertions.assertEquals(list.get(0).getNum(),1);
        Assertions.assertEquals(list.get(1).getNum(),2);

        list = JQuerys.lambdaQuery(TestDoc.class).in(TestDoc::getNum, 1, 2).orderDesc(TestDoc::getNum).list();
        Assertions.assertEquals(list.size(),2);
        Assertions.assertEquals(list.get(0).getNum(),2);
        Assertions.assertEquals(list.get(1).getNum(),1);
    }

    /**
     * 二级字段查询
     */
    @Test
    public void testSubField(){
        List<TestDoc> list = JQuerys.lambdaQuery(TestDoc.class).eq(of().subList(TestDoc::getToc).sub(TestDoc.Title::getTitle), "sub_title_0_0").list();
        Assertions.assertEquals(list.size(),1);
        Assertions.assertEquals(list.get(0).getNum(),0);
    }
    public CascadeField<TestDoc,TestDoc> of(){
        return new CascadeField<>();
    }

    @Test
    public void testAddU1(){

        U1 u1 = new U1();
        u1.setType("555");
        u1.setName("name2");


    }
    @Test
    public void queryU1(){
        List<U1> list = JQuerys.lambdaQuery(U1.class)
                .eq(U1::getId, 1)
                .or().eq(U1::getId,2)
                .or(or->
                        or.eq(U1::getId,1).eq(U1::getName,"name1").eq(U1::getName,"666")
                )
                .list();
        System.out.println(JSON.toJSONString(list));
    }
}
