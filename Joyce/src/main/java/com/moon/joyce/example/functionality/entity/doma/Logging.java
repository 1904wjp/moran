package com.moon.joyce.example.functionality.entity.doma;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.commons.factory.enums.Type;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 日志记录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(content = "日志记录")
@TableName("logging")
public class Logging extends Page {
    @Column(comment = "访问的接口")
    private String uri;
    @Column(comment = "登录地址")
    private String loginIp;
    @Column(comment = "登录系统")
    private String loginSys;

    @TableField("event_desc")
    @Column(comment = "事件信息")
    private String eventDesc;
    @Column(comment = "参数",type = Type.TEXT)
    private String params;

    @Column(comment = "使用的账户")
    private String username;

    /**
     * 开始时间
     */
    @TableField(exist = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat( pattern="yyyy-MM-dd")
    @Column(exist = false)
    private Date startTime;
    /**
     * 结束时间
     */
    @TableField(exist = false)
    @Column(exist = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat( pattern="yyyy-MM-dd")
    private Date endTime;
    /**
     * 开始时间
     */
    @TableField(exist = false)
    @Column(exist = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat( pattern="yyyy-MM-dd")
    private Date startTime2;
    /**
     * 结束时间
     */
    @TableField(exist = false)
    @Column(exist = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat( pattern="yyyy-MM-dd")
    private Date endTime2;

    public Logging(String eventDesc, String params,String uri) {
        this.eventDesc = eventDesc;
        this.params = params;
        this.uri = uri;
    }
}
