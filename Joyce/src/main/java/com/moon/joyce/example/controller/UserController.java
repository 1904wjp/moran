package com.moon.joyce.example.controller;



import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.*;
import com.moon.joyce.example.entity.ChatRecord;
import com.moon.joyce.example.entity.User;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.entity.vo.UserChartVo;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.functionality.entity.Setting;
import com.moon.joyce.example.functionality.entity.VerifyCode;
import com.moon.joyce.example.functionality.service.FileService;
import com.moon.joyce.example.service.ChatRecordService;
import com.moon.joyce.example.service.UserService;
import com.moon.joyce.example.service.serviceControllerDetails.UserServiceControllerDetailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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

    @Autowired
    private UserService userService;
    @Autowired
    private ChatRecordService chatRecordService;
    /**
     * 页面路径前缀
     */
    private final String  pagePrefix = "user/";
    private final String  commonPagePrefix = "common/error/";

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
    private UserServiceControllerDetailService userServiceControllerDetailService;


    /*****************************************************************************************************************************************************************/
    /***********页面映射****************/

    /**
     * bar页面
     */
    @GetMapping("/headerBar")
    public String getHeaderBarPage(){
        return "common/public/headerBar.html";
    }
    /**
     * user列表页面
     */
    @GetMapping("/userList")
    public String getUsersPage(){
        return pagePrefix+"userListPage";
    }

    /**
     * 注册页面
     * @return
     */
    @GetMapping("/regist")
    public String registPage(){
        return pagePrefix+"registPage";
    }

    /**
     * 注册结果页面
     * @return
     */
    @GetMapping("/statusResult")
    public String statusResultPage(){
        return pagePrefix+"statusResultPage";
    }

    /**
     * 登录页面
     * @return
     */
    @GetMapping("/login")
    public String loginPage(){
        return pagePrefix+"loginPage";
    }

    /**
     * 查看页面
     * @param id
     * @param map
     * @return
     */
    @GetMapping("/queryUser/{id}")
    public String queryUserPage(@PathVariable("id") Long id,ModelMap map){
        User dbUser = userService.getById(id);
        map.addAttribute("user",dbUser);
        return pagePrefix+"queryUserPage";
    }

    /**
     * 编辑页面
     * @param id
     * @param map
     * @return
     */
    @Transactional
    @GetMapping("/editUser/{id}")
    public String updateUserPage(@PathVariable Long id,ModelMap map){
    /* if (!id.equals( getSessionUser().getId())){
            return commonPagePrefix+"404";
        }*/
        User dbUser = userService.getById(id);
        if (Objects.nonNull(dbUser)){
            map.addAttribute("user",dbUser);
            return pagePrefix+"updateUserPage";
        }
        return pagePrefix+"userListPage";
    }

    /**
     * 聊天页面
     * @return
     */
    @RequestMapping("/websocket")
    public String indexPage(ModelMap map){
        User sessionUser = getSessionUser();
        List<User> userList = userService.getUserList(null);
        userList.remove(getSessionUser());
        map.put("users",userList);
        map.addAttribute("user",sessionUser);
        map.addAttribute("websocketUrlPrefix",appUrlValue+"/websocket");
        return pagePrefix+"websocketPage";
    }


    /**************************************************************************************************************************************************************************************************************/
    /**********逻辑判断**********/

    /**
     * 好友
     */
    @ResponseBody
    @GetMapping("/sessionUsers")
    public Result getSessionUsers(@RequestParam String nickname) {
        User user = new User();
        user.setNickname(nickname);
        List<User> userList = userService.getUserList(user);
        List<UserChartVo> userChartVos = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            boolean flag = false;
            for (int i1 = 0; i1 < sessionUsers.size(); i1++) {
                if (userList.get(i).getUsername().equals(sessionUsers.get(i1).getUsername())){
                    flag = true;
                }
                if (i1==sessionUsers.size()-1){
                    UserChartVo userChartVo = new UserChartVo();
                    userChartVo.setId(userList.get(i).getId());
                    userChartVo.setNickname(userList.get(i).getNickname());
                    userChartVo.setUsername(userList.get(i).getUsername());
                    userChartVo.setAddress(userList.get(i).getAddress());
                    userChartVo.setFileUrl(userList.get(i).getFileUrl());
                    userChartVo.setEmail(userList.get(i).getEmail());
                    if (flag){
                        userChartVo.setChartStatus(1);
                    }else {
                        userChartVo.setChartStatus(0);
                    }
                    userChartVos.add(userChartVo);
                }
            }
        }
        return ResultUtils.success(userChartVos);
    }

    /**
     * 用户发送数据
     * @param id
     * @param msg
     */
    @ResponseBody
    @Transactional
    @PostMapping("/sendTo")
    public Result sendTo(@RequestParam("id") Long id,@RequestParam("msg") String msg) {
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setCreateTime(new Date());
        chatRecord.setUserAId(getSessionUser().getId());
        chatRecord.setUserBId(id);
        chatRecord.setContent(msg);
        boolean save = chatRecordService.save(chatRecord);
        return ResultUtils.dataResult(save,"发送失败","发送成功");
    }


    /**
     * user数据保存方法
     * @param user
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/doSaveUser")
    public Result saveUser(User user){
        //创建保存数据结果
        boolean result = false;
        //密码加密
        if (!StringUtils.isBlank(user.getPassword())){
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
        }
        if (Objects.nonNull(user.getId())){
            user.setUpdateTime(new Date());
            result = userService.saveOrUpdate(user);
        }
        //注册
        if (Objects.isNull(user.getId())){
            Result checkRegistResult = userServiceControllerDetailService.checkRegistData(user);
            if (!checkRegistResult.getRs()){
                return checkRegistResult;
            }
            user.setStatus(Constant.UNDELETE_STATUS);
            user.setStatus(Constant.USER_TYPE_VAILD_STATUS);
            user.setDeleteFlag(Constant.UNDELETE_STATUS);
            //发送邮件
           // userServiceControllerDetailService.getEmailAddress(user,appUrl);
            user.setFileUrl(Constant.FILE_DEFAULT_NAME);
            user.setCreateTime(new Date());
            result =  userService.saveOrUpdate(user);
        }
        //结果处理
        if (result){
            if (Objects.nonNull(getSessionUser())){
               removeSessionUser();
            }
            setSession(Constant.SESSION_USER,user);
            return ResultUtils.success();
        }
            return  ResultUtils.error(Constant.ERROR_CODE,false);
    }

    /**
     * 邮政验证
     * @param code
     * @returnc
     */
    @Transactional
    @RequestMapping("/checkCode")
    public String checkCode(String code){
        User user = new User();
        user.setStatusCode(code);
        if (!Constant.RESULT_ONE_SUCCESS_SQL_RESULT.equals(userService.getUserCount(user,Constant.USER_TYPE_UNIQUE_STATUS_CODE))){
            return Constant.REDIRECT+urlPrefix+"regist";
        }
        User dbUser = userService.getUser(user,Constant.USER_TYPE_UNIQUE_STATUS_CODE);
        if (Objects.nonNull(dbUser)){
            //激活成功，让之前生成code失效
            String md5Code = MD5Utils.getMD5Str(code);
            dbUser.setStatusCode(md5Code);
            User updateUser= new User();
            updateUser.setId(dbUser.getId());
            userService.updateUser(dbUser,updateUser,Constant.USER_TYPE_UP_VAILD_STATUS);
        }
        return Constant.REDIRECT+urlPrefix+"login";
    }

    /**
     *  登录验证
     * @param username
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping("/doLogin")
    public Result loginUser(@RequestParam("username") String username,@RequestParam("password") String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Utils.getMD5Str(password));
        logger.info(username+"======>登录中");
        User dbUser = userService.getUser(user, Constant.USER_TYPE_LOGIN);
        user.setStatus(dbUser.getStatus());
        if (Objects.nonNull(dbUser)){
            if(!user.getPassword().equals(dbUser.getPassword())){
              return ResultUtils.error(Constant.CHINESE_PASSWORD_ERROR_MESSAGE);
            }
            //头像为空则为默认值
            if (StringUtils.isEmpty(dbUser.getFileUrl())){
                dbUser.setFileUrl(Constant.FILE_DEFAULT_NAME);
            }
            //设置当前登录账号的状态
            Result checkStatusResult = userServiceControllerDetailService.checkStatusData(dbUser);
            if (!checkStatusResult.getRs()){
                return checkStatusResult;
            }
            //设置当前登录人
            setSession(Constant.SESSION_USER,dbUser);
            //设置当前在线集合
            sessionUsers.add(dbUser);
            //检测是否存在当前登录人的相关配置
            if (!user.getStatus().equals(Constant.USER_TYPE_INVAILD_STATUS)){
                Setting currentSetting = userServiceControllerDetailService.checkData(getSessionUser().getId());
                if (Objects.nonNull(currentSetting)){
                    logger.info(username+"======>设置装配中");
                    setSession(getSessionUser().getId()+Constant.CURRENT_SETTING,currentSetting);
                }
            }
            logger.info(username+"======>登录成功");
            setSession("index",0);
            return ResultUtils.success(Constant.RESULT_SUCCESS_MSG,user.getStatus());
        }
        return ResultUtils.error(Constant.ERROR_CODE,Constant.CHINESE_SELECT_BLANK_USERNAME_MESSAGE);
    }

    /**
     * 获取所有用户数据
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/userListData")
    public PageVo getUsers(User user){
        //得到总页数
        int total = userService.getUsersCount(user);
        //得到user数据对象
        List<User> rows = userService.getUserList(user);
        return  new PageVo(rows,total);
    }

    /**
     * 删除某个user
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/deleteUser")
    public Result deleteUser(@RequestParam String ids){
        if (StringUtils.isBlank(ids)){
            return ResultUtils.error(Constant.NULL_CODE);
        }
        List<String> list = StringsUtils.StrToList(ids);
        if (StringsUtils.listIsContainsStr(getSessionUser().getId().toString(),list)){
            return ResultUtils.error(getSessionUser().getUsername()+"正在使用，无法操作");
        }
        boolean del = userService.removeByIds(list);
        return ResultUtils.dataResult(del,Constant.ERROR_CODE);
    }

    /**
     * 冻结某个user
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/freezeUser")
    public Result freezeUser(@RequestParam String ids){
        if (StringUtils.isBlank(ids)){
            return ResultUtils.error(Constant.NULL_CODE);
        }
        if (ids.equals(getSessionUser().getId().toString())){
            return ResultUtils.error(getSessionUser().getUsername()+"正在使用，无法操作");
        }
        User dbUser = userService.getById(Long.valueOf(ids));
        if (Objects.isNull(dbUser)){
            return ResultUtils.error(Constant.NULL_CODE);
        }
        dbUser.setStatus(Constant.USER_TYPE_FROZEN_STATUS);
        boolean update = userService.updateById(dbUser);
        return ResultUtils.dataResult(update,Constant.ERROR_CODE);
    }

    /**
     * 恢复某个冻结的user
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/recoverUser")
    public Result recoverUser(@RequestParam String ids){
        if (StringUtils.isBlank(ids)){
            return ResultUtils.error(Constant.NULL_CODE);
        }
        User dbUser = userService.getById(Long.valueOf(ids));
        if (Objects.isNull(dbUser)){
            return ResultUtils.error(Constant.NULL_CODE);
        }
        dbUser.setStatus(Constant.USER_TYPE_VAILD_STATUS);
        boolean update = userService.updateById(dbUser);
        return ResultUtils.dataResult(update,Constant.ERROR_CODE);
    }

    /**
     * 编辑user的数据
     * @param id
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/doQueryUser")
    public Result updateUser(@RequestParam Long id){
        User dbUser = userService.getById(id);
        Result result = userServiceControllerDetailService.checkStatusData(dbUser);
        if (!result.getRs()){
            return result;
        }
        return ResultUtils.success(dbUser);
    }

    /**
     * 忘记密码
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/getEmailCode")
    public Result getEmailCode(@RequestParam String email){
        boolean result = false;
        String mailCode = null;
        User user = new User();
        user.setEmail(email);
        User dbUser = userService.getUser(user, "");
        if (Objects.isNull(dbUser)){
            return ResultUtils.error(Constant.NULL_CODE);
        }
        VerifyCode sessionVerifyCode= (VerifyCode) getSessionValue(Constant.SESSION_VERIFY_CODE+dbUser.getId());
        if (Objects.isNull(sessionVerifyCode)){
            logger.info("email=>"+user.getEmail());
            mailCode = EmailUtils.SendMailCode(user.getEmail(), 6);
            VerifyCode verifyCode = new VerifyCode();
            verifyCode.setCreateTime(new Date());
            verifyCode.setVerifyCodeValue(mailCode);
            verifyCode.setVaildTime(6*10000L);
            setSession(Constant.SESSION_VERIFY_CODE+dbUser.getId(),verifyCode);
            return  ResultUtils.success(Constant.SEND_EMAIL_SEND_SUCCESS_MESSAGE);
        }else {
            //是否超过验证码有效时间
            result = DateUtils.dateCompare(sessionVerifyCode.getCreateTime(), new Date(), sessionVerifyCode.getVaildTime());
        }
        if (result){
            //超过了有效时间，移除之前的验证码，可重新发送验证码
            removeSessionValue(Constant.SESSION_VERIFY_CODE+dbUser.getId());
            getSession().removeAttribute(Constant.SESSION_VERIFY_CODE+dbUser.getId());
            mailCode = EmailUtils.SendMailCode(user.getEmail(), 6);
            VerifyCode verifyCode = new VerifyCode();
            verifyCode.setCreateTime(new Date());
            verifyCode.setVerifyCodeValue(mailCode);
            verifyCode.setVaildTime(6*10000L);
            getSession().setAttribute(Constant.SESSION_VERIFY_CODE+dbUser.getId(),verifyCode);
           return ResultUtils.success(Constant.SEND_EMAIL_SEND_SUCCESS_MESSAGE);
        }else {
            //有效时间不能重复发送验证码
           return ResultUtils.error(Constant.ERROR_CODE,Constant.SEND_EMAIL_SEND_VAILD_TIME_MESSAGE);
        }
    }

    /**
     * 检查验证码修改密码验证
     * @param emailCode
     * @param email
     * @param newPassword
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/forgetPassword")
    public Result checkUpdateCode(@RequestParam String emailCode,@RequestParam String email,@RequestParam String newPassword){
        User user = new User();
        user.setEmail(email);
        User dbUser = userService.getUser(user, "");
        if (Objects.isNull(dbUser)){
            return ResultUtils.error("该邮件未注册");
        }
        VerifyCode verifyCode = (VerifyCode) getSessionValue(Constant.SESSION_VERIFY_CODE+dbUser.getId());
        boolean vaildTime = DateUtils.dateCompare(verifyCode.getCreateTime(), new Date(), verifyCode.getVaildTime());
        //判断验证码是否失效
        if (vaildTime){
            return ResultUtils.error("验证码已失效");
        }
        if (StringUtils.isBlank(emailCode)){
            return ResultUtils.error(Constant.ERROR_FILL_ERROR_CODE);
        }
        //判断填入数据是否正确
        if (verifyCode.getVerifyCodeValue().equals(emailCode)){
            getSession().removeAttribute(Constant.SESSION_VERIFY_CODE+dbUser);
            dbUser.setPassword(newPassword);
            boolean result = userService.updateById(dbUser);
            return ResultUtils.dataResult(result);
        }
        return ResultUtils.error(Constant.ERROR_FILL_ERROR_CODE);
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
    public Result updatePassword(@RequestParam("password") String password,@RequestParam("newPassword") String newPassword,@RequestParam("userId")Long id ){
      /*  User user = (User) request.getSession().getAttribute(Constant.SESSION_USER);*/
        if (StringUtils.isBlank(password.trim())){
            return ResultUtils.error(Constant.CHINESE_BLANK_MESSAGE);
        }
        if (StringUtils.isBlank(newPassword.trim())){
            return ResultUtils.error(Constant.CHINESE_BLANK_MESSAGE);
        }
        User user = userService.getById(id);
        if (!(user.getPassword().equals(MD5Utils.getMD5Str(password)))){
            return ResultUtils.error(Constant.ERROR_FILL_ERROR_CODE);
        }
        user.setPassword(MD5Utils.getMD5Str(newPassword));
        boolean updateById = userService.updateById(user);
        if (updateById){
            userService.updateById(user);
            removeSessionUser();
            return ResultUtils.success();
        }
        return ResultUtils.error(Constant.ERROR_CODE);
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    @ResponseBody
    @Transactional
    @PostMapping("/upload")
    public Result uploadImg(@RequestParam("file") MultipartFile file){
        String filePath = fileService.uploadImg(file);
        return ResultUtils.success("上传成功",filePath);
    }

    /**
     * 退出登录
     */
    @ResponseBody
    @GetMapping("/toRemoveUser")
    public Result toRemoveUser(){
        //移除会话数据存在的用户
        removeCurrentSetting();
        removeSessionUser();
        return ResultUtils.success();
    }


}

