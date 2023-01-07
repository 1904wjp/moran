package com.moon.joyce.example.entity.base.entity.doma;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.commons.factory.enums.Type;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/10/10-- 13:45
 * @describe:
 */
@Table(content = "根实体",isParent = true)
public class RootEntity implements Serializable {


}
