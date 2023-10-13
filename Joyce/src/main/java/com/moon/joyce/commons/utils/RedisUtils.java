package com.moon.joyce.commons.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Objects;

/**
 * @Author: XingDaoRong
 * @Date: 2022/2/25
 */
@Component
public class RedisUtils {
    @Autowired
    private static RedisTemplate<String,Object> redisTemplate;

    public  Object get(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    public RedisUtils(){};
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
    public static Jedis getInstance(String host ,int port,String password){
        if (Objects.isNull(jedis)){
            jedis = new Jedis(host,port);
            if (Objects.nonNull(password)){
                jedis.auth(password);
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
      * @param jedis 缓存对象
     * @param code 验证码
     * @param uniqueAppend 唯一拼接
     * @param maxCount 最大次数
     * @param time 过期时间（单位秒）
     * @return
     * 获取验证码
     */
    public static String getVerifyCode(Jedis jedis,String code, String uniqueAppend, int maxCount, long time){
        //查看当前的验证码是否超过次数
        int result = inputNumberByTime(jedis, time, uniqueAppend, maxCount);
        String currentCount = uniqueAppend;
        //若无超出规定范围次数
        if (result==1){
              //查询次数是否过期，如果过期，验证码将会失效
              if (jedis.ttl(currentCount)==-1||jedis.ttl(currentCount)==-2){
                  System.out.println("该验证码已经失效");
                  jedis.del(currentCount);
                  jedis.close();
                  return jedis.ttl(currentCount).toString();
              }else {
                  //如果没有失效，直接返回验证码
                  System.out.println("剩余时间"+jedis.ttl(currentCount));
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
        String currentCount = uniqueAppend;
        //检测剩余次数是否为空，若为空则创建redis缓存，将次数标记为1
        if (Objects.isNull(jedis.get(currentCount))){
            System.out.println("设置原本过期的时间"+time);
            jedis.setex(currentCount,time,"1");
            return 1;
        //检测剩余次数是否小于规定次数，若为真，则将次数加1
        }else if(Integer.parseInt(jedis.get(currentCount))<maxCount){
            jedis.incr(currentCount);
            return 1;
        }else {
            //次数超出规定范围，直接关闭，返回0
            jedis.close();
            return 0;
        }
    }
}
