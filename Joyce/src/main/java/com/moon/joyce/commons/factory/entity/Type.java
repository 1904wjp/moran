package com.moon.joyce.commons.factory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/04/12-- 10:17
 * @describe:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    private String type;
    private List<String> annotations;
}
