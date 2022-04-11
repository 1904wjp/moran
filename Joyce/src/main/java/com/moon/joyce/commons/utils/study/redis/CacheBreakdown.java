package com.moon.joyce.commons.utils.study.redis;

/**
 * @Author: XingDaoRong
 * @Date: 2022/3/9
 */
public class CacheBreakdown {
    /**
     * 缓存击穿
     * 可能出现:
     * 1 数据库压力增大
     * 2 redis没有出现大量key过期
     * 3 redis正常运行
     * 现象原因：
     *  热门访问key过期
     * 解决:
     *  1 预先设置热门数据：
     *   提前热门词汇加入redis,加强过期时间
     *  2 实时调整：
     *   运维人员现场热门词汇加入redis
     *  3 使用锁
     *   去查询key，若返回值为空。则过段时间进行查询，成功则释放锁
     */
}
