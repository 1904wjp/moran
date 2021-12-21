package com.moon.joyce.commons.base.cotroller;


import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.entity.DbBaseSetting;
import com.moon.joyce.example.entity.User;
import com.moon.joyce.example.functionality.entity.Setting;
import com.moon.joyce.example.functionality.service.DbBaseSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Xing Dao Rong
 * @date 2021/9/3 13:55
 * @desc 基本controller
 */
@Controller
public class BaseController {
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
    public String filePath;
    @Value("${setting.super_administrator}")
    public String superAdministrator;
    //sessionMap
    public Map<String,Object> sessionMap = new HashMap<>();
    @Autowired
    private DbBaseSettingService dbBaseSettingService;
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
     * set sessions属性
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
        return (Setting) getSession().getAttribute(getSessionUser().getId()+Constant.CURRENT_SETTING);
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
            if (Objects.nonNull(getCurrentSetting().getDbBaseSetting())){
                return dbBaseSettingService.switchDataSource(getCurrentSetting().getDbBaseSetting(), Constant.REMOVE_DATASOURCE);
            }
        }
        return false;
    }
    //开启数据源
    public boolean startupDatasource(){
        if (Objects.nonNull(getCurrentSetting())){
            if (Objects.nonNull(getCurrentSetting().getDbBaseSetting())){
                return dbBaseSettingService.switchDataSource(getCurrentSetting().getDbBaseSetting(), Constant.CREATE_DATASOURCE);
            }
        }
        return false;
    }
}
