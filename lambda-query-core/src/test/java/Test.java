import com.github.xuejike.query.core.base.BaseQuery;
import com.github.xuejike.query.core.tool.lambda.CascadeField;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class Test {
    @org.junit.Test
    public void t(){
        CascadeField<String, TestBean> cascadeField = cascadeField().sub(TestBean::getSub).
                sub(TestBean.SubNameCls::getSubName)
                .subFieldName("xxx")
                .subFieldName("xxx");
        cascadeField.apply(null);
        BaseQuery<TestBean> baseQuery = new BaseQuery<>(TestBean.class);
        baseQuery.eq(cascadeField().sub(TestBean::getSub).
                sub(TestBean.SubNameCls::getSubName)
                .subFieldName("xxx")
                .subFieldName("xxx"),"xx")
                .eq(TestBean::getSub,  "xx")
                .or().eq(TestBean::getName,"666").findAll();
        System.out.println("666");



    }

    public static CascadeField<TestBean,TestBean> cascadeField(){
        return new CascadeField<>();
    }
}
