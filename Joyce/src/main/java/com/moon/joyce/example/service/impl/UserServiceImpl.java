package com.moon.joyce.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.entity.User;
import com.moon.joyce.example.mapper.UserMapper;
import com.moon.joyce.example.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Joyce
 * @since 2021-09-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public int getUserCount(User user, String type) {
        QueryWrapper wrapper = new QueryWrapper();
        if (null==user){
            return Constant.RESULT_UNKNOWN_SQL_RESULT;
        }

        /**
         * 该用户名是否存在
         */
        if (type.equals(Constant.USER_TYPE_UNIQUE_USERNAME)){
            if (Objects.isNull(user.getUsername())||StringUtils.isEmpty(user.getUsername().trim())){
                return  Constant.RESULT_UNKNOWN_SQL_RESULT;
            }
            wrapper.eq("username",user.getUsername());
            return baseMapper.selectCount(wrapper);
        }

        /**
         *  验证手机号码是否存在
         */
        if (type.equals(Constant.USER_TYPE_UNIQUE_PHONE)){
            if (Objects.isNull(user.getPhone())||StringUtils.isEmpty(user.getPhone().trim())){
                return  Constant.RESULT_UNKNOWN_SQL_RESULT;
            }
            wrapper.eq("phone",user.getPhone());
            return baseMapper.selectCount(wrapper);
        }

        /**
         *  验证邮件是否存在
         */
        if (type.equals(Constant.USER_TYPE_UNIQUE_EMAIL)){
            if (Objects.isNull(user.getEmail())||StringUtils.isEmpty(user.getEmail().trim())){
                return  Constant.RESULT_UNKNOWN_SQL_RESULT;
            }
            wrapper.eq("email",user.getEmail());
            return baseMapper.selectCount(wrapper);
        }

        /**
         * 该用户名状态激活码是否存在
         */
        if (type.equals(Constant.USER_TYPE_UNIQUE_STATUS_CODE)){
            if (Objects.isNull(user.getStatusCode())||StringUtils.isEmpty(user.getStatusCode().trim())){
                return  Constant.RESULT_UNKNOWN_SQL_RESULT;
            }
        }
        /**
         * 该用户名websocketSessionId是否存在
         */
      /*  if (type.equals(Constant.USER_TYPE_WEBSOCKET_SESSION_ID)){
            if (Objects.isNull(user.getWebsocketSessionId())||StringUtils.isEmpty(user.getWebsocketSessionId().trim())){
                return  Constant.RESULT_UNKNOWN_SQL_RESULT;
            }
        }*/


        /**user属性*/
        if (Objects.nonNull(user.getUsername())&&StringUtils.isNotEmpty(user.getUsername().trim())){
            wrapper.eq("username",user.getUsername());
        }
        if (Objects.nonNull(user.getPassword())&&StringUtils.isNotEmpty(user.getPassword().trim())){
            wrapper.eq("password",user.getPassword());
        }
        if (Objects.nonNull(user.getEmail())&&StringUtils.isNotEmpty(user.getEmail().trim())){
            wrapper.eq("email",user.getEmail());
        }
        if (Objects.nonNull(user.getPhone())&&StringUtils.isNotEmpty(user.getPhone().trim())){
            wrapper.eq("phone",user.getPhone());
        }
        if (Objects.nonNull(user.getSecondarPassword())&&StringUtils.isNotEmpty(user.getSecondarPassword().trim())){
            wrapper.eq("secondar_password",user.getSecondarPassword());
        }
        if (Objects.nonNull(user.getStatusCode())&&StringUtils.isNotEmpty(user.getStatusCode().trim())){
            wrapper.eq("status_code",user.getStatusCode());
        }
     /*   if (Objects.nonNull(user.getWebsocketSessionId())&&StringUtils.isNotEmpty(user.getWebsocketSessionId().trim())){
            wrapper.eq("websocket_session_id",user.getWebsocketSessionId());
        }*/
            wrapper.eq("delete_flag",Constant.UNDELETE_STATUS);
        return baseMapper.selectCount(wrapper);

    }

    @Override
    public User getUser(User user,String type) {
        QueryWrapper wrapper = new QueryWrapper();
        if (null==user){
            return null;
        }

        /**
         * 登录
         */
        if (type.equals(Constant.USER_TYPE_LOGIN)){
            if (Objects.isNull(user.getUsername())||Objects.isNull(user.getPassword())||StringUtils.isEmpty(user.getUsername().trim())||StringUtils.isEmpty(user.getPassword().trim())){
                return null;
            }
                wrapper.eq("username",user.getUsername());
                wrapper.ne("status",Constant.USER_TYPE_INVAILD_STATUS);
                return baseMapper.selectOne(wrapper);
        }
        if (Objects.nonNull(user.getUsername())&&StringUtils.isNotEmpty(user.getUsername().trim())){
            wrapper.eq("username",user.getUsername());
        }
        if (Objects.nonNull(user.getPassword())&&StringUtils.isNotEmpty(user.getPassword().trim())){
            wrapper.eq("password",user.getPassword());
        }
        if (Objects.nonNull(user.getEmail())&&StringUtils.isNotEmpty(user.getEmail().trim())){
            wrapper.eq("email",user.getEmail());
        }
        if (Objects.nonNull(user.getPhone())&&StringUtils.isNotEmpty(user.getPhone().trim())){
            wrapper.eq("phone",user.getPhone());
        }
        if (Objects.nonNull(user.getSecondarPassword())&&StringUtils.isNotEmpty(user.getSecondarPassword().trim())){
            wrapper.eq("secondar_password",user.getSecondarPassword());
        }
        if (Objects.nonNull(user.getStatusCode())&&StringUtils.isNotEmpty(user.getStatusCode().trim())){
            wrapper.eq("status_code",user.getStatusCode());
        }
       /* if (Objects.nonNull(user.getWebsocketSessionId())&&StringUtils.isNotEmpty(user.getWebsocketSessionId().trim())){
            wrapper.eq("websocket_session_id",user.getWebsocketSessionId());
        }*/
        wrapper.eq("delete_flag",Constant.UNDELETE_STATUS);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<User> getUserList(User user) {
        return userMapper.getUsers(user);
    }

    @Override
    public int getUsersCount(User user) {
        return userMapper.getTotal(user);
    }


    @Override
    public int updateUser(User dbUser,User user, String type) {
        /**
         * 该用户名状态是否存在
         */
        if (Objects.isNull(dbUser.getStatus())){
            return Constant.RESULT_UNKNOWN_SQL_RESULT;
        }
        /**
         * 激活用户
         */
        if (type.equals(Constant.USER_TYPE_UP_VAILD_STATUS)){ ;
            dbUser.setStatus(Constant.USER_TYPE_VAILD_STATUS);
        }
        /**
         * 升级vip
         */
        if (type.equals(Constant.USER_TYPE_UP_VIP_STATUS)){
            dbUser.setStatus(Constant.USER_TYPE_VIP_STATUS);
        }
        /**
         * 修改密码
         */
        if (type.equals(Constant.USER_TYPE_PASSWORD)){
            if (Objects.isNull(dbUser.getPassword())||StringUtils.isEmpty(dbUser.getPassword().trim())){
                return Constant.RESULT_UNKNOWN_SQL_RESULT;
            }
        }
        /**
         * 更改户名
         */
        if (type.equals(Constant.USER_TYPE_UNIQUE_USERNAME)){
            if (Objects.isNull(dbUser.getUsername())||StringUtils.isEmpty(dbUser.getUsername().trim())){
                return Constant.RESULT_UNKNOWN_SQL_RESULT;
            }
        }
        /**
         *  更改号码
         */
        if (type.equals(Constant.USER_TYPE_UNIQUE_PHONE)){
            if (Objects.isNull(dbUser.getPhone())||StringUtils.isEmpty(dbUser.getPhone().trim())){
                return Constant.RESULT_UNKNOWN_SQL_RESULT;
            }
        }
        /**
         *  更改邮件
         */
        if (type.equals(Constant.USER_TYPE_UNIQUE_EMAIL)){
            if (Objects.isNull(dbUser.getEmail())||StringUtils.isEmpty(dbUser.getEmail().trim())){
                return Constant.RESULT_UNKNOWN_SQL_RESULT;
            }
        }
        UpdateWrapper wrapper = new UpdateWrapper();
        if (Objects.nonNull(user.getUsername())&&StringUtils.isNotEmpty(user.getUsername().trim())){
            wrapper.eq("username",user.getUsername());
        }
        if (Objects.nonNull(user.getPassword())&&StringUtils.isNotEmpty(user.getPassword().trim())){
            wrapper.eq("password",user.getPassword());
        }
        if (Objects.nonNull(user.getEmail())&&StringUtils.isNotEmpty(user.getEmail().trim())){
            wrapper.eq("email",user.getEmail());
        }
        if (Objects.nonNull(user.getPhone())&&StringUtils.isNotEmpty(user.getPhone().trim())){
            wrapper.eq("phone",user.getPhone());
        }
        if (Objects.nonNull(user.getSecondarPassword())&&StringUtils.isNotEmpty(user.getSecondarPassword().trim())){
            wrapper.eq("secondar_password",user.getSecondarPassword());
        }
        if (Objects.nonNull(user.getStatus())){
            wrapper.eq("status",user.getStatus());
        }
        if (Objects.nonNull(user.getId())){
            wrapper.eq("id",user.getId());
        }
        return baseMapper.update(dbUser, wrapper);
    }
}
