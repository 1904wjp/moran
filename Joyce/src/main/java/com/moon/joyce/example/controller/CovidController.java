package com.moon.joyce.example.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

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
