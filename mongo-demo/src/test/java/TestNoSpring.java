import com.github.xuejike.query.core.JLambdaQuery;
import com.github.xuejike.query.core.JQuerys;
import com.github.xuejike.query.mongo.demo.data.TestDoc;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author xuejike
 * @date 2020/12/28
 */
public class TestNoSpring {

    @Test
    public void test(){
        JLambdaQuery<TestDoc> query = JQuerys.lambdaQuery(TestDoc.class);
        List<TestDoc> list = query.eq(TestDoc::getId, "666").list();

    }
}
