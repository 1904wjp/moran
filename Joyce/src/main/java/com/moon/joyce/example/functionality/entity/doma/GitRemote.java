package com.moon.joyce.example.functionality.entity.doma;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * git远程信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(content = "git的远程信息")
@TableName("git_remote")
public class GitRemote extends Page {

    @Column(comment = "git远程地址",name = "remote_url")
    @TableField("remote_url")
    private String remoteURL;
    @Column(comment = "git用户名",exist = false)
    @TableField(exist = false)
    private String username;
    @Column(comment = "git密码",exist = false)
    @TableField(exist = false)
    private String password;
    @Column(comment = "git远程地址")
    @TableField("password_id")
    private Long passwordId;
}
