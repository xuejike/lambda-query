import com.github.xuejike.query.core.JQuerys;
import com.github.xuejike.query.http.client.HttpClientFactory;
import com.github.xuejike.query.mongo.demo.App;
import http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author xuejike
 * @date 2020/12/31
 */
@SpringBootTest(classes = App.class)
@ContextConfiguration
@Slf4j
@Rollback(value = false)
public class HttpClientTest {

    @BeforeEach
    public void before(){
        new HttpClientFactory();

    }

    @Test
    public void list(){
        JQuerys.lambdaQuery(HttpRequest.class).eq(HttpRequest::getName,"xxx").eq(HttpRequest::getUserName,"un").list();
    }
}
