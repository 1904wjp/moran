package com.moon.joyce.commons.utils.study.redis;

/**
 * @Author: XingDaoRong
 * @Date: 2022/3/9
 */
public class CacheAvalanche {
    /**
     * 缓存雪崩
     * 可能特点:
     * 1 数据库压力变大服务器崩溃
     *  现象：
     *  在极少的时间内，查询的大量key过期
     *  解决方案
     *  1 构建多级缓存架构
     *      nginx缓存+redis缓存+其他缓存（ehcache等）
     *  2 使用锁或者队列
     *     进行一次性读写，避免失效时请求落在储存上，高并发不可用
     *  3 设置过期更新缓存
     *  4 缓存时间分散
     */
}
