package com.github.xuejike.query.mongo.demo.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xuejike.query.mybatisplus.annotation.MyBatisPlusDaoSelect;
import lombok.Data;

/**
 * @author xuejike
 * @date 2020/12/29
 */
@Data
@TableName("u2")
@MyBatisPlusDaoSelect
public class U2 {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
}
