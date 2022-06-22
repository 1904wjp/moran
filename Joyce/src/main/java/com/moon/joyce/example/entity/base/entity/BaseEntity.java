package com.moon.joyce.example.entity.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moon.joyce.commons.annotation.Column;
import com.moon.joyce.commons.annotation.Table;
import com.moon.joyce.commons.factory.enums.Type;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Xing Dao Rong
 * @date 2021/9/3 13:55
 * @desc 基础类实体类
 */
@Table(name = "基础类",isParent = true)
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 9019164396662157010L;
   /* @Id*/
    @Column(name="id",auto = true,comment = "主键", type = Type.BIGINT)
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @Column(name="create_by",comment = "创建人", type = Type.VARCHAR)
    @TableField("create_by")
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name="createTime",comment = "创建时间",length = "0", type = Type.DATETIME)
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("update_by")
    @Column(name="update_by",comment = "更新人", type = Type.VARCHAR)
    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name="update_time",comment = "更新时间",length = "0",type = Type.DATETIME)
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableLogic
    @Column(name="delete_flag",comment = "删除标志", type = Type.INT,defaultValue = "0")
    @TableField(value = "delete_flag")
    private Integer deleteFlag;

    @TableField(exist = false)
    private Map<String,String> params;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BaseEntity){
            return this.id.equals(((BaseEntity) obj).id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(String.valueOf(this.id % 16));
    }
}
