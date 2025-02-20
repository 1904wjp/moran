package com.moon.joyce.example.functionality.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.DateUtils;
import com.moon.joyce.commons.utils.FileUtils;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.commons.utils.UUIDUtils;
import com.moon.joyce.example.entity.doma.SysMenu;
import com.moon.joyce.example.functionality.entity.doma.PageComponent;
import com.moon.joyce.example.functionality.service.FileService;
import com.moon.joyce.example.mapper.SysMenuMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2021/09/23-- 1:14
 * @describe: 上传文件实现类
 */
@Service
public class FileServiceImpl implements FileService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${file.upload.path}")
    private String sysPath;
    /*    @Value("${file.upload.relative}")
        private String relationImg;*/
    @Value("${file.upload.access}")
    private String access;
    @Value("${file.config.path}")
    private String confPath;
    @Value("${file.config.ueColorFileName}")
    private String ueColorFileName;
    @Value("${file.temp}")
    private String fileUploadTempDir;
    @Value("${file.upload.path}")
    private String fileUploadDir;


    //ueCss文件名称统计
    static String ueCssFileNameArrayStr;

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Resource
    private ResourceLoader resourceLoader;

    @Override
    public Map<String, String> uploadImg(MultipartFile file) {
        Map<String, String> map = null;
        try {
            map = FileUtils.fileUpLoad(file, sysPath, access);
        } catch (Exception e) {
            logger.error("文件上传异常:" + e.getMessage());
        }
        return map;
    }

    @Override
    public String uploadImgs(MultipartFile[] files) {
        List<String> paths = null;
        try {
            paths = FileUtils.fileUpLoad(files, sysPath, access);
        } catch (Exception e) {
            logger.error("文件上传异常:" + e.getMessage());
        }
        List<String> list = Arrays.stream(files)
                .map(MultipartFile::getName)
                .collect(Collectors.toList());
        ArrayList<String> arrayList = new ArrayList<>();
        list.forEach(x -> arrayList.add(sysPath + x));
        String names = StringsUtils.listToStr(arrayList);
        String pathStr = StringsUtils.appendStr(paths, ",");
        logger.info("文件:{}正在上传,访问路径为：{}", names, pathStr);
        return pathStr;
    }

    @Override
    public Map<String, List<PageComponent>> readJoyceConfig(String userId) {
        String filePathName = confPath + userId + "_config.xml";
        logger.info("正在读取{}文件", filePathName);
        Map<String, List<PageComponent>> map = FileUtils.readXmlConfig(filePathName);
        return map;
    }

    @Override
    public boolean writeJoyceConfig(String userId, Map<String, List<PageComponent>> map) {
        //文件全路径
        String filePathName = confPath + userId + "_config.xml";
        File file = new File(filePathName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        //初始化配置文件
        if (Objects.isNull(map) && !file.exists()) {
            logger.info("文件：{}，初始化配置开始", filePathName);
            FileUtils.writeConfigXml(filePathName, initPageComponent());
            if (FileUtils.fileExist(file, 60) && !readJoyceConfig(userId).isEmpty()) {
                logger.info("文件：{}初始化配置结束", filePathName);
                return true;
            }
        }
        if (Objects.isNull(map) && file.exists()) {
//            if (initPageComponent().toString().equals(readJoyceConfig(userId).toString())){
//                logger.info("文件：{}，已存在，无需初始化", filePathName);
//                return true;
//            }else {
//                logger.info("文件：{},数据异常，正在重新加载，初始化配置开始", filePathName);
//                logger.info(initPageComponent().toString()+"<<<<<<<<<<<"+readJoyceConfig(userId).toString());
//                FileUtils.deleteFile(filePathName);
//                FileUtils.writeConfigXml(filePathName, initPageComponent());
//                if (FileUtils.fileExist(file, 60) && !readJoyceConfig(userId).isEmpty()) {
//                    logger.info("文件：{}初始化配置结束", filePathName);
//                    return true;
//                }
//            }
            return true;
        }
        if (Objects.nonNull(map) && !map.isEmpty() && file.exists()) {
            FileUtils.deleteFile(filePathName);
            FileUtils.writeConfigXml(filePathName, map);
            if (FileUtils.fileExist(file, 60) && !readJoyceConfig(userId).isEmpty()) {
                logger.info("文件：{}更改配置结束", filePathName);
                return true;
            }
        }

        return false;
    }

    @Override
    public void downloadFile(HttpServletResponse response, HttpServletRequest request, String filename, String serverFilename) {
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            String path = "templates/" + serverFilename;
            org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:" + path);
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
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                File file = new File(System.getProperty("user.dir") + "\\Joyce\\target\\classes\\templates\\" + serverFilename);
                if (file.exists()) {
                    logger.info("文件：{}下载完成:", filename);
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
        logger.info("目录:{}正在存入新文件", fileUploadDir);
        return com.moon.joyce.commons.utils.FileUtils.uploadVideo(res, resp, fileUploadTempDir, fileUploadDir);

    }

    @Override
    public Map<String, Object> mergeTempFile(String uuid, String name) {
        Map<String, Object> map = FileUtils.mergeTempFile(fileUploadTempDir, sysPath, access, uuid, name);
        String realPath = map.get(FileUtils.vrp).toString();
        try {
            File picFile = FileUtils.captureVideoFrames(realPath, fileUploadTempDir + "/" + uuid + ".jpg");
            FileInputStream fileInput = new FileInputStream(picFile);
            MultipartFile toMultipartFile = new MockMultipartFile("file", picFile.getName(), "application/json;charset=UTF-8", IOUtils.toByteArray(fileInput));
            toMultipartFile.getInputStream();
            fileInput.close();
            String picPath = FileUtils.fileUpLoad(toMultipartFile, sysPath, access).get("v");
            picFile.delete();
            map.put(FileUtils.vp, picPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public String execPythonFile(FileUtils.ExecArgs execArgs) {
        String text = null;
        try {
            text = FileUtils.execPythonFile(execArgs);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return text;
    }

    //初始化组件
    public  Map<String, List<PageComponent>>  initPageComponent() {
        Map<String, List<PageComponent>> initMap = new HashMap<>();
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().isNotNull(SysMenu::getName);
        List<SysMenu> sysMenus = sysMenuMapper.selectList(wrapper);
        List<PageComponent> pageComponents = new ArrayList<>();
        Map<String, String> params;
        for (SysMenu sysMenu : sysMenus) {
            params = new HashMap<>();
            if (sysMenu.getId().equals(6L)) {
                if (Objects.isNull(ueCssFileNameArrayStr)) {
                    List<File> colorFiles = FileUtils.getFilesByMkdirPath(ueColorFileName);
                    ueCssFileNameArrayStr = StringsUtils.replaceAll(colorFiles.stream().
                            map(File::getName)
                            .collect(Collectors.toList())
                            .toString(), "[,],.css", "");
                }
                params.put("ueColor", ueCssFileNameArrayStr);
            }
            params.put(Constant.FONTSIZE_DEFAULT_NAME, Constant.FONTSIZE_DEFAULT_SIZE);
            params.put(Constant.FILE_DEFAULT_SET_NAME, Constant.FILE_DEFAULT_URL);
            params.put("login_date_" + UUIDUtils.getUUID(), DateUtils.dateForMat("s", new Date()));
            PageComponent pageComponent =
                    PageComponent.builder()
                            .id(sysMenu.getId())
                            .name(sysMenu.getName())
                            .params(params)
                            .backgroundType(Constant.BACKGROUND_DEFAULT_TYPE)
                            .backgroundColor(Constant.BACKGROUND_DEFAULT_COLOR)
                            .backgroundUrl(Constant.BACKGROUND_DEFAULT_URL)
                            .build();
            pageComponents.add(pageComponent);
        }
        initMap.put("init_page_config", pageComponents);
        return initMap;
    }
}

