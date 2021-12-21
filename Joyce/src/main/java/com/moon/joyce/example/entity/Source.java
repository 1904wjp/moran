package com.moon.joyce.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.example.entity.dto.Page;
import com.moon.joyce.example.entity.vo.PageVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("source")
public class Source  extends Page {
    /**
     * 资源名称
     */
    @TableField("source_name")
    private String sourceName;
    /**
     * 类型[0.图片；1.音频；2.视频]
     */
    private String type;
    /**
     * 资源连接
     */
    private String url;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 用户姓名
     */
    @TableField(exist = false)
    private String username;
}
