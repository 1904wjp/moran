package com.moon.joyce.example.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.example.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserChartVo extends User {
    private static final long serialVersionUID = -3740559224522564353L;
    /**
     * 好友状态
     */
    private Integer chartStatus;
}
