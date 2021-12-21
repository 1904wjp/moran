package com.moon.joyce.example.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.moon.joyce.example.entity.base.entity.BaseEntity;



/**
 * @author Xing Dao Rong
 * @date 2021/9/11 10:33
 * @desc 分页查询类
 */
/*@Entity
@Table(name = "page")
@org.hibernate.annotations.Table(appliesTo = "page",comment = "分页实体类")*/
public class Page extends BaseEntity {
    private static final long serialVersionUID = 2587093547879634734L;
    // 偏移页数
    @TableField(exist = false)
    /*@Transient*/
    private int offset;
    // 每页条数
    @TableField(exist = false)
    /*@Transient*/
    private int pageNumber;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
