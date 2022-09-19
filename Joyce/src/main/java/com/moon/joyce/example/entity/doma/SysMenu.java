package com.moon.joyce.example.entity.doma;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.commons.factory.enums.Type;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 
 * </p>
 *
 * @author Joyce
 * @since 2021-10-08
 */
/*@Entity*/
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sys_menu",content = "菜单表")
/*@Table(name = "sys_menu")
@org.hibernate.annotations.Table(appliesTo = "sys_menu",comment = "菜单表")*/
public class SysMenu extends Page {

    private static final long serialVersionUID=1L;
    @TableField("menu_name")
 /*   @Column(name = "menu_name",columnDefinition = "varchar(12) COMMENT '菜单名称'")*/
    private String menuName;

    @Column(comment = "菜单名称")
    /*   @Column(name = "menu_name",columnDefinition = "varchar(12) COMMENT '菜单名称'")*/
    private String name;

    @Column(comment = "菜单连接")
    private String menuUrl;

    @Column(comment = "菜单链接", type = Type.VARCHAR)
   /* @Column(name = "menu_url",columnDefinition = "varchar(12) COMMENT '菜单地址'")*/
    private String url;

    @TableField("menu_order")
    /*@Column(name = "menu_order",columnDefinition = "bigint(12) COMMENT '菜单排序'")*/
    private Long menuOrder;

    @TableField("parent_id")
   /* @Column(name = "parent_id",columnDefinition = "bigint(32) COMMENT '菜单父类id'")*/
    private Long parentId;

    @TableField("menu_type")
    private String menuType;

    @TableField("file_url")
   /* @Column(name = "file_url",columnDefinition = "varchar(32) COMMENT '文件地址'")*/
    private String fileUrl;

    @Column(exist = false)
    @TableField(exist = false)
    private List<SysMenu> childSys;

    @Column(exist = false)
    @TableField(exist = false)
    private List<Long> childs;

    @Column(comment = "菜单标识")
    private String val;

    /**
     * 排版自定义
     * @param sysMenus
     * @return
     */
    public static List<SysMenu>  typeSettingSysMenu(List<SysMenu> sysMenus){
        List<SysMenu> menus = new ArrayList<>();
        List<SysMenu> menuList = sysMenus.stream().filter(x -> x.getParentId() == 0).collect(Collectors.toList());
        menus.addAll(menuList);
        return addAll(menus,sysMenus);
    }

    /**
     * 添加菜单
     * @param menus
     * @param sysMenus
     */
    private static  List<SysMenu> addAll(List<SysMenu> menus, List<SysMenu> sysMenus) {
        sysMenus.removeIf(sysMenu ->menus.stream().anyMatch(menu->menu.getId().equals(sysMenu.getId())));
        List<SysMenu> list = new ArrayList<>();
        for (SysMenu menu : menus) {
           list.add(getChildSystemsMethod(menu,sysMenus));
        }
        Map<Long, SysMenu> map = new HashMap<>();
        findSys(list, map);
        return sortSys(map);
    }

    private static SysMenu getChildSystemsMethod(SysMenu menu,List<SysMenu> sysMenus) {
        List<SysMenu> childSystems = new ArrayList<>();
        List<Long> childSystemLongs = new ArrayList<>();
       // sysMenus.removeIf(x->x.getId().equals(menu.getId()));
        for (SysMenu sysMenu : sysMenus) {
            if (sysMenu.getParentId().equals(menu.getId())){
                System.out.println("------------->"+sysMenu.getParentId()+":::"+menu.getId());
                getChildSystemsMethod(sysMenu,sysMenus);
                childSystems.add(sysMenu);
                childSystemLongs.add(sysMenu.getId()-1);
            }
        }
        menu.setChildSys(childSystems);
        menu.setChilds(childSystemLongs);
        return menu;
    }
    public static Map<Long,SysMenu> findSys(List<SysMenu> sysMenus,Map<Long, SysMenu> map){
        for (SysMenu sysMenu : sysMenus) {
            if (!map.containsKey(sysMenu.getId())){
                map.put(sysMenu.getId(),sysMenu);
            }
            if (!sysMenu.getChildSys().isEmpty()){
                findSys(sysMenu.getChildSys(),map);
            }
        }
        return map;
    }

    public static List<SysMenu> sortSys(Map<Long,SysMenu> hashMap){
        List<Long> longs = new ArrayList<>(hashMap.keySet());
        List<Object> collect = Arrays.stream(longs.toArray()).sorted().collect(Collectors.toList());
        List<SysMenu> sysMenus = new ArrayList<>();
        for (int i = 0; i < collect.size(); i++) {
            sysMenus.add(hashMap.get(collect.get(i)));
        }
        return sysMenus;
    }
}
