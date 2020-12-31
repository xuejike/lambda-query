package vo;


import com.github.xuejike.query.core.annotation.RefValue;
import com.github.xuejike.query.core.annotation.SetRefValue;
import lombok.Data;

/**
 * @author xuejike
 * @date 2020/12/31
 */
@Data
public class TestVo {
    private Long id;
    private String name;
    @SetRefValue("sub")
    private Long subId;
    @RefValue("#sub.name")
    private String subName;
}
