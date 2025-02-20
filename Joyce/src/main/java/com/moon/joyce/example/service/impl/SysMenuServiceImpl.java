package com.moon.joyce.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.commons.annotation.RedisValueComponet;
import com.moon.joyce.example.entity.doma.SysMenu;
import com.moon.joyce.example.mapper.SysMenuMapper;
import com.moon.joyce.example.service.CommonService;
import com.moon.joyce.example.service.SysMenuService;
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
 * @since 2021-10-08
 */
@Service
@RedisValueComponet("SysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper,SysMenu> implements SysMenuService , CommonService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    public List<SysMenu> getMenus(SysMenu menu) {
        QueryWrapper wrapper = new QueryWrapper();
        if (Objects.nonNull(menu)){
            if (StringUtils.isNotEmpty(menu.getName())){
                wrapper.like("name",menu.getName());
            }
            if (StringUtils.isNotEmpty(menu.getUrl())){
                wrapper.like("menu_url",menu.getUrl());
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
        if (StringUtils.isNoneBlank(sysMenu.getName())){
            wrapper.eq("name",sysMenu.getName());
        }
        if (StringUtils.isNoneBlank(sysMenu.getUrl())){
            wrapper.eq("menu_url",sysMenu.getUrl());
        }
        if (Objects.nonNull(sysMenu.getMenuOrder())){
            wrapper.eq("menu_menu_order",sysMenu.getMenuOrder());
        }
        if (Objects.nonNull(sysMenu.getFileUrl())){
            wrapper.eq("menu_file_url",sysMenu.getFileUrl());
        }
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<SysMenu> getList(Long sessionUserId) {
        return sysMenuMapper.getList(sessionUserId);
    }
}
