package com.github.xuejike.query.mongo.demo.http;

import com.github.xuejike.query.http.annotation.HttpDaoSelect;
import lombok.Data;

/**
 * @author xuejike
 * @date 2021/1/4
 */
@HttpDaoSelect( serverAddress = "http://127.0.0.1:7000/lambda/u1/")
@Data
public class HttpEntity {
    private Long id;
    private String name;
    private String type;
    private Long u2Id;
}
