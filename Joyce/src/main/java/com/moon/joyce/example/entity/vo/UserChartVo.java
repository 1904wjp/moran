package com.moon.joyce.example.entity.vo;

import com.moon.joyce.example.entity.doma.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     * 好友状态("在线：0 不在线：1")
     */
    private Integer chartStatus;
}
