package com.moon.joyce.commons.base.cotroller;


import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.RedisUtils;
import com.moon.joyce.example.entity.DbBaseSetting;
import com.moon.joyce.example.entity.UU;
import com.moon.joyce.example.entity.User;
import com.moon.joyce.example.functionality.entity.Setting;
import com.moon.joyce.example.functionality.service.DbBaseSettingService;
import com.moon.joyce.example.service.UUService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author Xing Dao Rong
 * @date 2021/9/3 13:55
 * @desc 基本controller
 */
@Controller
public class BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //app链接
    @Value("${app.url}")
    public String appUrl;
    @Value("${app.urlValue}")
    public String appUrlValue;
    //app名字
    @Value("${app.name}")
    public String appName;
    //app版本
    @Value("${app.version}")
    public String appVersion;
    //上传文件路径
    @Value("${file.upload.path}")
    public  String filePath;
    @Value("${setting.super_administrator}")
    public String superAdministrator;

    //私有命令
    @Value("$(command.start)")
    public  volatile static String c_start;
    @Value("$(command.close)")
    public volatile static String c_close;
    @Value("$(command.init)")
    public volatile static String c_init;
    @Value("$(command.clear)")
    public volatile static String c_clear;
    //sessionMap
    public List<User> sessionUsers = new ArrayList<>();
    @Autowired
    private DbBaseSettingService dbBaseSettingService;

    //缓存
    public static Jedis cache ;

    /**
     * 获得session
     * @return
     */
    public HttpSession getSession(){
        return  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    /**
     * 获取在线用户
     * @return
     */
    public User getSessionUser(){
        return  (User) getSession().getAttribute(Constant.SESSION_USER);
    }
    /**
     * 获取在线用户id
     * @return
     */
    public String getSessionUserName(){
        return  ((User) getSession().getAttribute(Constant.SESSION_USER)).getUsername();
    }

    /**
     * 获取在线用户id
     * @return
     */
    public Long getSessionUserId(){
        return  ((User) getSession().getAttribute(Constant.SESSION_USER)).getId();
    }

    /**
     * 获取会话数据
     * @param key
     * @return
     */
    public Object getSessionValue(String key){
        return   getSession().getAttribute(key);
    }

    /**
     * 移除当前登录用户
     */
    public void removeSessionUser(){
        getSession().removeAttribute(Constant.SESSION_USER);

    }

    /**
     * 移除会话数据
     * @param key
     */
    public void removeSessionValue(String key){
         getSession().removeAttribute(key);
    }

    /**
     * 设置 sessions属性
     * @param key
     * @param object
     */
    public void setSession(String key,Object object){
            getSession().setAttribute(key,object);
    }

    /**
     * 设置多个session
     * @param map
     */
    @Deprecated
    public void setSessions(Map<String,Object> map){
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            getSession().setAttribute(entry.getKey(),entry.getValue());
        }
    }

    /**
     * 获取当前数据源
     * @return
     */
    public Setting getCurrentSetting(){
        Setting setting = null;
        if (Objects.nonNull(getSession().getAttribute(getSessionUser().getId()+Constant.CURRENT_SETTING))){
            setting = (Setting) getSession().getAttribute(getSessionUser().getId()+Constant.CURRENT_SETTING);
        }
        return setting;
    }

    /**
     * 获取当前数据源
     * @return
     */
    public void removeCurrentSetting(){
        if (Objects.nonNull(getCurrentSetting())){
            getSession().removeAttribute(getSessionUser().getId()+Constant.CURRENT_SETTING);
        }
    }

    //关闭数据源
    public  boolean shutdownDatasource(){
        if (Objects.nonNull(getCurrentSetting())){
                logger.info("数据源关闭成功");
                return dbBaseSettingService.switchDataSource(getCurrentSetting().getDbBaseSetting(), Constant.REMOVE_DATASOURCE);
        }
         logger.info("数据源关闭失败");
        return false;
    }
    //开启数据源
    public boolean startupDatasource(){
        if (Objects.nonNull(getCurrentSetting())){
            if (Objects.nonNull(getCurrentSetting().getDbBaseSetting())){
                 logger.info("当前为默认系统数据源");
                return dbBaseSettingService.switchDataSource(getCurrentSetting().getDbBaseSetting(), Constant.CREATE_DATASOURCE);
            }
        }
        logger.info("当前数据源:" + getCurrentSetting().getDbBaseSetting().getDataSourceName());
        return false;
    }

}
