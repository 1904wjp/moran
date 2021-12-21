package com.moon.joyce.commons.utils;

import com.moon.joyce.example.entity.vo.SysMenuVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xing Dao Rong
 * @date 2021/10/11 13:45
 * @desc 菜单工具类
 */
public class TreeUtils {
    /**
     * 待用菜单
     */
    private static List<SysMenuVo> sysMenuVos=null;

    /**
     * 转化成树形
     * @param list
     * @return
     */
    public static List<SysMenuVo> toTree(List<SysMenuVo> list){
        sysMenuVos = new ArrayList<>(list);
        //获取顶级菜单存入roots
        List<SysMenuVo> roots = new ArrayList<>();
        for (SysMenuVo sysMenuVo : list) {
            if (sysMenuVo.getParentId()==0||sysMenuVo.getParentId()==-1){
                roots.add(sysMenuVo);
            }
        }
        //移除父类，获取子类菜单集合
        sysMenuVos.removeAll(roots);
        for (SysMenuVo sysMenuVo : roots) {
            sysMenuVo.setChildren(getCurrentNodeChildren(sysMenuVo));
        }
        return roots;
    }

    private static List<SysMenuVo> getCurrentNodeChildren(SysMenuVo sysMenuVo) {
        // 判断当前节点有没有子节点, 没有则创建一个空长度的 List, 有就使用之前已有的所有子节点.
        List<SysMenuVo> childList = sysMenuVo.getChildren() == null ? new ArrayList<>() : sysMenuVo.getChildren();
        // 从 "待用菜单列表" 中找到当前节点的所有子节点
        for (SysMenuVo child : sysMenuVos) {
            if (sysMenuVo.getId().equals(child.getParentId())) {
                childList.add(child);
            }
        }
        // 将当前节点的所有子节点从 "待用菜单列表" 中删除
        sysMenuVos.removeAll(childList);
        // 所有的子节点再寻找它们自己的子节点
        for (SysMenuVo menuTreeVO : childList) {
            menuTreeVO.setChildren(getCurrentNodeChildren(menuTreeVO));
        }
        return childList;
    }
}
