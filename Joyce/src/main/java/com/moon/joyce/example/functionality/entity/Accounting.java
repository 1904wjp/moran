package com.moon.joyce.example.functionality.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.commons.factory.enums.TableStrategy;
import com.moon.joyce.commons.factory.enums.Type;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/07/02-- 10:51
 * @describe: 记账
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("accounting")
@Table(name = "accounting",content = "记账表")
public class Accounting extends Page {
  @Column(name = "income",type = Type.DECIMAL,comment = "收入")
  private BigDecimal income;

  @Column(name = "expenditure",type = Type.DECIMAL,comment = "支出")
  private BigDecimal expenditure;

  @Column(name = "budget",type = Type.DECIMAL,comment = "预算")
  private BigDecimal budget;

  @Column(name = "remark",type = Type.VARCHAR,comment = "备注")
  private String remark;

  @TableField("user_id")
  @Column(name = "user_id",type = Type.BIGINT,comment = "用户id")
  private Long userId;
}
