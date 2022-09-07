package com.moon.joyce.example.functionality.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Xing Dao Rong
 * @date 2021/10/13 11:04
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageComponent {
    private String name;
    private Map<String,String> params;
    private String backgroundUrl;
    private String backgroundType;
    private String backgroundColor;


}
