package com.moon.joyce.example.entity.doma;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2023/04/26-- 18:06
 * @describe: git用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("git_info")
@Table(content = "git用户信息")
public class GitInfo extends Page {
    @Column(exist = false)
    @TableField(exist = false)
    private String proName;
    @Column(exist = false)
    @TableField(exist = false)
    private String proPath;
    @Column(exist = false)
    @TableField(exist = false)
    private File file;
    @Column(comment = "git账户")
    private String username;
    @Column(comment = "git密码")
    private String password;
    @Column(exist = false)
    @TableField(exist = false)
    private String remoteUR;
}
