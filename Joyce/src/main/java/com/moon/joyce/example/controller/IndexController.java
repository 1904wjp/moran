package com.moon.joyce.example.controller;

import com.moon.joyce.commons.base.cotroller.BaseController;

import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.FileUtils;
import com.moon.joyce.commons.utils.ResultUtils;
import com.moon.joyce.example.entity.Source;
import com.moon.joyce.example.entity.User;
import com.moon.joyce.example.entity.vo.MainSource;
import com.moon.joyce.example.functionality.entity.PageComponent;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.functionality.entity.Setting;
import com.moon.joyce.example.functionality.service.FileService;
import com.moon.joyce.example.service.SourceService;
import com.moon.joyce.example.service.UserService;
import com.moon.joyce.example.service.serviceControllerDetails.UserServiceControllerDetailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.*;

/**
 * @author Xing Dao Rong
 * @date 2021/9/15 10:07
 * @desc 默认的页面（index,404,500）
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${file.config.path}")
    private String confPath;
    @Autowired
    private SourceService sourceService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserServiceControllerDetailService userServiceControllerDetailService;
    /**
     * 头菜单
     * @return
     */
    @RequestMapping("/headerBar")
    public String headerBarPage(){
        return "common/public/headerBar";
    }
    /**
     * 欢迎页
     * @return
     */
    @RequestMapping("/index")
    public String indexPage(){
        return "index";
    }

    /**
     * 未发现页面
     * @return
     */
    @RequestMapping("/aboutUs")
    public String aboutUs(){
        return "common/public/aboutUs";
    }

    /**
     * 未发现页面
     * @return
     */
    @RequestMapping("/404")
    public String notFindPage(){
        return "common/error/404";
    }

    /**
     * 服务错误页面
     * @return
     */
    @RequestMapping("/500")
    public String codeErrorPage(){
        return "common/error/500";}

    /**
     * 主页
     * @return
     */
    @RequestMapping("/main")
    public String mainPage(){
        User sessionUser = getSessionUser();
        if (sessionUser.getStatus().equals(Constant.START_STATUS)){
            return "main/main2";
        }
        String filePathName = confPath + getSessionUser().getUsername() + "_config.xml";
        if (!sessionUser.getStatus().equals(Constant.APPLY_STATUS)){
            File file = new File(filePathName);
            if (!file.exists()){
                Map<String, Object> map = new HashMap<>();
                map.put(Constant.DEFAULT_CONFIG_FILE,file);
                getCurrentSetting().setMap(map);
                setSession(getSessionUser().getId()+Constant.CURRENT_SETTING,getCurrentSetting());
            }
        }
        userService.saveOrUpdate(sessionUser);
        return "main/main";}

    /**
     * 主页初始化
     * @return
     */
    @ResponseBody
    @RequestMapping("/doMainInit")
    public Result doMainInit(@RequestParam("code")String code){


        User user = getSessionUser();
        //文件内容
        String filePathName = confPath + user.getUsername() + "_config.xml";
        //加载配置文件
        Setting setting = new Setting();
        fileService.writeJoyceConfig(user.getUsername(),null);
        Map<String, Object> map = new HashMap<>();
        File file = new File(filePathName);
        if (file.exists()){
            map.put(Constant.DEFAULT_CONFIG_FILE,file);
        }
        setting.setMap(map);
        if (Objects.nonNull(setting)){
            logger.info(user+"======>第一次设置装配中");
            setSession(user.getId()+Constant.CURRENT_SETTING,setting);
        }
        String[] fileNames = {"/info.log","/warn.log","/error.log"};
        List<String> strings = new ArrayList<>();
        int index = (int)getSessionValue("index");

        if (code.equals(c_start)){
            if (index==0){
                setSession("index",1);
                strings.add("Joyce has been started");
            }else {
                strings.add("Joyce has been started,No need to open");
            }
        }else if (code.equals(c_init)){
            if (index==1){
                for (String fileName : fileNames) {
                    List<String> list = FileUtils.readyLineFileConvertList(Constant.LOGGER_PATH + fileName);
                    FileUtils.addList(strings, list);
                }
                strings.add(filePathName+" has been loaded");
                user.setStatus(Constant.START_STATUS);
                setSession(Constant.SESSION_USER,user);
            }else {
                strings.clear();
                strings.add("Joyce no start");
            }
        }else if (code.equals(c_init)){
           if (index!=1){
               strings.clear();
               strings.add("Joyce no start");
           }else {
               strings.clear();
               strings.add("Joyce has been close");
           }
        }else if(code.equals(c_clear)){
            strings.clear();
            strings.add("########********###");
        }else if(code.equalsIgnoreCase("Terminate batch operation(y/n):y")){
            strings.clear();
            strings.add("########********###::y");
        }else if(code.equalsIgnoreCase("Terminate batch operation(y/n):n")){
            strings.clear();
            strings.add("########********###::n");
        }else  {
            strings.clear();
            strings.add("The command was not found");
        }
        return ResultUtils.success(strings);}

    /**
     * 主页配置
     * @return
     */
    @ResponseBody
    @RequestMapping("/getSourceImage")
    public Result doProjectMainPage(){
        Source source = new Source();
        source.setDeleteFlag(Constant.UNDELETE_STATUS);
        source.setApplyStatus(Constant.APPLY_STATUS);
        source.setUserId(getSessionUserId());
        Source dbSource = sourceService.getOne(source);
        source.setApplyStatus(Constant.SPARE_STATUS);
        List<Source> list = sourceService.getList(source);
        if (Objects.nonNull(dbSource)&&!list.isEmpty()){
            MainSource mainSource = new MainSource(1l,dbSource, list);
            return ResultUtils.dataResult(true,mainSource);
        }
        return ResultUtils.error("主页背景默认配置");
    }

    /**
     * 关于我们
     * @return
     */
    @ResponseBody
    @RequestMapping("/aboutUsData")
    public Result aboutUsData(){
        String form = "1692239985@qq.com";
        String vx ="wxp://f2f02G94YifDWYRnnR2seljn7DoPsl_cpDXe_D6g1qTP9G8";
        String zfb = "https://qr.alipay.com/fkx15354mdrzidzicds5cc2?t=1642754834489";
        List<String> list = new ArrayList<>();
        list.add(form);
        list.add(vx);
        list.add(zfb);
        return ResultUtils.dataResult(!list.isEmpty(),Constant.NULL_CODE,list);
    }

    /**
     * 改变状态
     * @return
     */
    @ResponseBody
    @RequestMapping("/changeStatus")
    public Result changeStatus(){
        User sessionUser = getSessionUser();
        sessionUser.setStatus(Constant.START_STATUS);
        boolean rs = userService.saveOrUpdate(sessionUser);
        return ResultUtils.dataResult(rs,"Joyce初始化失败","Joyce初始化成功");
    }

}

