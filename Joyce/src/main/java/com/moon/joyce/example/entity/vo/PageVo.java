package com.moon.joyce.example.entity.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;

/**
 * @author Xing Dao Rong
 * @date 2021/9/11 10:36
 * @desc
 */

public class PageVo<T> implements Serializable {

    private static final long serialVersionUID = 6864620675861590712L;
    //总数据条数
    private long total;
    //分页查询的数据集合
    private List<T> rows;

    public PageVo(List<T> list, long total) {
        this.rows = list;
        this.total = total;
    }
    public PageVo(IPage<T> page){
        this.rows = page.getRecords();
        this.total = page.getTotal();
    }
    public long getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
