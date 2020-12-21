package com.github.xuejike.query.mongo.demo.data;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * @author xuejike
 * @date 2020/12/21
 */
@Document("demo_doc")
@Data
public class TestDoc {
    @MongoId
    private String id;
    private String name;
    private String title;
}
