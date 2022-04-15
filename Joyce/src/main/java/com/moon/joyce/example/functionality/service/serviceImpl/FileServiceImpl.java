package com.moon.joyce.example.functionality.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moon.joyce.commons.constants.Constant;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moon.joyce.commons.utils.R;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import com.moon.joyce.commons.utils.FileUtils;
import com.moon.joyce.example.entity.SysMenu;
import com.moon.joyce.example.functionality.entity.PageComponent;
import com.moon.joyce.example.functionality.service.FileService;
import com.moon.joyce.example.mapper.SysMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2021/09/23-- 1:14
 * @describe: 上传文件实现类
 */
@Service
public class FileServiceImpl implements FileService {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());
    @Value("${file.upload.path}")
    private String sysPath;
/*    @Value("${file.upload.relative}")
    private String relationImg;*/
    @Value("${file.upload.access}")
    private String access;
    @Value("${file.config.path}")
    private String confPath;
    @Value("${file.temp}")
    private  String fileUploadTempDir;
    @Value("${file.upload.path}")
    private  String fileUploadDir ;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Resource
    private ResourceLoader resourceLoader;
    @Override
    public String uploadImg(MultipartFile file) {
        String path = FileUtils.fileUpLoad(file, sysPath, access);
        logger.info("文件:{}正在上传,访问路径为：{}",sysPath+file,path);
        return   path;
    }

    @Override
    public Map <String, List<PageComponent>> readJoyceConfig(String username) {
        String filePathName = confPath + username + "_config.xml";
        logger.info("正在读取{}文件",filePathName);
        Map<String, List<PageComponent>> map = FileUtils.readXmlConfig(filePathName);
        return map;
    }

    @Override
    public void writeJoyceConfig(String username, Map<String, List<PageComponent>> map) {
        //文件全路径
        String filePathName = confPath + username + "_config.xml";
        File file = new File(filePathName);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        //初始化配置文件
        if (null == map) {
            logger.info("文件：{}，初始化配置开始", filePathName);
            Map<String, List<PageComponent>> initMap = new HashMap<>();
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.isNotNull("menu_url");
            List<SysMenu> sysMenus = sysMenuMapper.selectList(wrapper);
            System.out.println(sysMenus.toString());
            ArrayList<PageComponent> pageComponents = new ArrayList<>();
            for (SysMenu sysMenu : sysMenus) {
                Map<String, String> params = new HashMap<>();
                params.put(Constant.FONTSIZE_DEFAULT_NAME, Constant.FONTSIZE_DEFAULT_SIZE);
                params.put(Constant.FILE_DEFAULT_SET_NAME, Constant.FILE_DEFAULT_URL);
                PageComponent pageComponent =
                        PageComponent.builder()
                        .name(sysMenu.getMenuName())
                        .params(params)
                        .backgroundType(Constant.BACKGROUND_DEFAULT_TYPE)
                        .backgroundColor(Constant.BACKGROUND_DEFAULT_COLOR)
                        .backgroundUrl(Constant.BACKGROUND_DEFAULT_URL)
                        .build();
                pageComponents.add(pageComponent);
            }
            initMap.put("init_page_config", pageComponents);
            FileUtils.writeConfigXml(filePathName,initMap);
            logger.info("文件：{}初始化配置结束",filePathName);
        }else {
            logger.info("已有配置，无需初始化配置");
            FileUtils.writeConfigXml(filePathName,map);
        }
    }

    @Override
    public void downloadFile(HttpServletResponse response, HttpServletRequest request,String filename,String serverFilename) {
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            String path = "templates/"+serverFilename;
            org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:"+path);
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("charset", "utf-8");
            response.addHeader("Pragma", "no-cache");
            String encodeName = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);
            inputStream = resource.getInputStream();
            servletOutputStream = response.getOutputStream();
            IOUtils.copy(inputStream, servletOutputStream);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (servletOutputStream != null) {
                    servletOutputStream.close();
                    servletOutputStream = null;
                }
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
                File file = new File(System.getProperty("user.dir") + "\\Joyce\\target\\classes\\templates\\" + serverFilename);
                if (file.exists()){
                    logger.info("文件：{}下载完成:",filename);
                    file.delete();
                }
                // 召唤jvm的垃圾回收器
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int uploadVideo(HttpServletRequest res, HttpServletResponse resp) {
        logger.info("目录:{}正在存入新文件",fileUploadDir);
        return com.moon.joyce.commons.utils.FileUtils.uploadVideo(res, resp, fileUploadTempDir,fileUploadDir);

    }

    @Override
    public int mergeTempFile( String uuid, String name) {
       return FileUtils.mergeTempFile(fileUploadTempDir, fileUploadDir, uuid, name);
    }


}
