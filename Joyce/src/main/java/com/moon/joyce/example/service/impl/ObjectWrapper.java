package com.moon.joyce.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moon.joyce.example.entity.doma.Source;

import java.util.Objects;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2023/08/08-- 18:56
 * @describe:
 */
public class ObjectWrapper<T> {
    QueryWrapper<T> wrapperi;
    LambdaQueryWrapper<T> wrapper;
    public void flash(){
       wrapperi = new QueryWrapper<>();
       wrapper = wrapperi.lambda();
    }
}
