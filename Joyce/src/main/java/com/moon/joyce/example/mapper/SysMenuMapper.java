package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.entity.doma.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Joyce
 * @since 2021-10-08
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> getList(Long id);
}
