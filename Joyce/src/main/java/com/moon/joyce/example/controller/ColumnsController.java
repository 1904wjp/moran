package com.moon.joyce.example.controller;

import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.*;
import com.moon.joyce.example.functionality.entity.Column;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.functionality.service.ColumnsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipOutputStream;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/19
 */
@Controller
@RequestMapping("/example/columns")
public class ColumnsController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ColumnsService columnsService;
    /**
     * 创建的文件路径
     */
    private final String path = System.getProperty("user.dir") + "\\Joyce\\target\\classes\\templates\\files\\";

    @RequestMapping("/getColumnsPage")
    public String getColumnsPage() {
        /* logger.info("当前配置:\n数据源:"+getCurrentSetting().getDbBaseSetting().toString()+"\n数据包:"+getCurrentSetting().getPackageInfo().toString());*/
        return "common/public/getColumns";
    }

    /**
     * 获取属性列表
     * @param tableName
     * @param dbName
     * @return
     */
    @ResponseBody
    @RequestMapping("/getColumns")
    public Result getColumns(@RequestParam String tableName, @RequestParam String dbName) {
        //创建当前session中的应用数据源
        startupDatasource();
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(dbName)) {
            return R.error(Constant.ERROR_FILL_ERROR_CODE);
        }
        /* Column table = columnsService.getColumn.js(tableName,dbName);*/
        List<Column> columns = columnsService.getColumns(tableName, dbName);
        columns.stream().filter(x -> "NO".equals(x.getIsNull()) && Objects.isNull(x.getDefaultValue())).forEach(x -> x.setDefaultValue("默认值不为空"));
        if (columns.isEmpty()) {
            return R.error("该表无数据或者不存在");
        }
        List<Column> columns1 = new ArrayList<>();
        tableName = columns.get(0).getTableName().substring(columns.get(0).getTableName().indexOf("_") + 1);
        for (Column column : columns) {
            column.setTableName(tableName);
            columns1.add(column);
        }
        String type = "0";
        //设置超级管理员在本地执行
        if (getSessionUser().getUsername().equals(superAdministrator)) {
            type = "1";
        }
        //调用文件创建工具类
        if (Objects.isNull(getCurrentSetting().getPackageInfo())) {
            return R.error("数据对应的包还未配置，请先配置相关的包");
        }
        WebUtils.createWeb(columns1, type, getCurrentSetting().getPackageInfo());
        //创建对应类的文件名称存入session
        Map<String, String> map = new HashMap<>();
        String className = StringsUtils.getClassName(columns1.get(0).getTableName());
        map.put("entity", path+className + ".java");
        map.put("Controller",  path+className + "Controller.java");
        map.put("Service",  path+className + "Service.java");
        map.put("ServiceImpl",  path+className + "ServiceImpl.java");
        map.put("Mapper",  path+className + "Mapper.java");
        map.put("Mapperxml",  path+className + "Mapper.xml");
        setSession(getSessionUser().getUsername() + "webFile", map);
        logger.info("创建：" + columns1.get(0).getTableName());
        shutdownDatasource();
        return R.success(columns);
    }

    /**
     * 下载web文件
     * @param response
     * @throws IOException
     */
    @RequestMapping("/downloadWebFile")
    public void downloadWebFile(HttpServletResponse response) throws IOException {

        try {
            Map<String, String> webFile = (Map<String, String>) getSessionValue(getSessionUser().getUsername() + "webFile");
            String zipName = getSessionUser().getUsername() + UUIDUtils.getUUID(8) + "_JACFile.zip";
            FileUtils.downloadZip(zipName,response,webFile);
            removeSessionValue(getSessionUser().getUsername() + "webFile");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/getAllTables")
    public Result selectTablesByDatabase(String databaseName) {
        startupDatasource();
        List<Column> tables = columnsService.selectAllTables(databaseName);
        shutdownDatasource();
        if (tables.isEmpty()) {
            return R.error("该数据库无数据或者连接有误");
        }
        return R.success(tables);
    }

}

