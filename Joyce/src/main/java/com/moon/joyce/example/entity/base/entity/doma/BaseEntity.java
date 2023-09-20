package com.moon.joyce.example.entity.base.entity.doma;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.commons.factory.enums.Type;
import com.moon.joyce.example.functionality.entity.doma.JoyceException;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author Xing Dao Rong
 * @date 2021/9/3 13:55
 * @desc 基础类实体类
 */
@Table(name = "基础类",isParent = true)
public class BaseEntity  extends RootEntity{
    private static final long serialVersionUID = 9019164396662157010L;
   /* @Id*/

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

    @Column(comment = "处理人")
    private String userIds;

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

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
    @TableLogic
    @Column(name="delete_flag",comment = "删除标志", type = Type.INT,defaultValue = "1")
    @TableField(value = "delete_flag")
    private Integer deleteFlag;

    @Column(name = "create_ids",comment = "创建用户id",type = Type.BIGINT,length = "64")
    @TableField(value = "create_ids")
    private Long createIds;

    @Column(name = "update_ids",comment = "修改用户id",type = Type.BIGINT,length = "64")
    @TableField(value = "update_ids")
    private Long updateIds;

    @Column(comment = "参数表id",type = Type.BIGINT)
    private Long addParamsId;

    @Column(exist = false,comment = "参数")
    @TableField(exist = false)
    private AddParams addParams;

    @Column(exist = false,comment = "vo参数")
    @TableField(exist = false)
    private Map<String,Object> voParams;

    @Column(exist = false,comment = "查询关键字")
    @TableField(exist = false)
    private String searchWord;


    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getAddParamsId() {
        return addParamsId;
    }

    public void setAddParamsId(Long addParamsId) {
        this.addParamsId = addParamsId;
    }

    public AddParams getAddParams() {
        return addParams;
    }

    public void setAddParams(AddParams addParams) {
        this.addParams = addParams;
    }

    public Map<String, Object> getVoParams() {
        return voParams;
    }

    public void setVoParams(Map<String, Object> voParams) {
        this.voParams = voParams;
    }

    public Long getCreateIds() {
        return createIds;
    }

    public void setCreateIds(Long createIds) {
        this.createIds = createIds;
    }

    public Long getUpdateIds() {
        return updateIds;
    }

    public void setUpdateIds(Long updateIds) {
        this.updateIds = updateIds;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseEntity)){
           throw new JoyceException("该数据不是需要的数据，无法比较");
        }
        return this.id.toString().equals(((BaseEntity) obj).id.toString());
    }

    public static boolean equals(Object obj1,Object obj2){
        if (!(obj1 instanceof BaseEntity)){
            throw new JoyceException("该数据不是需要的数据，无法比较");
        }
        return ((BaseEntity) obj1).id.toString().equals(((BaseEntity) obj2).id.toString());
    }
}
