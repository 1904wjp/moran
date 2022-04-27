package com.moon.joyce.commons.utils;

import redis.clients.jedis.Jedis;


import java.util.Objects;

/**
 * @Author: XingDaoRong
 * @Date: 2022/2/25
 */
public class RedisUtils {
    private RedisUtils(){};
    static Jedis  jedis = null;
    public static   String redisPassword;
    //Jedis实例化
    public static Jedis getInstance(){
        if (Objects.isNull(jedis)){
            jedis = new Jedis("localhost",6379);
            if (Objects.nonNull(redisPassword)){
                jedis.auth(redisPassword);
            }
        }
        return jedis;
    }

    /**
     *校验验证码
     * @param code 验证码
     * @param uniqueAppend 唯一拼接
     * @param maxCount 最大次数
     * @return
     */
    public static int compareCode(Jedis jedis,String code, String uniqueAppend, int maxCount, long time){
        String redisCode = getVerifyCode(jedis, code, uniqueAppend, maxCount, time);
        if (!"-1".equals(redisCode)&&!"0".equals(redisCode)){
            if (code.equals(redisCode)){
                return 1;
            }
            return -1;
        }
        return 0;
    }

    /**
     * 设置验证码
     * @param jedis 对象
     * @param uniqueAppend 唯一拼接
     * @param vailTime 有效时间
     * @return
     */
    public static int setVerifyCode(Jedis jedis,String uniqueAppend,  long vailTime,String verityCode){
        jedis.setex(uniqueAppend, vailTime,verityCode);
        if (jedis.ttl(uniqueAppend)==-2||jedis.ttl(uniqueAppend)==-1){
            return 1;
        }
        return 0;
    }

    /**
     *
     * @param code 验证码
     * @param uniqueAppend 唯一拼接
     * @param maxCount 最大次数
     * @return
     */
    public static String getVerifyCode(Jedis jedis,String code, String uniqueAppend, int maxCount, long time){
        //查看当前的验证码是否超过次数
        int result = inputNumberByTime(jedis, time, uniqueAppend, maxCount);
        if (result==1){
              if (jedis.ttl(code)==-1||jedis.ttl(code)==-2){
                  jedis.close();
                  return "-1";
              }else {
                  code =  jedis.get(code);
                  return code;
              }
        }
       return "0";
    }

    /**
     * 规定的时间是否超过次数
     * @param jedis 对象
     * @param time 有效时间
     * @param uniqueAppend 唯一拼接
     * @param maxCount 最大次数
     * @return
     */
    private static int inputNumberByTime(Jedis jedis,long time,String uniqueAppend,int maxCount){
        String currentCount = "current_count_"+uniqueAppend;
        String countStr = jedis.get(uniqueAppend);
        if (Objects.isNull(countStr)){
            jedis.setex(currentCount,time,"1");
            return 1;
        }else if(Integer.parseInt(jedis.get(currentCount))<maxCount){
            jedis.incr(currentCount);
            return 1;
        }else {
            jedis.close();
            return 0;
        }
    }
}
