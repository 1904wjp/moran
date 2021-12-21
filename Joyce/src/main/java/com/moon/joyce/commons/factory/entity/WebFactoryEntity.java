package com.moon.joyce.commons.factory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/16
 * webFactory
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebFactoryEntity implements Serializable {
    private static final long serialVersionUID = 2306128673903397125L;
    /**
     * 继承或者实现的类名
     */
    private String father;
    /**
     * 包集合
     */
    private String packages;

}
