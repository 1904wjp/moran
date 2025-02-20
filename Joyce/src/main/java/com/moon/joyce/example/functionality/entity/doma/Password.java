package com.moon.joyce.example.functionality.entity.doma;

import com.baomidou.mybatisplus.annotation.TableField;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Ids;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.commons.factory.enums.Type;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/09/26-- 15:48
 * @describe: 自己的一些密码
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(content = "密码表")
public class Password extends Page {
    @Ids
    private String username;
    private String password;
    @Column(comment = "备注",type = Type.TEXT)
    private String remark;
    @Column(comment = "版本号")
    private Integer version;
    @Column(comment = "应用名称")
    private String appName;
    @Column(comment = "是否是最新版[0:不是，1:是]",length = "1")
    private Integer isNewV;
    @Column(comment = "ip地址",length = "255")
    private String uri;
    @Column(comment = "二级密码")
    private String twoPassword;
    @Column(comment = "是否有二级密码",exist = false)
    @TableField(exist = false)
    private Integer isPassword;

    @Column(comment = "场景")
    private String scene;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Password password = (Password) o;
        return Objects.equals(username, password.username) && Objects.equals(appName, password.appName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, appName);
    }
}
