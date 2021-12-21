package com.moon.joyce.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.SysMenu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Joyce
 * @since 2021-10-08
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 获取所有菜单数据的列表
     * @param menu
     * @return
     */
    List<SysMenu> getMenus(SysMenu menu);

    /**
     * 根据id删除菜单
     * @param id
     * @return
     */
    int deleteMenuById(Long id);

    /**
     * 获取一个菜单新信息
     * @param sysMenu
     * @return
     */
    SysMenu getOne(SysMenu sysMenu);
}
