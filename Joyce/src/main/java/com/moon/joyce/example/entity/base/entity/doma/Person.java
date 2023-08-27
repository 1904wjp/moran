package com.moon.joyce.example.entity.base.entity.doma;

import com.baomidou.mybatisplus.annotation.TableField;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.example.entity.dto.Page;




/**
 * @author Xing Dao Rong
 * @date 2021/10/20 13:37
 * @desc 人的基类
 */
/*@Entity*/
@Table(isParent = true)
public class Person extends Page {

    /**
     * 名字
     */
   /* @Column(name= "person_name",columnDefinition = "varchar(64) COMMENT '姓名'")*/
    private String personName;
    /**
     * 年龄
     */
   /* @Column(name= "age",columnDefinition = "int(3) COMMENT '年龄'")*/
    private Integer age;
    /**
     * 性别
     */
   /* @Column(name= "sex",columnDefinition = "int(1) COMMENT '性别'")*/
    private Integer sex;
    /**
     * 身份证号码
     */
    @TableField(value = "id_code")
   /* @Column(name= "id_code",columnDefinition = "varchar(25) COMMENT '身份号码'")*/
    private String idCode;
    /**
     * 家庭地址
     */
    /*@Column(name= "address",columnDefinition = "varchar(64) COMMENT '地址'")*/
    private String address;
    /**
     * 手机号码
     */
    /*@Column(name= "phone",columnDefinition = "varchar(20) COMMENT '手机号码'")*/
    private String phone;
    /**
     * 邮件
     */
   /* @Column(name= "email",columnDefinition = "varchar(25) COMMENT '邮件地址'")*/
    private String email;
    /**
     * 个人介绍
     */
    /*@Column(name= "person_desc",columnDefinition = "varchar(255)  COMMENT '个人简历'")*/
    private String personDesc;



    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonDesc() {
        return personDesc;
    }

    public void setPersonDesc(String personDesc) {
        this.personDesc = personDesc;
    }
}
