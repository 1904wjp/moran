package com.moon.joyce.example.entity.base.entity.doma;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.commons.factory.enums.Type;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/10/10-- 13:45
 * @describe:
 */
@Table(content = "根实体",isParent = true)
public class RootEntity implements Serializable {
    @Column(name="id",auto = true,comment = "主键", type = Type.BIGINT)
    @TableId(value = "id",type = IdType.AUTO)
    protected Long id;
    @Column(name="create_by",comment = "创建人", type = Type.VARCHAR)
    @TableField("create_by")
    protected String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name="create_time",comment = "创建时间", type = Type.DATETIME)
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    protected Date createTime;

    @TableField("update_by")
    @Column(name="update_by",comment = "更新人", type = Type.VARCHAR)
    protected String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name="update_time",comment = "更新时间",length = "0",type = Type.DATETIME)
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    protected Date updateTime;

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

}
