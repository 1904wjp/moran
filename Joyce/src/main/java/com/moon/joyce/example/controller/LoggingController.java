package com.moon.joyce.example.controller;


import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.doma.Logging;
import com.moon.joyce.example.functionality.service.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 日志控制层
 */
@Controller
@RequestMapping("/example/logging")
public class LoggingController extends BaseController {

    private final static String currentScene = "logging";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String urlPrefix = "/example/logging";
    @Autowired
    private LoggingService loggingService;

    /**
     * 日志页面
     */
    @GetMapping("/loggingPage")
    public String getHeaderBarPage() {
        return "logging/loggingListPage";
    }

    /**
     * 获取所有用户数据
     *
     * @param logging
     * @return
     */
    @ResponseBody
    @RequestMapping("/loggingList")
    public PageVo getUsers(Logging logging) {
        if (StringsUtils.isNoneBlank(logging.getUri())){
            String[] split = logging.getUri().split("/");
            if (split.length>3){
                logging.setUri(logging.getUri().substring(0,logging.getUri().lastIndexOf("/")));
            }
        }
        return loggingService.getPage(logging);
    }


}
