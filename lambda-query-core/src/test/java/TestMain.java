import cn.hutool.core.util.ReflectUtil;
import com.github.xuejike.query.core.tool.ELParseTool;
import org.junit.jupiter.api.Test;
import vo.TestVo;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author xuejike
 * @date 2020/12/31
 */
public class TestMain {
    @Test
    public void parseClsTest(){
//        ELParseTool.parseSetRefValue(TestVo.class);
//        ELParseTool.parseRefValue(TestVo.class);
        Map<Field, String> refValues = ELParseTool.getRefValues(TestVo.class);
        String subId = ELParseTool.getSetRefValue(TestVo.class, "subId");
        System.out.println(subId);
    }
    @Test
    public void xnTest() throws Throwable {

        Long begin = System.currentTimeMillis();
        for (long i = 0; i < 1000000000L; i++) {
            TestVo testVo = new TestVo();
            testVo.setName("1111111");
        }
        System.out.println("-->"+(System.currentTimeMillis()-begin));
        Field nameField = ReflectUtil.getField(TestVo.class, "name");
        nameField.setAccessible(true);
        MethodHandles.Lookup lookup=MethodHandles.lookup();
        MethodHandle mh=lookup.findConstructor(TestVo.class, MethodType.methodType(void.class));

//        System.out.println(p);
        Constructor<TestVo> constructor = ReflectUtil.getConstructor(TestVo.class);
        begin = System.currentTimeMillis();

        for (long i = 0; i < 1000000000L; i++) {
//            TestVo testVo = new TestVo();
            TestVo testVo = (TestVo)mh.invokeExact();
//            TestVo testVo = constructor.newInstance();
//            TestVo testVo = ReflectUtil.newInstance(TestVo.class);
//            ReflectUtil.setFieldValue(testVo,nameField,"1111111");
            nameField.set(testVo,"1111111");
        }
        System.out.println("-->"+(System.currentTimeMillis()-begin));

    }
}
