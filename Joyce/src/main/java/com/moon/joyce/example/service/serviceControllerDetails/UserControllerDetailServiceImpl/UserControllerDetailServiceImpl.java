package com.moon.joyce.example.service.serviceControllerDetails.UserControllerDetailServiceImpl;

import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.R;
import com.moon.joyce.commons.utils.UUIDUtils;
import com.moon.joyce.example.entity.doma.DbBaseSetting;
import com.moon.joyce.example.entity.doma.PackageInfo;
import com.moon.joyce.example.entity.doma.User;
import com.moon.joyce.example.functionality.entity.DTO.GitInfoDTO;
import com.moon.joyce.example.functionality.entity.doma.*;
import com.moon.joyce.example.functionality.entity.vo.GitInfoVO;
import com.moon.joyce.example.functionality.service.*;
import com.moon.joyce.example.service.UserService;
import com.moon.joyce.example.service.serviceControllerDetails.UserControllerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2021/09/22-- 0:15
 * @describe: controller细节处理服务层
 */
@Service
public class UserControllerDetailServiceImpl extends R implements UserControllerDetailService {
    /**
     * 注入用户服务
     */
    @Autowired
    private UserService userService;

    /**
     * 注入数据源服务
     */
    @Autowired
    private DbBaseSettingService dbBaseSettingService;
    /**
     * 注入包服务
     */
    @Autowired
    private PackageInfoService  packageInfoService;
    /**
     * 支付接口
     */
    @Autowired
    private PayConfigService payConfigService;
    /**
     * git服务
     */
    @Autowired
    private GitInfoService gitInfoService;
    /**
     * 注入邮件接口
     */
    @Autowired
    private MailService mailService;
    @Override
    public String getEmailAddress(User user,String appUrl) {
        //状态设置成未激活
        user.setStatus(Constant.USER_TYPE_INVAILD_STATUS);
        //随机生成状态激活码
        user.setStatusCode(UUIDUtils.getUUID());
        user.setDeleteFlag(Constant.UNDELETE_STATUS);
        user.setCreateTime(new Date());
        //发送邮件
        String emailTitle = Constant.SEND_EMAIL_TITLE;
        //设置html
        String context = "<a href="+appUrl+"/example/user/checkCode?code="+user.getStatusCode()+">"+ Constant.SEND_EMAIL_TEMPLATE2+user.getStatusCode()+"</a>";
        mailService.sendHtmlMail(EmailData.builder().to(user.getEmail()).subject(emailTitle).context(context).build());
        return context;
    }

    @Override
    public Setting checkData(Long userId) {
        DbBaseSetting dbBaseSetting = new DbBaseSetting();
        dbBaseSetting.setUserId(userId);
        dbBaseSetting.setApplyStatus(Constant.APPLY_STATUS);
        PackageInfo packageInfo = new PackageInfo();
        packageInfo.setApplyStatus(Constant.APPLY_STATUS);
        packageInfo.setUserId(userId);
        Map<String, Object> map = new HashMap<>();
        PayConfig payConfig = new PayConfig();
        payConfig.setCreateIds(userId);
        payConfig.setStatus(String.valueOf(Constant.UNDELETE_STATUS));
        PayConfig dbPayConfig = payConfigService.getOne(payConfig);
        if (Objects.nonNull(dbPayConfig)){
            map.put(payConfig.getClass().getSimpleName(),dbPayConfig);
        }
        GitInfoDTO gitInfoDTO = new GitInfoDTO();
        gitInfoDTO.setCreateIds(userId);
        GitInfoVO gitInfoVO = gitInfoService.getLatestOne(gitInfoDTO);
        if (Objects.nonNull(gitInfoVO)){
            map.put(gitInfoVO.getClass().getSimpleName(),dbPayConfig);
        }
        Setting setting = new Setting(dbBaseSettingService.getMain(dbBaseSetting), packageInfoService.getMain(packageInfo));
        setting.setMap(map);
        return  setting;
    }

    @Override
    public Result checkRegistData(User user) {
        //非空判断
        int userCountByUsername = userService.getUserCount(user, Constant.USER_TYPE_UNIQUE_USERNAME);
        if (userCountByUsername== Constant.RESULT_UNKNOWN_SQL_RESULT){
            return error(Constant.ERROR_FILL_ERROR_CODE);
        }
        //用户是否唯一
        if (userCountByUsername>Constant.RESULT_NO_SQL_RESULT){
            return error(Constant.ERROR_CODE,Constant.CHINESE_SELECT_EXIST_USERNAME_MESSAGE);
        }
        //非空判断
        int userCountByEmail = userService.getUserCount(user, Constant.USER_TYPE_UNIQUE_EMAIL);
        int userCountByPhone = userService.getUserCount(user, Constant.USER_TYPE_UNIQUE_PHONE);
        if (userCountByEmail== Constant.RESULT_UNKNOWN_SQL_RESULT){
            return error(Constant.ERROR_FILL_ERROR_CODE);
        }
        //邮件是否唯一
        if (userCountByEmail>Constant.RESULT_NO_SQL_RESULT){
            return error(Constant.ERROR_CODE,Constant.CHINESE_SELECT_EXIST_EMAIL_MESSAGE);
        }
        //手机号是否唯一
        if (userCountByPhone>Constant.RESULT_NO_SQL_RESULT){
            return error(Constant.ERROR_CODE,Constant.CHINESE_SELECT_EXIST_PHONE_MESSAGE);
        }
        return success();
    }

    @Override
    public Result checkStatusData(User user) {
        //用户为空（异常）
        if (Objects.isNull(user)){
            return error(Constant.NULL_CODE);
        }
        //状态为空（异常）
        if (Objects.isNull(user.getStatus())){
            return error(Constant.NULL_CODE,"该账号状态异常，请联系客服");
        }
        //状态冻结
        if (user.getStatus().equals(Constant.USER_TYPE_FROZEN_STATUS)){
            return error(Constant.CHINESE_FROZEN_MESSAGE);
        }
        //状态未激活
        if (user.getStatus().equals(Constant.INACTIVE_CODE)){
            return error(Constant.INACTIVE_CODE);
        }
        //通过状态
        if (user.getStatus().equals(Constant.USER_TYPE_VAILD_STATUS)
                ||user.getStatus().equals(Constant.START_STATUS)) {
            return success();}
        return error();
    }

}
