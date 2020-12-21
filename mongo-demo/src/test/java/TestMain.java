import com.alibaba.fastjson.JSON;
import com.github.xuejike.query.core.JkLambdaQuery;
import com.github.xuejike.query.mongo.MongoDao;
import com.github.xuejike.query.mongo.MongoQuerys;
import com.github.xuejike.query.mongo.demo.App;
import com.github.xuejike.query.mongo.demo.data.TestDoc;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

/**
 * @author xuejike
 * @date 2020/12/21
 */
@SpringBootTest(classes = App.class)
@ContextConfiguration
@Slf4j
public class TestMain {
    @Autowired
    MongoTemplate mongoTemplate;
    @Test
    public void Test(){
        JkLambdaQuery<TestDoc> lambda = MongoQuerys.lambda(mongoTemplate, TestDoc.class);
        List<TestDoc> list = lambda.eq(TestDoc::getId, "888").list();

        System.out.println(JSON.toJSONString(list));
    }
}
