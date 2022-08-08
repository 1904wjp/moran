package com.moon.joyce.example.functionality.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.Column;
import com.moon.joyce.commons.annotation.Table;
import com.moon.joyce.commons.factory.enums.TableStrategy;
import com.moon.joyce.commons.factory.enums.Type;
import com.moon.joyce.example.entity.dto.Page;
import com.moon.joyce.example.entity.vo.PageVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
@Table(name = "accounting",content = "记账表",strategy = TableStrategy.ADD)
public class Accounting extends Page {
  @Column(name = "income",type = Type.BIGINT,comment = "收入")
  private Long income;

  @Column(name = "expenditure",type = Type.BIGINT,comment = "支出")
  private Long expenditure;

  @Column(name = "budget",type = Type.BIGINT,comment = "预算")
  private Long budget;

  @Column(name = "remark",type = Type.TEXT,length = "0",comment = "备注")
  private String remark;

  @TableField("user_id")
  @Column(name = "user_id",type = Type.BIGINT,comment = "用户id")
  private Long userId;
}
