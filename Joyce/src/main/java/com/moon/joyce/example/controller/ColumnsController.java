package com.moon.joyce.example.controller;

import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.FileUtils;
import com.moon.joyce.commons.utils.ResultUtils;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.commons.utils.WebUtils;
import com.moon.joyce.example.functionality.entity.Column;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.functionality.entity.Setting;
import com.moon.joyce.example.functionality.service.ColumnsService;
import com.moon.joyce.example.functionality.service.DbBaseSettingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/19
 */
@Controller
@RequestMapping("/example/columns")
public class ColumnsController extends BaseController {
   private Logger logger = LoggerFactory.getLogger(this.getClass());
   @Autowired
   private ColumnsService columnsService;
    /**
     * 创建的文件路径
     */
   private String path = System.getProperty("user.dir") + "\\Joyce\\target\\classes\\templates\\files\\";
   @RequestMapping("/getColumnsPage")
   public String getColumnsPage(){
     /* logger.info("当前配置:\n数据源:"+getCurrentSetting().getDbBaseSetting().toString()+"\n数据包:"+getCurrentSetting().getPackageInfo().toString());*/
      return "common/public/getColumns";
   }

   @ResponseBody
   @RequestMapping("/getColumns")
   public Result getColumns(@RequestParam String tableName,@RequestParam String dbName){
       //创建当前session中的应用数据源
       boolean result = startupDatasource();
       if (!result){
            logger.info("当前为默认系统数据源");
        }else {
            logger.info("当前数据源:"+getCurrentSetting().getDbBaseSetting().getDataSourceName());
        }
     if (StringUtils.isBlank(tableName)||StringUtils.isBlank(dbName)){
        return ResultUtils.error(Constant.ERROR_FILL_ERROR_CODE);
     }
     /* Column table = columnsService.getColumn.js(tableName,dbName);*/
      List<Column> columns = columnsService.getColumns(tableName,dbName);
       if (Objects.isNull(columns)||columns.isEmpty()){
           return ResultUtils.error("该表无数据或者不存在");
       }
       logger.info("size:"+columns.size());
       List<Column> columns1 = new ArrayList();
       logger.info("size:"+columns.size());
       int temp =0;
       tableName = columns.get(0).getTableName().substring(columns.get(0).getTableName().indexOf("_") + 1);
       logger.info("tablename:"+ tableName);
       for (Column column : columns) {
           temp++;
           column.setTableName(tableName);
           columns1.add(column);
           if (temp== columns.size()/2){
               break;
           }
       }
      String type = "0";
      //设置超级管理员在本地执行
      if (getSessionUser().getUsername().equals(superAdministrator)){
          type = "1";
      }
      //调用文件创建工具类
       if (Objects.isNull(getCurrentSetting().getPackageInfo())){
           return ResultUtils.error("数据对应的包还未配置，请先配置相关的包");
       }
      WebUtils.createWeb(columns1,type,getCurrentSetting().getPackageInfo());
      //创建对应类的文件名称存入session
      Map<String, String> map = new HashMap<>();
      String className = StringsUtils.getClassName(columns1.get(0).getTableName());
      map.put("entity", className +".java");
      map.put("Controller", className +"Controller.java");
      map.put("Service", className +"Service.java");
      map.put("ServiceImpl", className +"ServiceImpl.java");
      map.put("Mapper", className +"Mapper.java");
      map.put("Mapperxml", className +"Mapper.xml");
      setSession(getSessionUser().getUsername()+"webFile",map);
      logger.info("创建："+columns1.get(0).getTableName());
       boolean res = shutdownDatasource();
       if (res){
           logger.info("数据源关闭成功");
       }else {
           logger.info("数据源关闭失败");
       }
       return ResultUtils.success();
   }

    /**
     * 下载web文件
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("/downloadWebFile")
    public void downloadWebFile(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Map<String, String> webFile = (HashMap<String, String>) getSessionValue(getSessionUser().getUsername()+"webFile");
        String zipName = getSessionUser().getUsername()+"_JACFile.zip";
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition","attachment; filename="+zipName);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        for (Map.Entry<String, String> entry : webFile.entrySet()) {
            FileUtils.doCompress( path+entry.getValue(),out);
            response.flushBuffer();
            System.out.println("下载执行:"+entry.getValue());
            /*fileService.downloadFile(response,request,entry.getKey(),entry.getValue());*/
        }
        removeSessionValue(getSessionUser().getUsername()+"webFile");
    }
        @ResponseBody
        @RequestMapping("/getAllTables")
        public Result selectTablesByDatabase(String databaseName){
            boolean result = startupDatasource();
            if (!result){
                logger.info("当前为默认系统数据源");
            }else {
                logger.info("当前数据源:"+getCurrentSetting().getDbBaseSetting().getDataSourceName());
            }

          List<Column> tables = columnsService.selectAllTables(databaseName);
            boolean res = shutdownDatasource();
            if (res){
                logger.info("数据源关闭成功");
            }else {
                logger.info("数据源关闭失败");
            }
            if (tables.isEmpty()){
                return ResultUtils.error("该数据库无数据或者连接有误");
            }
            return ResultUtils.success(tables);
        }

}

