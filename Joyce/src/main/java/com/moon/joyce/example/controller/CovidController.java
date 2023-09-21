package com.moon.joyce.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/22
 * 疫情控制层
 */
@Controller
@RequestMapping("/example/covid")
public class CovidController {
    /**
     * 页面路径前缀
     */
    private final String pagePrefix = "common/public/";

    /**
     * 可视化页面
     * @return
     */
    @RequestMapping("/map")
    public String getCovidPge(){
        return pagePrefix+"covidPage";
    }

}
