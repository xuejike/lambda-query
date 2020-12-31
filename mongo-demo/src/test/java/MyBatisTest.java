import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xuejike.query.core.JkQuerys;
import com.github.xuejike.query.mongo.MongoDaoFactory;
import com.github.xuejike.query.mongo.demo.App;
import com.github.xuejike.query.mongo.demo.mybatis.entity.U1;
import com.github.xuejike.query.mongo.demo.mybatis.entity.U2;
import com.github.xuejike.query.mongo.demo.mybatis.mapper.U1Mapper;
import com.github.xuejike.query.mongo.demo.vo.U1Vo;
import com.github.xuejike.query.mybatisplus.MyBatisPlusDaoFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

/**
 * @author xuejike
 * @date 2020/12/31
 */
@SpringBootTest(classes = App.class)
@ContextConfiguration
@Slf4j
@Rollback(value = false)
public class MyBatisTest {

    @BeforeEach
    public void before(){
        new MyBatisPlusDaoFactory(SpringUtil.getBeansOfType(BaseMapper.class).values());

    }

    @Test
    public void list(){
        List<U1Vo> list = JkQuerys.lambdaQuery(U1.class)
                .loadRef(U1::getU2Id, U2.class, U2::getId)
                .map(U1Vo.class).list();
        System.out.println(JSON.toJSONString(list));
    }
}
