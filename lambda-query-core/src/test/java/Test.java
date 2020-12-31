import com.alibaba.fastjson.JSON;
import com.github.xuejike.query.core.po.QueryInfo;
import com.github.xuejike.query.core.tool.lambda.CascadeField;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class Test {
    @org.junit.Test
    public void t(){



    }

    public static CascadeField<TestBean,TestBean> cascadeField(){
        return new CascadeField<>();
    }
}
