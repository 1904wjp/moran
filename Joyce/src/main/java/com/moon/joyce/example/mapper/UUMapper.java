package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.entity.UU;
import joyce.example.entity.UserType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author Joyce
 * @since 2021-09-25
 * 用户与用户关系的mapper层
 */
public interface UUMapper extends BaseMapper<UU> {
     /**
      * 获取用户对应类型的关系用户id集合
      * @param type
      * @param userId
      * @return
      */
     List<Long> getListByType(@Param("type") String type,@Param("userId") Long userId);

     /**
      * 获取id为userId的用户所有的被申请关系集合
      * @param userId
      * @return
      */
     List<UU> selectList(Long userId);

     /**
      * 获取搜友好友
      * @param id1
      * @param id2
      * @return
      */
     List<UU> getFriend(@Param("id1") Long id1,@Param("id2") Long id2);
}
