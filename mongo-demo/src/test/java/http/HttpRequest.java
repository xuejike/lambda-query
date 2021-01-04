package http;

import com.github.xuejike.query.http.annotation.HttpDaoSelect;
import lombok.Data;

/**
 * @author xuejike
 * @date 2020/12/31
 */
@HttpDaoSelect
@Data
public class HttpRequest {
    private Long id;
    private String name;
    private String type;
    private String userName;
}
