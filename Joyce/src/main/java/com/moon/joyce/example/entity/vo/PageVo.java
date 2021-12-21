package com.moon.joyce.example.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Xing Dao Rong
 * @date 2021/9/11 10:36
 * @desc
 */

public class PageVo implements Serializable {

    private static final long serialVersionUID = 6864620675861590712L;
    //总数据条数
    private int total;
    //分页查询的数据集合
    private List<?> rows;

    public PageVo(List<?> list, int total) {
        this.rows = list;
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
