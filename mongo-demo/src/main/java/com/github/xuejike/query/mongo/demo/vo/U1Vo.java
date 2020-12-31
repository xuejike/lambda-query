package com.github.xuejike.query.mongo.demo.vo;


import com.github.xuejike.query.core.annotation.RefValue;
import com.github.xuejike.query.core.annotation.SetRefValue;
import lombok.Data;

/**
 * @author xuejike
 * @date 2020/12/31
 */
@Data
public class U1Vo {
    private Long id;
    private String name;
    private String type;
    @SetRefValue("u2")
    private Long u2Id;
    @RefValue("#u2.name")
    private String u2Name;
}
