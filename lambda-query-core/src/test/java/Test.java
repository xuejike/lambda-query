import com.alibaba.fastjson.JSON;
import com.github.xuejike.query.core.base.BaseQuery;
import com.github.xuejike.query.core.po.QueryInfo;
import com.github.xuejike.query.core.tool.lambda.CascadeField;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class Test {
    @org.junit.Test
    public void t(){


        BaseQuery<TestBean> baseQuery = new BaseQuery<>(TestBean.class);
        QueryInfo queryInfo = baseQuery.eq(cascadeField().sub(TestBean::getSub).
                sub(TestBean.SubNameCls::getSubName)
                .subFieldName("xxx1")
                .subFieldName("xxx2"), "xx")
                .eq(TestBean::getSub, "xxx")
                .or()
                .eq(TestBean::getName, "666")
                .eq(TestBean::getSubList,"666666")
                .eq(TestBean::getSubArray,"666666")
                .gt(TestBean::getName,"n1").lt(TestBean::getName,"n2")
                .buildQueryInfo();
        System.out.println(JSON.toJSONString(queryInfo));
        System.out.println("666");
        cascadeField().subList(TestBean::getSubList).sub(TestBean.SubNameCls::getSub);


    }

    public static CascadeField<TestBean,TestBean> cascadeField(){
        return new CascadeField<>();
    }
}
