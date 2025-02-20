package com.moon.joyce.example.controller;

import com.moon.joyce.commons.annotation.url.UriPri;
import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.ListsUtils;
import com.moon.joyce.example.entity.doma.User;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.functionality.entity.doma.Setting;
import com.moon.joyce.example.functionality.service.DictService;
import com.moon.joyce.example.functionality.service.FileService;
import com.moon.joyce.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Xing Dao Rong
 * @date 2021/9/15 10:07
 * @desc 默认的页面（index,404,500）
 */
@Controller
@UriPri( name = "默认页面相关",pri = "/" ,scene = "/")
@RequestMapping("/")
public class IndexController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${file.config.path}")
    private String confPath;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    @Autowired
    private DictService dictService;
    /**
     * 当前场景
     */
    private final static String currentScene = "/";
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
    public String mainPage(ModelMap modelMap){
        modelMap.addAttribute("redisConnection",isRedisConnection);
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
        String filePathName = confPath + user.getId() + "_config.xml";
        //加载配置文件
        Setting setting = new Setting();
        fileService.writeJoyceConfig(user.getId().toString(),null);
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
                    List<String> list = ListsUtils.readyLineFileConvertList(Constant.LOGGER_PATH + fileName, StandardCharsets.UTF_8);
                    ListsUtils.addList(strings, list);
                }
                strings.add(filePathName+" has been loaded");
                strings.add("Joyce has been close");
                user.setStatus(Constant.START_STATUS);
                setSession(Constant.SESSION_USER,user);
            }else {
                strings.clear();
                strings.add("Joyce no start");
                strings.add("Joyce no start");
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
        return success(strings);}



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
        return dataResult(rs,"Joyce初始化失败","Joyce初始化成功");
    }



}

