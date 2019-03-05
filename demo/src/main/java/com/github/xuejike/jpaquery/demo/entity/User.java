package com.github.xuejike.jpaquery.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xuejike
 * @since 2019-03-03
 */
@Data
@Table(name = "gg_user")
@Entity
public class User  {

    @Id
    @Column(name = "id")
    protected Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "username")
    public String username;

    @Column(name = "pwd")
    public String pwd;

    @Column(name = "create_time")
    public LocalDateTime createTime;

    @Column(name = "status")
    public String status;
    @Column(name = "dept_id")
    public Integer deptId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id",insertable = false,updatable = false)
    public Dept dept;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
