package com.moon.joyce.example.functionality.entity.doma;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Xing Dao Rong
 * @date 2021/10/13 11:04
 * @desc 页面配置：该类型是从文件中读取而非数据库
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageComponent implements Serializable {
    //主键
    private Long id;
    //名称
    private String name;
    //参数
    private Map<String,String> params;
    //背景链接
    private String backgroundUrl;
    //背景类型
    private String backgroundType;
    //背景颜色
    private String backgroundColor;


}
