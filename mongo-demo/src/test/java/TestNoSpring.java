import com.github.xuejike.query.core.JkLambdaQuery;
import com.github.xuejike.query.core.JkQuerys;
import com.github.xuejike.query.core.config.JkQueryConfig;
import com.github.xuejike.query.mongo.MongoDaoFactory;
import com.github.xuejike.query.mongo.demo.data.TestDoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

/**
 * @author xuejike
 * @date 2020/12/28
 */
public class TestNoSpring {

    @Test
    public void test(){
        JkLambdaQuery<TestDoc,TestDoc> query = JkQuerys.lambdaQuery(TestDoc.class);
        List<TestDoc> list = query.eq(TestDoc::getId, "666").list();

    }
}
