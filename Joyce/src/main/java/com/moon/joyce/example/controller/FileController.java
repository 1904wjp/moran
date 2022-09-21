package com.moon.joyce.example.controller;

import com.moon.joyce.commons.annotation.url.MethodUrl;
import com.moon.joyce.commons.annotation.url.UriPri;
import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.functionality.service.FileService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/08/02-- 9:24
 * @describe:文件上传接口
 */
@UriPri(name = "文件接口", pri = "/fileUpload")
@RequestMapping("/fileUpload/s")
public class FileController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FileService fileService;

    /**
     * 上传多个文件
     *
     * @param files
     * @return
     */
    @ResponseBody
    @MethodUrl(name = "多文件上传", url = "/files")
    @PostMapping("/files")
    public Result uploadAlbum(@RequestParam("files") MultipartFile[] files) {
        String paths = null;
        try {
            paths = fileService.uploadImgs(files);
        } catch (Exception e) {
            e.printStackTrace();
            return error("上传失败");
        }
        logger.info("上传相册的路径----》" + paths);
        return dataResult(Objects.nonNull(paths), "上传失败", "上传成功", paths);
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @ResponseBody
    @Transactional
    @PostMapping("/file")
    public Result uploadImg(@RequestParam("file") MultipartFile file) {
        String filePath = null;
        try {
            filePath = fileService.uploadImg(file);
        } catch (Exception e) {
            return error("上传失败");
        }
        return success("上传成功", filePath);
    }

}
