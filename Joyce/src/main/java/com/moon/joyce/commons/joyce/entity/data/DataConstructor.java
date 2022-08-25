package com.moon.joyce.commons.joyce.entity.data;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/08/25-- 15:14
 * @describe: 基本的数据结构
 */
public interface DataConstructor<T> {
    /**
     * 获取长度
     * @return
     */
    int len();

    /**
     * 新增元素
     * @param t
     */
    boolean add(T t);

    /**
     * 是否为空
     * @return
     */
    boolean isEmpty();

    /**
     * 删除元素
     * @param t
     */
    boolean remove(T t);
}
