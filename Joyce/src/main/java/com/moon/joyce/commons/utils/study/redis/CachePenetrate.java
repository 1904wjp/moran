package com.moon.joyce.commons.utils.study.redis;

/**
 * @Author: XingDaoRong
 * @Date: 2022/3/9
 */
public class CachePenetrate {
    /**
     * 缓存穿透
     * 可能出现：
     *  应用服务器压力变大
     *  redis命中率降低
     *  一直查询数据库
     *  现象：
     *  redis查询不到数据库
     *  出现很多非正常url访问
     *  解决方法：
     *  1对空值缓存：
     *  如果查询返回的数据为空，我们将这个空结果进行缓存，设置过期时间不超过5分钟
     *  2设置ip白名单：
     *  使用bitmap用id比较进行访问
     *  3采用布隆过滤器：
     *  加强版IP白名单
     *  4实时监控:
     *  运维人员将对应的恶意操作ip加入黑名单
     */
}
