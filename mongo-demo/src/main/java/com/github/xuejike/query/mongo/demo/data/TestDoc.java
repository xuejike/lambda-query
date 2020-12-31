package com.github.xuejike.query.mongo.demo.data;

import com.github.xuejike.query.mongo.annotation.MongoDaoSelect;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

/**
 * @author xuejike
 * @date 2020/12/21
 */
@Document("demo_doc")
@Data
@MongoDaoSelect
public class TestDoc {
    @MongoId
    private String id;
    private String name;
    private String title;
    private Long num;
    private List<Title> toc;
    @Data
    public static class Title{
        private String title;
        private String desc;

    }
}
