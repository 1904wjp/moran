package com.moon.joyce.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.commons.annotation.RedisValueComponet;
import com.moon.joyce.example.entity.SysMenu;
import com.moon.joyce.example.mapper.SysMenuMapper;
import com.moon.joyce.example.service.CommonService;
import com.moon.joyce.example.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Joyce
 * @since 2021-10-08
 */
@Service
@RedisValueComponet("SysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper,SysMenu> implements SysMenuService , CommonService {

    @Override
    public List<SysMenu> getMenus(SysMenu menu) {
        QueryWrapper wrapper = new QueryWrapper();
        if (Objects.nonNull(menu)){
            if (StringUtils.isNotEmpty(menu.getMenuName())){
                wrapper.like("menu_name",menu.getMenuName());
            }
            if (StringUtils.isNotEmpty(menu.getMenuUrl())){
                wrapper.like("menu_url",menu.getMenuUrl());
            }
            if (Objects.nonNull(menu.getParentId())){
                wrapper.like("parent_id",menu.getParentId());
            }
           wrapper.orderByAsc("menu_order");
           wrapper.orderByAsc("id");
        }
        return  baseMapper.selectList(wrapper);
    }



    @Override
    public int deleteMenuById(Long id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public SysMenu getOne(SysMenu sysMenu) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(sysMenu.getMenuType())){
            wrapper.eq("menu_type",sysMenu.getMenuType());
        }
        if (StringUtils.isNoneBlank(sysMenu.getMenuName())){
            wrapper.eq("menu_name",sysMenu.getMenuName());
        }
        if (StringUtils.isNoneBlank(sysMenu.getMenuUrl())){
            wrapper.eq("menu_url",sysMenu.getMenuUrl());
        }
        if (Objects.nonNull(sysMenu.getMenuOrder())){
            wrapper.eq("menu_menu_order",sysMenu.getMenuOrder());
        }
        if (Objects.nonNull(sysMenu.getFileUrl())){
            wrapper.eq("menu_file_url",sysMenu.getFileUrl());
        }
        return baseMapper.selectOne(wrapper);
    }
}
