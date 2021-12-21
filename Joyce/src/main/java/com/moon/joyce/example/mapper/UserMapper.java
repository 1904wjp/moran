package com.moon.joyce.example.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.entity.User;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Joyce
 * @since 2021-09-01
 */

public interface UserMapper extends BaseMapper<User> {
    /**
     * 查询所有用户
     * @param user
     * @return
     */
    List<User> getUsers(User user);

    /**
     * 查询数据个数
     * @return
     */
    int getTotal(User user);
}
