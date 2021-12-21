package com.moon.joyce.example.controller;

import com.moon.joyce.commons.base.cotroller.BaseController;

import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.ResultUtils;
import com.moon.joyce.example.entity.User;
import com.moon.joyce.example.functionality.entity.PageComponent;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.functionality.service.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Xing Dao Rong
 * @date 2021/9/15 10:07
 * @desc 默认的页面（index,404,500）
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {
    @Autowired
    private FileService fileService;
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
    public String projectMainPage(){
        return "common/public/main";}

    /**
     * 主页配置
     * @return
     */
    @ResponseBody
    @RequestMapping("/doMain")
    public Result doProjectMainPage(){
        User sessionUser = getSessionUser();
        Map<String, List<PageComponent>> stringListMap = fileService.readJoyceConfig(sessionUser.getUsername());
        if (Objects.isNull(stringListMap)){
            return ResultUtils.success(Constant.FILE_DEFAULT_NAME);
        }
        if (!sessionUser.getFileUrl().equals(Constant.FILE_DEFAULT_NAME)){
            return ResultUtils.success(true,sessionUser.getFileUrl());
        }
        List<PageComponent> list = stringListMap.get("init_page_config");
        for (PageComponent pageComponent : list) {
            if (pageComponent.getName().equals("菜单")){
                if (StringUtils.isNotEmpty(pageComponent.getParams().get(Constant.FILE_DEFAULT_SET_NAME))){
                    return ResultUtils.success(true,pageComponent.getParams().get(Constant.FILE_DEFAULT_SET_NAME));
                }
            }
        }
        return ResultUtils.success(Constant.FILE_DEFAULT_NAME);
    }

}

