package com.moon.joyce.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.moon.joyce.example.entity.base.entity.Person;


/**
 * 用户
 * @author Joyce
 * @since 2021-09-01
 */
@TableName("user")
/*@Entity
@Table(name = "user")
@org.hibernate.annotations.Table(appliesTo = "user",comment = "用户表")*/
public class User extends Person {
    private static final long serialVersionUID = 8843327699543286578L;
    /**
     * 用户名
     */
  /*  @Column(name="username" ,unique = true,columnDefinition = "varchar(10) COMMENT '用户名'")*/
    private String username;
    /**
     * 密码
     */
    /*@Column(name="password" ,columnDefinition = "varchar(12) COMMENT '登录密码'")*/
    private String password;
    /**
     * 二级密码
     */
    @TableField("secondar_password")
   /* @Column(name="secondar_password" ,columnDefinition = "varchar(12) COMMENT '二级密码'")*/
    private String secondarPassword;
    /**
     * 激活状态
     */
  /*  @Column(name="status" ,columnDefinition = "varchar(2) COMMENT '账号状态【0.未激活；1.激活；10000：冻结】'")*/
    private Integer status;
    /**
     * 状态码
     */
    @TableField(value = "status_code")
/*    @Column(name="status_code" ,columnDefinition = "varchar(100) COMMENT '激活码'")*/
    private String statusCode;
    /**
     * 头像url
     */
    @TableField(value = "file_url")
   /* @Column(name="file_url" ,columnDefinition = "varchar(100) COMMENT '头像地址'")*/
    private String fileUrl;
    /**
     * 身份
     */
    @TableField(value = "user_type_id")
  /*  @Column(name="user_type_id" ,columnDefinition = "varchar(32) COMMENT '角色id'")*/
    private Long UserTypeId;
    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;
    /**
     * websocketSessionId的值
     */
  /*  @TableField(value = "websocket_session_id")
    private String websocketSessionId;*/


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecondarPassword() {
        return secondarPassword;
    }

    public void setSecondarPassword(String secondarPassword) {
        this.secondarPassword = secondarPassword;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Long getUserTypeId() {
        return UserTypeId;
    }

    public void setUserTypeId(Long userTypeId) {
        UserTypeId = userTypeId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
