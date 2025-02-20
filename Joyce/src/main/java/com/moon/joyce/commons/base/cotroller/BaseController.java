package com.moon.joyce.commons.base.cotroller;


import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.factory.demo.UrlFactory;
import com.moon.joyce.commons.factory.entity.url.MethodUrlEntity;
import com.moon.joyce.commons.factory.entity.url.UrlPriEntity;
import com.moon.joyce.commons.utils.CommonUtils;
import com.moon.joyce.commons.utils.HttpUtils;
import com.moon.joyce.commons.utils.R;
import com.moon.joyce.commons.utils.RedisUtils;
import com.moon.joyce.example.entity.base.entity.doma.AddParams;
import com.moon.joyce.example.entity.base.entity.doma.BaseEntity;
import com.moon.joyce.example.entity.doma.User;
import com.moon.joyce.example.functionality.entity.doma.Auth;
import com.moon.joyce.example.functionality.entity.doma.Logging;
import com.moon.joyce.example.functionality.entity.doma.Setting;
import com.moon.joyce.example.functionality.entity.doma.Uri;
import com.moon.joyce.example.functionality.service.AddParamsService;
import com.moon.joyce.example.functionality.service.DbBaseSettingService;
import com.moon.joyce.example.functionality.service.LoggingService;
import com.moon.joyce.example.service.ChatRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Xing Dao Rong
 * @date 2021/9/3 13:55
 * @desc 基本controller
 */
@Controller
public class BaseController extends R {
    @Autowired
    protected RedisTemplate<String,Object> redisTemplate;
    @Autowired
    protected LoggingService loggingService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
    @Value("${spring.redis.host}")
    protected  String redisHost;
    @Value("${spring.redis.port}")
    protected  String redisPort;
    @Value("${spring.redis.password}")
    protected String redisPassword;
    protected static  boolean isRedisConnection = true;
    @Value("${setting.super_administrator}")
    public String superAdministrator;
    @Value("${auto.controller.package}")
    private String ps;
    //私有命令
    @Value("$(command.start)")
    public   String c_start;
    @Value("$(command.close)")
    public  String c_close;
    @Value("$(command.init)")
    public  String c_init;
    @Value("$(command.clear)")
    public  String c_clear;
    //sessionMap
    public List<User> sessionUsers = new CopyOnWriteArrayList<>();
    @Autowired
    private DbBaseSettingService dbBaseSettingService;
    @Autowired
    protected ChatRecordService chatRecordService;
    //缓存
    protected static Jedis cache ;
    //缓存的聊天记录
    protected static String addChatRecords = "add_chatRecord";
    protected static  ConcurrentHashMap<Long, Auth> authMap = new ConcurrentHashMap<>();
    private static Map<String, Uri> uriMap;
    protected static String allRecords = "ALL_RECORDS";
    @Autowired
    private AddParamsService addParamsService;
  //  public static Map<String,Object> redisMap = new HashMap<>();
    /**
     * 获得session
     * @return
     */
    public HttpSession getSession(){
        return  ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
    }

    /**
     * 获取在线用户
     * @return
     */
    public User getSessionUser(){
        return  (User) getSession().getAttribute(Constant.SESSION_USER);
    }

    /**
     * 获取在线用户名
     * @return
     */
    public String getSessionUserName(){
        return  getSessionUser().getUsername();
    }

    /**
     * 获取在线用户id
     * @return
     */
    public Long getSessionUserId(){
        return  getSessionUser().getId();
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
        getSession().removeAttribute(getSessionUserId()+"ip");
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
     * 移除当前数据源
     * @return
     */
    public void removeCurrentSetting(){
        if (Objects.nonNull(getCurrentSetting())){
            getSession().removeAttribute(getSessionUser().getId()+Constant.CURRENT_SETTING);
        }
    }

    //关闭数据源
    public void shutdownDatasource(){
        if (Objects.nonNull(getCurrentSetting())){
            try {
                dbBaseSettingService.switchDataSource(getCurrentSetting().getDbBaseSetting(), Constant.REMOVE_DATASOURCE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.warn("数据源关闭成功");
            return;
        }
         logger.warn("数据源关闭失败");
    }

    //开启数据源
    public void startupDatasource(){
        if (Objects.nonNull(getCurrentSetting())){
            if (Objects.nonNull(getCurrentSetting().getDbBaseSetting())){
                 logger.warn("当前为默认系统数据源,使用此方法('startupDatasource')接口结束之前需要使用‘shutdownDatasource()’方法结束，否则会出现数据源异常");
                try {
                    Thread.sleep(1000);
                    dbBaseSettingService.switchDataSource(getCurrentSetting().getDbBaseSetting(), Constant.CREATE_DATASOURCE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.warn("数据源开启成功,当前数据源:{}" , Objects.requireNonNull(getCurrentSetting()).getDbBaseSetting().getDataSourceName());
            }
        }
    }

    /**
     * 获取redis操作对象
     * @return
     */
    public   ValueOperations<String, Object>  getRedisValueOperation(){
        return redisTemplate.opsForValue();
    }

    /**
     * 获取redis对象过期时间
     * @param k
     * @return
     */
    public long getExpireTime(String k){
        if (Objects.isNull(k)||Objects.isNull(getRedisValueOperation().get(k))){
            return -3;
        }
        return getRedisValueOperation().getOperations().getExpire(k);
    }

    /**
     * 设置基础属性
     * @param entity
     */
    public void setBaseField(BaseEntity entity){
        entity.setDeleteFlag(Constant.UNDELETE_STATUS);
        if (Objects.isNull(entity.getId())){
            entity.setCreateBy(getSessionUserName());
            entity.setCreateTime(new Date());
            entity.setCreateIds(getSessionUserId());
        }else {
            entity.setUpdateBy(getSessionUserName());
            entity.setUpdateTime(new Date());
            entity.setUpdateIds(getSessionUserId());
        }
    }

    /**
     * 获取所有接口
     * @return
     */
    public Map<String, Uri> getMap() {
       if (uriMap==null){
           uriMap = new HashMap<>();
           Map<MethodUrlEntity, UrlPriEntity> map = UrlFactory.init(ps);
           for (Map.Entry<MethodUrlEntity, UrlPriEntity> entry : map.entrySet()) {
               Uri uri = new Uri();
               uri.setName(entry.getValue().getName()+"_"+entry.getKey().getName());
               uri.setUrl(entry.getValue().getPri()+entry.getKey().getUrl());
               uri.setParams(entry.getKey().getParams());
               uri.setScene(entry.getValue().getScene());
               uriMap.put(uri.getUrl(),uri);
           }
       }
       return uriMap;
    }

    /**
     * 验证单点登录
     * @param user
     * @param request
     * @return
     */
    protected int getAuthCode(User user ,  HttpServletRequest request){
       /* MySessionContext myc = MySessionContext.getInstance();*/
        logger.info("--------------------->登录ip:"+HttpUtils.getIpAddress(request));
        Auth auth = authMap.get(user.getId());
        if (Objects.isNull(auth)){
            return 200;
        }
      /*  HttpSession session = myc.getSession(request.getSession().getId());
        if (Objects.isNull(session)){
            return 200;
        }*/
        if (auth.getSessionId().equals(request.getSession().getId())){
            return 206;
        }
        if (!auth.getIp().equals(HttpUtils.getIpAddress(request))){
            return 500;
        }
        return 500;
    }

    /**
     * 登录存入map
     * @param user
     * @param request
     * @return
     */
    protected void authMapPut(User user ,  HttpServletRequest request){
        authMap.put(user.getId(),new Auth(request.getSession().getId(),HttpUtils.getIpAddress(request)));
    }


    /**
     * 获取参数
     * @param baseEntity
     * @return
     */
    protected BaseEntity getParam(BaseEntity baseEntity){
        if (CommonUtils.allNonNull(baseEntity,baseEntity.getAddParamsId())){
            AddParams params = addParamsService.getParams(baseEntity.getAddParamsId());
            if (CommonUtils.allNonNull(params,params.getParams(),params.getParamsMap())){
                baseEntity.setAddParams(params);
            }
        }
        return baseEntity;
    }

    /**
     * sessionUsers容器移除当前登录人
     */
    protected  void sessionUsersRmCurrentUser(){
       if (!sessionUsers.isEmpty()){
           for (int i = 0; i < sessionUsers.size(); i++) {
               if (sessionUsers.get(i).equals(getSessionUser())){
                   sessionUsers.remove(sessionUsers.get(i));
                   break;
               }
           }
       }
    }

    /**
     * 获取基础日志实体类
     * @param eventDesc
     * @param params
     * @return
     */
    public  Logging getLogging(String eventDesc,String params,String uri){
        uri = uri.replace("//","/");
        Logging logging = new Logging(eventDesc,params,uri);
        if (Objects.nonNull(getSessionUser())){
            setBaseField(logging);
            String logIp = getSessionValue(getSessionUserId() + "ip").toString();
            logging.setLoginIp(logIp);
            logging.setUsername(getSessionUserName());
        }
       // logging.setLoginSys(System.getProperty("os.name"));
        return logging;
    }


    public  Logging getLogging(boolean bool,String eventDesc,String params,String uri){
        return getLogging(eventDesc+(bool ? "成功":"失败"),params,uri);
    }
}
