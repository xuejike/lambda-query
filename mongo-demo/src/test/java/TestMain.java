import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.github.xuejike.query.core.JkLambdaQuery;

import com.github.xuejike.query.core.JkQuerys;
import com.github.xuejike.query.core.tool.lambda.CascadeField;
import com.github.xuejike.query.mongo.MongoDao;


import com.github.xuejike.query.mongo.MongoDaoFactory;
import com.github.xuejike.query.mongo.demo.App;
import com.github.xuejike.query.mongo.demo.data.TestDoc;
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

import java.util.LinkedList;
import java.util.List;

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
    @BeforeEach
    public void before(){
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
        List<TestDoc> list = lambda.eq(of().subList(TestDoc::getToc)
                .sub(TestDoc.Title::getTitle), "sub_title_0_2")
                .or().eq(of().sub(TestDoc::getTitle),"666")
                .or().eq(of().sub(TestDoc::getName),"6661")
                .select(TestDoc::getTitle)
                .list();

        System.out.println(JSON.toJSONString(list));
    }
    public CascadeField<TestDoc,TestDoc> of(){
        CascadeField<TestDoc,TestDoc> field = new CascadeField<>();
        return field;
    }
}
