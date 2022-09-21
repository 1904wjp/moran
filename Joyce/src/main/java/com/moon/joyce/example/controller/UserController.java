package com.moon.joyce.example.controller;


import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.*;
import com.moon.joyce.example.entity.doma.ChatRecord;
import com.moon.joyce.example.entity.doma.UU;
import com.moon.joyce.example.entity.doma.User;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.entity.vo.UserChartVo;
import com.moon.joyce.example.functionality.entity.doma.RequestCount;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.functionality.entity.doma.Setting;
import com.moon.joyce.example.functionality.service.FileService;
import com.moon.joyce.example.service.ChatRecordService;
import com.moon.joyce.example.service.UserService;
import com.moon.joyce.example.service.serviceControllerDetails.UserControllerDetailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
@RequestMapping("/example/user")
public class UserController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
/*********************************************************************************************************************************************/
    /**************全局变量***********************/
    private static HashMap<Long, String> USER_SESSION_MAP = new HashMap<>();
    private static HashMap<String, RequestCount> IP_SESSION_MAP = new HashMap<>();
    @Autowired
    private UserService userService;
    @Autowired
    private ChatRecordService chatRecordService;
    //redis缓存
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
     *
     * @return
     */
    @GetMapping("/regist")
    public String registPage() {
        return pagePrefix + "registPage";
    }

    /**
     * 注册结果页面
     *
     * @return
     */
    @GetMapping("/statusResult")
    public String statusResultPage() {
        return pagePrefix + "statusResultPage";
    }

    /**
     * 登录页面
     *
     * @return
     */
    @GetMapping("/login")
    public String loginPage() {
        return pagePrefix + "loginPage";
    }

    /**
     * 查看页面
     *
     * @param id
     * @param map
     * @return
     */
    @GetMapping("/queryUser/{id}")
    public String queryUserPage(@PathVariable("id") Long id, ModelMap map) {
        User dbUser = userService.getById(id);
        map.addAttribute("user", dbUser);
        return pagePrefix + "queryUserPage";
    }

    /**
     * 编辑页面
     *
     * @param id
     * @param map
     * @return
     */
    @Transactional
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
     *
     * @return
     */
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
     *
     * @param nickname
     * @return
     */
    @ResponseBody
    @GetMapping("/sessionUsers")
    public Result getSessionUsers(@RequestParam String nickname) {
        String cacheFriends = UUController.uniqueListSum;
        List<UU> uus = (List<UU>) redisTemplate.opsForValue().get(cacheFriends);
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
        Set<Long> sessionUserIdSet = Arrays.stream((User[]) Objects.requireNonNull(redisTemplate.opsForValue().get(Constant.SESSION_USER))).map(User::getId).collect(Collectors.toSet());
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
        return success(userChartVos);
    }

    /**
     * 用户发送数据
     *
     * @param id
     * @param msg
     */
    @ResponseBody
    @Transactional
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
        Object obj = getRedisValueOperation().get(addChatRecords);
        List<ChatRecord> chatRecords = new ArrayList<>();
        if (Objects.isNull(obj) || !redisTemplate.hasKey(addChatRecords)) {
            getRedisValueOperation().set(addChatRecords, chatRecords, 24, TimeUnit.DAYS);
        } else if (getExpireTime(addChatRecords) < 1) {
            logger.info("==========>正在存入数据" + redisTemplate.opsForValue().getOperations().getExpire(addChatRecords));
            boolean rs = true;
            if (!chatRecords.isEmpty()) {
                rs = chatRecordService.saveBatch(chatRecords);
            }
            if (!rs) {
                return error("信息保存异常");
            }
            redisTemplate.delete(addChatRecords);
            getRedisValueOperation().set(addChatRecords, chatRecords, 24, TimeUnit.DAYS);
        } else {
            chatRecords = (List<ChatRecord>) obj;
        }
        chatRecords.add(chatRecord);
        redisTemplate.opsForValue().set(addChatRecords, chatRecords);
        return dataResult(true, "发送失败", "发送成功");
    }

    /**
     * user数据保存方法
     *
     * @param user
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/doSaveUser")
    public Result saveUser(User user) {
        setBaseField(user);
        //创建保存数据结果
        boolean result = false;
        //密码加密
        if (!StringUtils.isBlank(user.getPassword())) {
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
        }
        if (Objects.nonNull(user.getId())) {
            result = userService.saveOrUpdate(user);
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
        }
        //结果处理
        if (result) {
            if (Objects.nonNull(getSessionUser())) {
                removeSessionUser();
            }
            setSession(Constant.SESSION_USER, user);
            return success();
        }
        return error(Constant.ERROR_CODE, false);
    }

    /**
     * 邮政验证
     *
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
        return Constant.REDIRECT + urlPrefix + "login";
    }

    /**
     * 登录验证
     *
     * @param username
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping("/doLogin")
    public Result loginUser(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
        String logIp = HttpUtils.getIpAddress(request);
        if (Objects.nonNull(IP_SESSION_MAP.get(username+logIp))){
            RequestCount requestCount = IP_SESSION_MAP.get(username+logIp);
            if (requestCount.getCount()==10){
                if (!DateUtils.dateCompare(requestCount.getLimitTime(),new Date(),24*60*60*1000L)){
                    return error("密码输入错误次数过多,请在1天后后重试");
                }
                IP_SESSION_MAP.remove(username+logIp);
            }
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Utils.getMD5Str(password));
        logger.info(username + "======>登录中");
        User dbUser = userService.getUser(user, Constant.USER_TYPE_LOGIN);
        if (Objects.nonNull(dbUser)) {
            user.setStatus(dbUser.getStatus());
            if (!user.getPassword().equals(dbUser.getPassword())) {
                setCount(username+logIp);
                return error(Constant.CHINESE_PASSWORD_ERROR_MESSAGE);
            }
            //头像为空则为默认值
            if (StringUtils.isEmpty(dbUser.getFileUrl())) {
                dbUser.setFileUrl(Constant.FILE_DEFAULT_NAME);
            }
            //设置当前登录账号的状态
            Result checkStatusResult = userServiceControllerDetailService.checkStatusData(dbUser);
            if (!checkStatusResult.getRs()) {
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
            boolean rs = fileService.writeJoyceConfig(user.getUsername(), null, false);
            if (!rs) {
                return error("初始化用户文件失败");
            }
            //设置当前登录人
            setSession(Constant.SESSION_USER, dbUser);
            //设置当前在线集合
            sessionUsers.add(dbUser);
            //检测是否存在当前登录人的相关配置
            if (!user.getStatus().equals(Constant.USER_TYPE_INVAILD_STATUS)) {
                Setting currentSetting = userServiceControllerDetailService.checkData(getSessionUser().getId());
                if (Objects.nonNull(currentSetting)) {
                    logger.info(username + "======>设置装配中");
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
            setSession("index", 0);
            redisTemplate.opsForValue().set(Constant.SESSION_USER, sessionUsers, 24, TimeUnit.HOURS);
            String msg = Constant.RESULT_SUCCESS_MSG;
            int code = Constant.SUCCESS_CODE;
            if (flag != 0) {
                msg = "此号在别处登录";
                code = 206;
            }
            logger.info("-------------->登录sessionId：{}", getSession().getId());
            return success(code, msg, user.getStatus());
        }
        return error(Constant.ERROR_CODE, Constant.CHINESE_SELECT_BLANK_USERNAME_MESSAGE);
    }

    /**
     * 获取所有用户数据
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/userListData")
    public PageVo getUsers(User user) {
        return userService.getPage(user);
    }

    /**
     * 删除某个user
     *
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/deleteUser")
    public Result deleteUser(@RequestParam String ids) {
        if (StringUtils.isBlank(ids)) {
            return error(Constant.NULL_CODE);
        }
        List<String> list = StringsUtils.strToList(ids);
        if (StringsUtils.listIsContainsStr(getSessionUser().getId().toString(), list)) {
            return error(getSessionUser().getUsername() + "正在使用，无法操作");
        }
        boolean del = userService.removeByIds(list);
        return dataResult(del, Constant.ERROR_CODE);
    }

    /**
     * 冻结某个user
     *
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/freezeUser")
    public Result freezeUser(@RequestParam String ids) {
        if (StringUtils.isBlank(ids)) {
            return error(Constant.NULL_CODE);
        }
        if (ids.equals(getSessionUser().getId().toString())) {
            return error(getSessionUser().getUsername() + "正在使用，无法操作");
        }
        User dbUser = userService.getById(Long.valueOf(ids));
        if (Objects.isNull(dbUser)) {
            return error(Constant.NULL_CODE);
        }
        dbUser.setStatus(Constant.USER_TYPE_FROZEN_STATUS);
        boolean update = userService.updateById(dbUser);
        return dataResult(update, Constant.ERROR_CODE);
    }

    /**
     * 恢复某个冻结的user
     *
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/recoverUser")
    public Result recoverUser(@RequestParam String ids) {
        if (StringUtils.isBlank(ids)) {
            return error(Constant.NULL_CODE);
        }
        User dbUser = userService.getById(Long.valueOf(ids));
        if (Objects.isNull(dbUser)) {
            return error(Constant.NULL_CODE);
        }
        dbUser.setStatus(Constant.USER_TYPE_VAILD_STATUS);
        boolean update = userService.updateById(dbUser);
        return dataResult(update, Constant.ERROR_CODE);
    }

    /**
     * 编辑user的数据
     *
     * @param id
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/doQueryUser")
    public Result updateUser(@RequestParam Long id) {
        User dbUser = userService.getById(id);
        Result result = userServiceControllerDetailService.checkStatusData(dbUser);
        if (!result.getRs()) {
            return result;
        }
        return success(dbUser);
    }

    /**
     * 忘记密码
     *
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
        return dataResult(RedisUtils.setVerifyCode(cache, email, 60, mailCode), "验证码不可重复发送", "验证码已发送，请查看");
    }

    /**
     * 检查验证码修改密码验证
     *
     * @param emailCode
     * @param email
     * @param newPassword
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/forgetPassword")
    public Result checkUpdateCode(@RequestParam String emailCode, @RequestParam String email, @RequestParam String newPassword) {
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
        return dataResult(result, "验证码已失效,请重新获取", "校验成功");
    }

    /**
     * 输入新密码
     *
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
        if (StringUtils.isBlank(password.trim())) {
            return error(Constant.CHINESE_BLANK_MESSAGE);
        }
        if (StringUtils.isBlank(newPassword.trim())) {
            return error(Constant.CHINESE_BLANK_MESSAGE);
        }
        User user = userService.getById(id);
        if (!(user.getPassword().equals(MD5Utils.getMD5Str(password)))) {
            return error(Constant.ERROR_FILL_ERROR_CODE);
        }
        user.setPassword(MD5Utils.getMD5Str(newPassword));
        boolean updateById = userService.updateById(user);
        if (updateById) {
            userService.updateById(user);
            sessionUsers.remove(getSessionUser());
            redisTemplate.opsForValue().set(Constant.SESSION_USER, sessionUsers, 24, TimeUnit.HOURS);
            removeSessionUser();
            return success();
        }
        return error(Constant.ERROR_CODE);
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @ResponseBody
    @Transactional
    @PostMapping("/upload")
    public Result uploadImg(@RequestParam("file") MultipartFile file) {
        String filePath = null;
        try {
            filePath = fileService.uploadImg(file);
        } catch (Exception e) {
            return error("上传失败");
        }
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
            removeCurrentSetting();
            removeSessionUser();
            sessionUsers.remove(getSessionUser());
            authMap.remove(getSessionUserId());
            redisTemplate.opsForValue().set(Constant.SESSION_USER, sessionUsers, 24, TimeUnit.HOURS);
        } catch (Exception e) {
            return error();
        }
        return success();
    }

    /**
     * 根据用户名查找用户
     */
    @ResponseBody
    @GetMapping("/searchUserByUsername")
    public Result searchUserByUsername(@RequestParam String username) {
        User user = new User();
        user.setUsername(username);
        User dbUser = userService.getUser(user, "");
        if (Objects.isNull(dbUser)) {
            return error(Constant.CHINESE_SELECT_BLANK_USERNAME_MESSAGE);
        }
        dbUser.setPassword("000000");
        return success(dbUser);
    }


    /**
     * 根据用户名查找用户
     */
    @ResponseBody
    @GetMapping("/checkUser")
    public Result checkUser() {
        try {
            if (Objects.isNull(getSession())||Objects.isNull(getSessionUserId())||Objects.isNull(USER_SESSION_MAP.get(getSessionUserId()))){
                return error();
            }
            String targSessionId = USER_SESSION_MAP.get(getSessionUserId());
            logger.info("------------------------------------->{}::{}", targSessionId, getSession().getId());
            if (!targSessionId.equals(getSession().getId())) {
                removeSessionUser();
                return error("此号在别处登录，您已下线");
            }
        } catch (Exception e) {
            return error();
        }
        return success();
    }

    private void setCount(String key){if (Objects.nonNull(IP_SESSION_MAP.get(key))){
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
}

