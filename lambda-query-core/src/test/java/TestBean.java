import lombok.Data;

import java.util.List;

/**
 * @author xuejike
 * @date 2020/12/18
 */
@Data
public class TestBean {
    private String name;
    private SubNameCls sub;
    private List<SubNameCls> subList;
    private SubNameCls[] subArray;

    @Data
    public static class SubNameCls{
        private String subName;
        private Sub1NameCls sub;
    }
    @Data
    public static class Sub1NameCls{
        private String subName;
    }
}
