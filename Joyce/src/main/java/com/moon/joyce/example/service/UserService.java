package com.moon.joyce.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.doma.User;
import com.moon.joyce.example.entity.vo.PageVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Joyce
 * @since 2021-09-01
 */

public interface UserService extends IService<User> {
    /**
     * 根据条件查询user
     * @param user
     * @return
     */
    int getUserCount(User user,String type);
    /**
     * 根据条件查询user
     * @param user
     * @return
     */
    User getUser(User user,String type);

    /**
     * 先分页查询后对结果排序
     * @param user
     * @return
     */
    List<User> getUserList(User user);

    /**
     * 查询好友
     * @param userId
     * @return
     */
    List<User> getAllFriends(Long userId);



    /**
     * 统计所有数据数量
     * @param
     * @return
     */
    long getUsersCount(User user);



    /**
     * 根据条件修改user
     * @param user
     * @return
     */
    @Transactional
    int updateUser(User dbUser,User user,String type);

    /**
     * 批量查询用户信息
     * @param list
     * @return
     */
    List<User> getAllFriendsByIds(List<Long> list);

    /**
     * 分页查询
     * @param user
     * @return
     */
    PageVo getPage(User user);
}
