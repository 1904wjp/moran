package com.moon.joyce.example.service.serviceControllerDetails.UserServiceControllerDetailServiceImpl;

import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.UUIDUtils;
import com.moon.joyce.example.entity.DbBaseSetting;
import com.moon.joyce.example.entity.PackageInfo;
import com.moon.joyce.example.entity.User;
import com.moon.joyce.example.functionality.entity.EmailData;
import com.moon.joyce.example.functionality.entity.Setting;
import com.moon.joyce.example.functionality.service.DbBaseSettingService;
import com.moon.joyce.example.functionality.service.MailService;
import com.moon.joyce.example.functionality.service.PackageInfoService;
import com.moon.joyce.example.functionality.service.serviceImpl.PackageInfoServiceImpl;
import com.moon.joyce.example.service.serviceControllerDetails.UserServiceControllerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2021/09/22-- 0:15
 * @describe: controller细节处理服务层
 */
@Service
public class UserServiceControllerDetailServiceImpl implements UserServiceControllerDetailService {
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
        return  new Setting(dbBaseSettingService.getMain(dbBaseSetting), packageInfoService.getMain(packageInfo));
    }

}
