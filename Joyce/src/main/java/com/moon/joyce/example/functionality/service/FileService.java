package com.moon.joyce.example.functionality.service;

import com.moon.joyce.commons.utils.FileUtils;
import com.moon.joyce.example.functionality.entity.PageComponent;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2021/09/23-- 0:15
 * @describe: 文件上传服务接口
 */
public interface FileService {
    /**
     * 图片上传
     * @param file
     * @return
     */
    String uploadImg(MultipartFile file);

    /**
     * 图片上传
     * @param file
     * @return
     */
    String uploadImgs(MultipartFile[] file);

    /**
     * 读取Joyce用户设置配置
     * @param username
     * @return
     */
    Map<String, List<PageComponent>> readJoyceConfig(String username);

    /**
     * 设置Joyce用户设置配置
     * @param username
     * @param map
     */
    boolean writeJoyceConfig(String username,Map<String, List<PageComponent>> map,boolean flag);

    /**
     * 下载文件
     * @param response
     * @param request
     */
    void downloadFile(HttpServletResponse response, HttpServletRequest request,String filename,String serverFilename);

    /**
     * 上传视频分段
     * @param res
     * @param resp
     * @return
     */
    int uploadVideo(HttpServletRequest res,HttpServletResponse resp);

    /**
     * 视频解析
     * @param uuid
     * @param name
     * @return
     */
    Map<String,Object> mergeTempFile(String uuid,String name);

    /**
     *  执行文件
     * @param execArgs
     * @return
     */
    String execPythonFile(FileUtils.ExecArgs execArgs);
}
