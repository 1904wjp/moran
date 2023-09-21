package com.moon.joyce.example.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moon.joyce.commons.annotation.url.MethodUrl;
import com.moon.joyce.commons.annotation.url.UriPri;
import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.*;
import com.moon.joyce.example.entity.doma.ChatRecord;
import com.moon.joyce.example.entity.doma.UU;
import com.moon.joyce.example.entity.doma.User;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.entity.vo.UserChartVo;
import com.moon.joyce.example.functionality.entity.doma.*;
import com.moon.joyce.example.functionality.service.FileService;

import com.moon.joyce.example.service.UserService;
import com.moon.joyce.example.service.serviceControllerDetails.UserControllerDetailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Joyce
 * @since 2021-09-01
 */
@Controller
@UriPri(name = "用户相关接口", pri = "/example/user")
@RequestMapping("/example/user")
public class UserController extends BaseController {

   // private User user;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
/*********************************************************************************************************************************************/
    /**************全局变量***********************/
    private static HashMap<Long, String> USER_SESSION_MAP = new HashMap<>();
    private static HashMap<String, RequestCount> IP_SESSION_MAP = new HashMap<>();
    @Autowired
    private UserService userService;
    //redis缓存

    /**
     * 页面路径前缀
     */
    private final String pagePrefix = "user/";

    /**
     * url的路径前缀
     */
    private final String urlPrefix = "/example/user/";

    /**
     * 文件服务
     */
    @Autowired
    private FileService fileService;

    /**
     * 文件路径
     */
    /*@Value("${file.upload.path}")
    private  String filePah;*/
    /**
     * 细节处理
     */
    @Autowired
    private UserControllerDetailService userServiceControllerDetailService;

    /*****************************************************************************************************************************************************************/
    /***********页面映射****************/

    /**
     * bar页面
     */
    @GetMapping("/headerBar")
    public String getHeaderBarPage() {
        return "common/public/headerBar.html";
    }

    /**
     * user列表页面
     */
    @GetMapping("/userList")
    public String getUsersPage() {
        return pagePrefix + "userListPage";
    }

    /**
     * 注册页面
     * @return
     */
    @GetMapping("/regist")
    public String registPage() {
        return pagePrefix + "registPage";
    }

    /**
     * 注册结果页面
     * @return
     */
    @GetMapping("/statusResult")
    public String statusResultPage() {
        return pagePrefix + "statusResultPage";
    }

    /**
     * 登录页面
     * @return
     */
    @GetMapping("/login")
    public String loginPage() {
        return pagePrefix + "loginPage";
    }

    /**
     * 查看页面
     * @param id
     * @param map
     * @return
     */
    //@MethodUrl(url = "/queryUser/{id}",name = "查看user页面")
    @GetMapping("/queryUser/{id}")
    public String queryUserPage(@PathVariable("id") Long id, ModelMap map) {
        User dbUser = userService.getById(id);
        map.addAttribute("user", dbUser);
        return pagePrefix + "queryUserPage";
    }

    /**
     * 编辑页面
     * @param id
     * @param map
     * @return
     */
    @Transactional
    //@MethodUrl(url = "/editUser/{id}",name = "编辑user页面")
    @GetMapping("/editUser/{id}")
    public String updateUserPage(@PathVariable Long id, ModelMap map) {
    /* if (!id.equals( getSessionUser().getId())){
            return commonPagePrefix+"404";
        }*/
        User dbUser = userService.getById(id);
        if (Objects.nonNull(dbUser)) {
            map.addAttribute("user", dbUser);
            return pagePrefix + "updateUserPage";
        }
        return pagePrefix + "userListPage";
    }

    /**
     * 聊天页面
     * @return
     */
    //@MethodUrl(url = "/websocket",name = "聊天页面")
    @RequestMapping("/websocket")
    public String indexPage(ModelMap map) {
        User sessionUser = getSessionUser();
        List<User> userList = userService.getUserList(null);
        userList.remove(getSessionUser());
        map.put("users", userList);
        map.addAttribute("user", sessionUser);
        map.addAttribute("websocketUrlPrefix", appUrlValue + "/websocket");
        return pagePrefix + "websocketPage";
    }

    /**************************************************************************************************************************************************************************************************************/
    /**********逻辑判断**********/

    /**
     * 好友
      * @param nickname
     * @return
     */
    @ResponseBody
    //@MethodUrl(url = "/sessionUsers",name = "好友")
    @GetMapping("/sessionUsers")
    public Result getSessionUsers(@RequestParam String nickname) {
        String cacheFriends = UUController.uniqueListSum;
        List<UU> uus = (List<UU>) getRedisValueOperation().get(cacheFriends);
        List<Long> longs = new ArrayList<>();
        if (Objects.nonNull(uus)) {
            List<UU> collect = uus.stream().filter(x -> x.getResultStr().equals("0")
                    && Integer.parseInt(x.getType()) < 5
                    && (x.getUserAId().equals(getSessionUserId()) || x.getUserBId().equals(getSessionUserId()))).collect(Collectors.toList());
            for (UU uu : collect) {
                if (uu.getUserBId().equals(getSessionUserId())) {
                    longs.add(uu.getUserAId());
                }
                if (uu.getUserAId().equals(getSessionUserId())) {
                    longs.add(uu.getUserBId());
                }
            }
        }
        List<User> allFriends1 = userService.getAllFriends(getSessionUserId());
        List<Long> collect = new ArrayList<>();
        if (Objects.nonNull(allFriends1)) {
            collect = allFriends1.stream().map(User::getId).collect(Collectors.toList());
        }
        collect.addAll(longs);
        List<Long> collect1 = collect.stream().distinct().collect(Collectors.toList());
        List<User> allFriends = userService.getAllFriendsByIds(collect1);
        Set<Long> sessionUserIdSet = new HashSet<>();
        Object o = getRedisValueOperation().get(Constant.SESSION_USER);
        if (o!=null){
            sessionUserIdSet = JSON.parseArray(JSON.toJSONString(o), User.class).stream()
                    .map(User::getId).collect(Collectors.toSet());
        }
        List<UserChartVo> userChartVos = new ArrayList<>();
        Set<Long> set = new HashSet<>();
        if (Objects.nonNull(allFriends)) {
            for (User allFriend : allFriends) {
                UserChartVo userChartVo = new UserChartVo();
                BeanUtils.copyProperties(allFriend, userChartVo, "password");
                userChartVo.setChartStatus(1);
                if (Objects.nonNull(sessionUserIdSet) && sessionUserIdSet.contains(userChartVo.getId()) && !set.contains(userChartVo.getId())) {
                    userChartVo.setChartStatus(0);
                }
                userChartVos.add(userChartVo);
            }
        }
        if (StringUtils.isNoneBlank(nickname)) {
            userChartVos = userChartVos.stream().filter(x -> x.getNickname().equals(nickname)).collect(Collectors.toList());
        }
        loggingService.save(getLogging("查看了好友信息",StringsUtils.paramFormat("nickname",nickname),urlPrefix+"/sessionUsers"));
        return success(userChartVos);
    }

    /**
     * 用户发送数据
     * @param id
     * @param msg
     */
    @ResponseBody
    @Transactional
    @MethodUrl(url = "/sendTo",name = "用户发送数据")
    @PostMapping("/sendTo")
    public Result sendTo(@RequestParam("id") Long id, @RequestParam("msg") String msg) {
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setCreateTime(new Date());
        chatRecord.setUserAId(getSessionUser().getId());
        chatRecord.setUserBId(id);
        chatRecord.setContent(msg);
        chatRecord.setCreateTimeValue(DateUtils.showDate(new Date()));
        chatRecord.setAFileUrl(getSessionUser().getFileUrl());
        User bUser = userService.getById(id);
        chatRecord.setBFileUrl(bUser.getFileUrl());
        chatRecord.setANickname(getSessionUser().getNickname());
        chatRecord.setBNickname(bUser.getNickname());
        chatRecord.setUserAName(getSessionUserName());
        chatRecord.setUserBName(bUser.getUsername());
        boolean flag = false;
        addChatRecords ="chatRecords"+getSessionUserId()+id;
        List<Object> dBChatRecords = new ArrayList<>();
        if (getRedisValueOperation().get(addChatRecords)!=null){
            dBChatRecords = JSON.parseArray(getRedisValueOperation().get(addChatRecords).toString(), Object.class);
        }
        List<ChatRecord> chatRecords = new ArrayList<>();
        if (Objects.isNull(dBChatRecords) || !redisTemplate.hasKey(addChatRecords)) {
            getRedisValueOperation().set(addChatRecords, JSON.toJSONString(chatRecords), 24, TimeUnit.DAYS);
       }
//        else if (getExpireTime(addChatRecords) < 1) {
//            logger.info("==========>正在存入数据" + getRedisValueOperation().getOperations().getExpire(addChatRecords));
           /* boolean rs = true;
            if (!chatRecords.isEmpty()) {
                flag = true;
                rs = chatRecordService.saveBatch(chatRecords);
            }
            if (!rs) {
                return error("信息保存异常");
            }
            redisTemplate.delete(addChatRecords);*/
 //           getRedisValueOperation().set(addChatRecords, JSON.toJSONString(chatRecords), 24, TimeUnit.DAYS);
//        }
        else {
            for (Object dBChatRecord : dBChatRecords) {
                chatRecords.add(JSONObject.parseObject(dBChatRecord.toString(),ChatRecord.class));
            }
        }
        chatRecords.add(chatRecord);
        getRedisValueOperation().set(addChatRecords, JSON.toJSONString(chatRecords));
        loggingService.save(getLogging("用户发送送数据",StringsUtils.paramFormat("id",id)+","+StringsUtils.paramFormat("msg",msg),urlPrefix+"/sendTo"));
        return dataResult(true, "发送失败", "发送成功");
    }

    /**
     * user数据保存方法
     * @param user
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/doSaveUser")
    public Result saveUser(User user,HttpServletRequest request) {
        if (Objects.nonNull(user.getId())){
            setBaseField(user);
        }
        //创建保存数据结果
        boolean result = false;
        String logIp = HttpUtils.getIpAddress(request);
        //密码加密
        if (!StringUtils.isBlank(user.getPassword())) {
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
        }
        if (Objects.nonNull(user.getId())) {
            result = userService.saveOrUpdate(user);
            if (result){
                loggingService.save(getLogging("用户修改了信息，并且成功了",JSONObject.toJSONString(user),urlPrefix+"/doSaveUser"));
                if (Objects.isNull(user.getIsUpdatePassword())){
                    //修改密码，重新登录
                    removeSessionUser();
                }else {
                    //修改信息
                    User dbUser = userService.getById(user.getId());
                    setSession(Constant.SESSION_USER, dbUser);
                    for (int i = 0; i < sessionUsers.size(); i++) {
                        if (sessionUsers.get(i).getId().equals(getSessionUserId())){
                                sessionUsers.remove(sessionUsers.get(i));
                        }
                    }
                    sessionUsers.add(getSessionUser());
                    getRedisValueOperation().set(Constant.SESSION_USER, sessionUsers, 24, TimeUnit.HOURS);
                }
                return success("用户修改信息成功");
            }
            loggingService.save(getLogging("用户修改了信息，但失败了",JSONObject.toJSONString(user),urlPrefix+"/doSaveUser"));
        }
        //注册
        if (Objects.isNull(user.getId())) {
            Result checkRegistResult = userServiceControllerDetailService.checkRegistData(user);
            if (!checkRegistResult.getRs()) {
                return checkRegistResult;
            }
            user.setStatus(Constant.USER_TYPE_VAILD_STATUS);
            //发送邮件
            // userServiceControllerDetailService.getEmailAddress(user,appUrl);
            user.setFileUrl(Constant.FILE_DEFAULT_NAME);
            result = userService.saveOrUpdate(user);
            if (result){
                Logging logging = getLogging("用户注册了信息，并且成功了", JSONObject.toJSONString(user), urlPrefix + "/doSaveUser");
                noLoginSaveLog(logging,logIp,user.getUsername());
                return success("用户注册成功");
            }
        }
        loggingService.save(getLogging("用户注册了信息，但失败了",JSONObject.toJSONString(user),urlPrefix+"/doSaveUser"));
        //结果处理
        return error(Constant.ERROR_CODE);
    }

    /**
     * 邮政验证
     * @param code
     * @returnc
     */
    @Transactional
    @RequestMapping("/checkCode")
    public String checkCode(String code) {
        User user = new User();
        user.setStatusCode(code);
        if (!Constant.RESULT_ONE_SUCCESS_SQL_RESULT.equals(userService.getUserCount(user, Constant.USER_TYPE_UNIQUE_STATUS_CODE))) {
            return Constant.REDIRECT + urlPrefix + "regist";
        }
        User dbUser = userService.getUser(user, Constant.USER_TYPE_UNIQUE_STATUS_CODE);
        if (Objects.nonNull(dbUser)) {
            //激活成功，让之前生成code失效
            String md5Code = MD5Utils.getMD5Str(code);
            dbUser.setStatusCode(md5Code);
            User updateUser = new User();
            updateUser.setId(dbUser.getId());
            userService.updateUser(dbUser, updateUser, Constant.USER_TYPE_UP_VAILD_STATUS);
        }
        loggingService.save(getLogging("用户使用了验证码",StringsUtils.paramFormat("code",code),urlPrefix+"/checkCode"));
        return Constant.REDIRECT + urlPrefix + "login";
    }

    /**
     * 登录验证
     * @param username
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping("/doLogin")
    public Result loginUser(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
        String logIp = HttpUtils.getIpAddress(request);
        String param = StringsUtils.paramFormat(new Object[]{"username",username,"password",password});
        if (Objects.nonNull(IP_SESSION_MAP.get(username+logIp))){
            RequestCount requestCount = IP_SESSION_MAP.get(username+logIp);
            if (requestCount.getCount()==10){
                if (!DateUtils.dateCompare(requestCount.getLimitTime(),new Date(),24*60*60*1000L)){
                    Logging logging = getLogging("用户登录失败，密码输入错误次数过多", param, urlPrefix + "/doLogin");
                    noLoginSaveLog(logging,logIp,username);
                    return error("密码输入错误次数过多,请在1天后后重试");
                }
                IP_SESSION_MAP.remove(username+logIp);
            }
        }
        User user = new User(username,MD5Utils.getMD5Str(password));
        logger.info(username + "======>登录中");
        User dbUser = userService.getUser(user, Constant.USER_TYPE_LOGIN);
        if (Objects.nonNull(dbUser)) {
            user.setStatus(dbUser.getStatus());
            if (!user.getPassword().equals(dbUser.getPassword())) {
                setCount(username+logIp);
                Logging logging = getLogging("用户登录失败，密码输入错误", param, urlPrefix + "/doLogin");
                noLoginSaveLog(logging,logIp,username);
                return error(Constant.CHINESE_PASSWORD_ERROR_MESSAGE);
            }
            //头像为空则为默认值
            if (StringUtils.isEmpty(dbUser.getFileUrl())) {
                dbUser.setFileUrl(Constant.FILE_DEFAULT_NAME);
            }
            //设置当前登录账号的状态
            Result checkStatusResult = userServiceControllerDetailService.checkStatusData(dbUser);
            if (!checkStatusResult.getRs()) {
                Logging logging = getLogging("用户登录失败，用户状态不允许登录", param, urlPrefix + "/doLogin");
                noLoginSaveLog(logging,logIp,username);
                return checkStatusResult;
            }
            int authCode = getAuthCode(dbUser, request);
            int flag = 0;
            if (authCode != 200) {
                if (authCode == 206) {
                    /*return error("该用户已登录");*/
                } else {
                    if (Objects.nonNull(getSessionValue("flag"))){
                        if (Integer.parseInt(getSessionValue("flag").toString()) == 4){
                            dbUser.setStatus(Constant.USER_TYPE_FROZEN_STATUS);
                            userService.saveOrUpdate(dbUser);
                            Logging logging = getLogging("用户登录失败，操作次数过多，该用户已被冻结", param, urlPrefix + "/doLogin");
                            noLoginSaveLog(logging,logIp,username);
                            return error("操作次数过多，该用户已被冻结");
                        }
                    }else {
                        setSession("flag",flag++);
                    }
                   /* try {
                        Thread.sleep(40 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            }
            authMapPut(dbUser, request);
           /* if (sessionUsers.contains(dbUser)){
                return error("用户已存在");
            }*/
            boolean rs = fileService.writeJoyceConfig(dbUser.getId().toString(), null);
            if (!rs) {
                Logging logging = getLogging("用户登录失败，初始化用户文件失败", param, urlPrefix + "/doLogin");
                noLoginSaveLog(logging,logIp,username);
                return error("初始化用户文件失败");
            }
            //设置当前登录人
            setSession(Constant.SESSION_USER, dbUser);
            //设置当前在线集合
            Object o = getRedisValueOperation().get(Constant.SESSION_USER);
            if (o!=null){
                sessionUsers =  JSON.parseArray(JSON.toJSONString(o),User.class);
                if (sessionUsers.isEmpty()){
                    sessionUsers.add(getSessionUser());
                }else {
                    int sflag = 0;
                    for (User sessionUser : sessionUsers) {
                        if (sessionUser.equals(getSessionUser())){
                            sflag = 1;
                            break;
                        }
                    }
                    if (sflag == 0){
                        sessionUsers.add(getSessionUser());
                    }
                }
            }

            //检测是否存在当前登录人的相关配置
            if (!user.getStatus().equals(Constant.USER_TYPE_INVAILD_STATUS)) {
                Setting currentSetting = userServiceControllerDetailService.checkData(getSessionUser().getId());
                if (Objects.nonNull(currentSetting)) {
                    logger.info(username + "======>设置装配中");
                    Map<String, List<PageComponent>> settingUeFile = fileService.readJoyceConfig(dbUser.getId().toString());
                    Map<String, Object> map = currentSetting.getMap();
                   // System.out.println("-----<<.>>>"+settingUeFile.toString());
                    map.put(Constant.SETTING_UE_FILE,settingUeFile);
                    currentSetting.setMap(map);
                    setSession(getSessionUserId() + Constant.CURRENT_SETTING, currentSetting);
                }
            }
            //  清空次数
            if (Objects.nonNull(IP_SESSION_MAP.get(username+logIp))){
                RequestCount requestCount = IP_SESSION_MAP.get(username+logIp);
                if (requestCount.getCount()<10){
                    IP_SESSION_MAP.remove(username+logIp);
                }
            }
            logger.info(username + "======>登录成功");
            USER_SESSION_MAP.put(getSessionUserId(), getSession().getId());
            setSession(getSessionUserId()+"ip",logIp);
            setSession("index", 0);
            getRedisValueOperation().set(Constant.SESSION_USER, sessionUsers, 24, TimeUnit.HOURS);
            String msg = Constant.RESULT_SUCCESS_MSG;
            int code = Constant.SUCCESS_CODE;
            if (flag != 0) {
                msg = "此号在别处登录";
                code = 206;
                loggingService.save(getLogging("用户登录成功，此号在别处登录",param,urlPrefix+"/doLogin"));
            }
            loggingService.save(getLogging("用户登录成功", param, urlPrefix + "/doLogin"));
            logger.info("-------------->登录sessionId：{}", getSession().getId());
            return success(code, msg, user.getStatus());
        }
        Logging logging = getLogging("用户登录失败，" + Constant.CHINESE_SELECT_BLANK_USERNAME_MESSAGE, param, urlPrefix + "/doLogin");
        noLoginSaveLog(logging,logIp,username);
        return error(Constant.ERROR_CODE, Constant.CHINESE_SELECT_BLANK_USERNAME_MESSAGE);
    }

    /**
     * 获取所有用户数据
      * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/userListData")
    public PageVo getUsers(User user) {
        loggingService.save(getLogging("获取所有用户数据",JSONObject.toJSONString(user),urlPrefix+"/userListData"));
        return userService.getPage(user);
    }

    /**
     * 删除某个user
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/deleteUser")
    public Result deleteUser(@RequestParam String ids) {
        String param = StringsUtils.paramFormat("ids",ids);
        if (StringUtils.isBlank(ids)) {
            return error(Constant.NULL_CODE);
        }
        List<String> list = StringsUtils.strToList(ids);
        if (StringsUtils.listIsContainsStr(getSessionUser().getId().toString(), list)) {
            loggingService.save(getLogging("删除user失败，正在使用，无法操作",param,urlPrefix+"/deleteUser"));
            return error(getSessionUser().getUsername() + "正在使用，无法操作");
        }
        boolean del = userService.removeByIds(list);
        loggingService.save(getLogging("删除user"+(del ?"成功":"失败"),param,urlPrefix+"/deleteUser"));
        return dataResult(del, Constant.ERROR_CODE);
    }

    /**
     * 冻结某个user
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/freezeUser")
    public Result freezeUser(@RequestParam String ids) {
        String param = StringsUtils.paramFormat("ids",ids);
        if (StringUtils.isBlank(ids)) {
            return error(Constant.NULL_CODE);
        }
        if (ids.equals(getSessionUser().getId().toString())) {
            loggingService.save(getLogging("冻结user失败,正在使用，无法操作",param,urlPrefix+"/freezeUser"));
            return error(getSessionUser().getUsername() + "正在使用，无法操作");
        }
        User dbUser = userService.getById(Long.valueOf(ids));
        if (Objects.isNull(dbUser)) {
            loggingService.save(getLogging("冻结user失败,用户不存在",param,urlPrefix+"/freezeUser"));
            return error(Constant.NULL_CODE);
        }
        dbUser.setStatus(Constant.USER_TYPE_FROZEN_STATUS);
        boolean update = userService.updateById(dbUser);
        loggingService.save(getLogging("冻结user"+(update?"成功":"失败"),param,urlPrefix+"/freezeUser"));
        return dataResult(update, Constant.ERROR_CODE);
    }

    /**
     * 恢复某个冻结的user
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/recoverUser")
    public Result recoverUser(@RequestParam String ids) {
        String param = StringsUtils.paramFormat("ids",ids);
        if (StringUtils.isBlank(ids)) {
            return error(Constant.NULL_CODE);
        }
        User dbUser = userService.getById(Long.valueOf(ids));
        if (Objects.isNull(dbUser)) {
            loggingService.save(getLogging("恢复用户失败，用户不存在",param,urlPrefix+"/recoverUser"));
            return error(Constant.NULL_CODE);
        }
        dbUser.setStatus(Constant.USER_TYPE_VAILD_STATUS);
        boolean update = userService.updateById(dbUser);
        loggingService.save(getLogging("恢复用户"+(update?"成功":"失败"),param,urlPrefix+"/recoverUser"));
        return dataResult(update, Constant.ERROR_CODE);
    }

    /**
     * 编辑user的数据
      * @param id
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/doQueryUser")
    public Result updateUser(@RequestParam Long id) {
        String param = StringsUtils.paramFormat("id",id);
        User dbUser = userService.getById(id);
        Result result = userServiceControllerDetailService.checkStatusData(dbUser);
        if (!result.getRs()) {
            loggingService.save(getLogging("用户准备编辑信息失败，检验未通过",param,urlPrefix+"/doQueryUser"));
            return result;
        }
        loggingService.save(getLogging("用户准备编辑信息，检验通过",param,urlPrefix+"/doQueryUser"));
        return success(dbUser);
    }

    /**
     * 忘记密码
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/getEmailCode")
    public Result getEmailCode(@RequestParam String email) {
        User user = new User();
        user.setEmail(email);
        User dbUser = userService.getUser(user, "");
        if (Objects.isNull(dbUser)) {
            return error(Constant.NULL_CODE);
        }
        String mailCode = EmailUtils.SendMailCode(user.getEmail(), 6);
        cache = RedisUtils.getInstance();
       // loggingService.save(getLogging("用户忘记密码，获取验证码",StringsUtils.paramFormat("email",email),urlPrefix+"/getEmailCode"));
        return dataResult(RedisUtils.setVerifyCode(cache, email, 60, mailCode), "验证码不可重复发送", "验证码已发送，请查看");
    }

    /**
     * 检查验证码修改密码验证
      * @param emailCode
     * @param email
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/forgetPassword")
    public Result checkUpdateCode(@RequestParam String emailCode, @RequestParam String email) {
        String param  = StringsUtils.paramFormat("emailCode",emailCode)+","+StringsUtils.paramFormat("email",email);
        User user = new User();
        user.setEmail(email);
        User dbUser = userService.getUser(user, "");
        if (Objects.isNull(dbUser)) {
            return error("该邮件未注册");
        }
        int result = RedisUtils.compareCode(cache, emailCode, email, 3, 24 * 60 * 60);
        if (result == -1) {
            return error("请输入正确的验证码");
        }
        loggingService.save(getLogging("用户校验修改密码验证码"+(result==1?"成功":"失败"),param,urlPrefix+"/forgetPassword"));
        return dataResult(result, "验证码已失效,请重新获取", "校验成功");
    }

    /**
     * 输入新密码
      * @param newPassword
     * @param password
     * @param id
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/updatePassword")
    public Result updatePassword(@RequestParam("password") String password, @RequestParam("newPassword") String newPassword, @RequestParam("userId") Long id) {
        /*  User user = (User) request.getSession().getAttribute(Constant.SESSION_USER);*/
        String param  = StringsUtils.paramFormat("password",password)+","+StringsUtils.paramFormat("newPassword",newPassword)+","+StringsUtils.paramFormat("userId",id);
        if (StringUtils.isBlank(password.trim())) {
            return error(Constant.CHINESE_BLANK_MESSAGE);
        }
        if (StringUtils.isBlank(newPassword.trim())) {
            return error(Constant.CHINESE_BLANK_MESSAGE);
        }
        User user = userService.getById(id);
        if (!(user.getPassword().equals(MD5Utils.getMD5Str(password)))) {
            loggingService.save(getLogging("用户校验修改密码失败，密码不正确",param,urlPrefix+"/updatePassword"));
            return error(Constant.ERROR_FILL_ERROR_CODE);
        }
        user.setPassword(MD5Utils.getMD5Str(newPassword));
        boolean updateById = userService.updateById(user);
        if (updateById) {
            userService.updateById(user);
            sessionUsersRmCurrentUser();
            getRedisValueOperation().set(Constant.SESSION_USER, sessionUsers, 24, TimeUnit.HOURS);
            removeSessionUser();
            loggingService.save(getLogging("用户校验修改密码成功",param,urlPrefix+"/updatePassword"));
            return success();
        }
        loggingService.save(getLogging("用户校验修改密码失败",param,urlPrefix+"/updatePassword"));
        return error(Constant.ERROR_CODE);
    }

    /**
     * 文件上传
      * @param file
     * @return
     */
    @ResponseBody
    @Transactional
    @PostMapping("/upload")
    public Result uploadImg(@RequestParam("file") MultipartFile file) {
        String param = StringsUtils.paramFormat("file",file.getName());
        String filePath = null;
        try {
            filePath = fileService.uploadImg(file).get("v");
        } catch (Exception e) {
            loggingService.save(getLogging("用户上传文件失败",param,urlPrefix+"/upload"));
            return error("上传失败");
        }
        loggingService.save(getLogging("用户上传文件成功",param,urlPrefix+"/upload"));
        return success("上传成功", filePath);
    }

    /**
     * 退出登录
     */
    @ResponseBody
    @GetMapping("/toRemoveUser")
    public Result toRemoveUser() {
        //移除会话数据存在的用户
        try {
            if (Objects.isNull(getSessionUser())){
                removeCurrentSetting();
                removeSessionUser();
                sessionUsersRmCurrentUser();
                authMap.remove(getSessionUserId());
            }
            getRedisValueOperation().set(Constant.SESSION_USER, sessionUsers, 24, TimeUnit.HOURS);
        } catch (Exception e) {
            loggingService.save(getLogging("退出登录失败","",urlPrefix+"/toRemoveUser"));
            e.printStackTrace();
            return error("退出登录失败");
        }
        loggingService.save(getLogging("退出登录成功","",urlPrefix+"/toRemoveUser"));
        return success();
    }

    /**
     * 根据用户名查找用户
     */
    @ResponseBody
    @GetMapping("/searchUserByUsername")
    public Result searchUserByUsername(@RequestParam String username) {
        String param = StringsUtils.paramFormat("username",username);
        User user = new User();
        user.setUsername(username);
        User dbUser = userService.getUser(user, "");
        if (Objects.isNull(dbUser)) {
            loggingService.save(getLogging("查找用户失败，"+Constant.CHINESE_SELECT_BLANK_USERNAME_MESSAGE,param,urlPrefix+"/searchUserByUsername"));
            return error(Constant.CHINESE_SELECT_BLANK_USERNAME_MESSAGE);
        }
        dbUser.setPassword("000000");
        loggingService.save(getLogging("查找用户成功，"+Constant.CHINESE_SELECT_BLANK_USERNAME_MESSAGE,param,urlPrefix+"/searchUserByUsername"));
        return success(dbUser);
    }


    /**
     * 检测用户是否重复登录
     */
    @ResponseBody
    @GetMapping("/checkUser")
    public Result checkUser() {
        try {
            if (Objects.isNull(getSession())||Objects.isNull(getSessionUserId())||Objects.isNull(USER_SESSION_MAP.get(getSessionUserId()))){
                return error();
            }
            String targetSessionId = USER_SESSION_MAP.get(getSessionUserId());
     //       logger.info("------------------------------------->{}::{}", targSessionId, getSession().getId());
            if (!targetSessionId.equals(getSession().getId())) {
              //  loggingService.save(getLogging("此号在别处登录",StringsUtils.paramFormat("id",getSessionUserId())));
                removeSessionUser();
                return error("此号在别处登录，您已下线");
            }
        } catch (Exception e) {
            return error();
        }
        return success();
    }
    @ResponseBody
    @GetMapping("/getLogging")
    public List<Logging> getLogging(){
        return loggingService.getList(new Logging());
    }

    private void setCount(String key){
        if (Objects.nonNull(IP_SESSION_MAP.get(key))){
        RequestCount requestCount = IP_SESSION_MAP.get(key);
        if (requestCount.getCount()<10){
            if (!DateUtils.dateCompare(requestCount.getLimitTime(),new Date(),24*60*60*1000L)){
                requestCount.setCount(requestCount.getCount()+1);
                requestCount.setLimitTime(new Date());
            }else {
                requestCount.setCount(1);
                requestCount.setLimitTime(new Date());
            }
            IP_SESSION_MAP.put(key,requestCount);
        }
    }else {
        RequestCount requestCount = new RequestCount(1,new Date());
        IP_SESSION_MAP.put(key,requestCount);
    }
    }

    /**
     * 未登录日志保存
     * @param logging
     * @param logIp
     * @param username
     */
    private void noLoginSaveLog(Logging logging,String logIp,String username){
        logging.setLoginIp(logIp);
        logging.setUsername(username);
        loggingService.save(logging);
    }
}

