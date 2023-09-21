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
 * 本地git地址
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(content = "电脑本地git地址")
@TableName("git_local")
public class GitLocal extends Page {

    @Column(comment = "本地的git地址",name = "local_url")
    @TableField("local_url")
    private String localURL;

}
