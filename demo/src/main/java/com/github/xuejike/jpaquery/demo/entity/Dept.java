package com.github.xuejike.jpaquery.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "dept")
@Data
public class Dept {
    @Id
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "deptId",fetch = FetchType.LAZY)

    public List<User> userList;
}
