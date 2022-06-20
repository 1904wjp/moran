package com.moon.joyce.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.Ids;
import com.moon.joyce.commons.annotation.Table;
import com.moon.joyce.commons.factory.enums.TableStrategy;
import com.moon.joyce.example.entity.dto.Page;


/**
 * <p>
 * 
 * </p>
 *
 * @author Joyce
 * @since 2021-10-08
 */
/*@Entity*/
@TableName("sys_menu")
@Table(name = "sys_menu",strategy = TableStrategy.SECURITY)
/*@Table(name = "sys_menu")
@org.hibernate.annotations.Table(appliesTo = "sys_menu",comment = "菜单表")*/
public class SysMenu extends Page {

    private static final long serialVersionUID=1L;
    @Ids
    @TableField("menu_name")
 /*   @Column(name = "menu_name",columnDefinition = "varchar(12) COMMENT '菜单名称'")*/
    private String menuName;

    @TableField("menu_url")
   /* @Column(name = "menu_url",columnDefinition = "varchar(12) COMMENT '菜单地址'")*/
    private String menuUrl;

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

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Long getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Long menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SysMenu{");
        sb.append("menuName='").append(menuName).append('\'');
        sb.append(", menuUrl='").append(menuUrl).append('\'');
        sb.append(", menuOrder=").append(menuOrder);
        sb.append(", parentId=").append(parentId);
        sb.append(", menuType='").append(menuType).append('\'');
        sb.append(", fileUrl='").append(fileUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
