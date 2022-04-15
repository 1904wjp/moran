package com.moon.joyce.example.controller;


import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.R;
import com.moon.joyce.example.entity.Source;
import com.moon.joyce.example.entity.vo.MainSource;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.functionality.service.FileService;
import com.moon.joyce.example.service.SourceService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */
@Controller
@RequestMapping("/example/source")
public class SourceController extends BaseController {
    /**
     * 页面路径前缀
     */
    private final String pagePrefix = "source/";
    @Autowired
    private SourceService sourceService;
    @Autowired
    private FileService fileService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 资源页面
     *
     * @return
     */
    @RequestMapping("/sourcePage")
    public String addSourcePage() {
        return pagePrefix + "sourcePage";
    }

    /**
     * 获取资源列表
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/getList")
    public PageVo getList(Source source) {
        source.setUserId(getSessionUser().getId());
        List<Source> list = sourceService.getList(source);
        int total = sourceService.getCount(source);
        return new PageVo(list, total);
    }

    /**
     * 保存资源
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/saveSource")
    public Result addSource(Source source) {
        System.out.println("====>" + source.toString());
        if (source.getApplyStatus().equals(Constant.APPLY_STATUS)) {
            boolean b = retireApplyStatus();
            if (!b) {
                return R.error();
            }
        }
        Source source1 = new Source();
        source1.setSourceName(source.getSourceName());
        source1.setUserId(getSessionUserId());
        Source one = sourceService.getOne(source1);
        if (Objects.nonNull(one)) {
            return R.error("资源名已存在，请修改资源名重新上传");
        }
        if (Objects.isNull(source.getId())) {
            source.setCreateBy(getSessionUser().getUsername());
            source.setUserId(getSessionUser().getId());
            source.setCreateTime(new Date());
            source.setDeleteFlag(0);
        } else {
            source.setUpdateBy(getSessionUser().getUsername());
            source.setUserId(getSessionUser().getId());
            source.setUpdateTime(new Date());
        }
        boolean rs = sourceService.saveOrUpdate(source);
        return R.dataResult(rs);
    }

    /**
     * 保存资源
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/setMainSource")
    public Result addSource(@RequestParam("id") Long id) {
        Source source = sourceService.getById(id);
        if (Objects.isNull(source)) {
            return R.error(Constant.NULL_CODE);
        }
        source.setUpdateBy(getSessionUser().getUsername());
        source.setUpdateTime(new Date());
        boolean b = retireApplyStatus();
        if (!b) {
            return R.error();
        }
        source.setApplyStatus(Constant.APPLY_STATUS);
        boolean rs = sourceService.saveOrUpdate(source);
        return R.dataResult(rs, "修改失败", "修改成功", source);
    }


    /**
     * 上传资源
     *
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping("/uploadSource")
    public Result uploadSource(@RequestParam(value = "file", required = true) MultipartFile file) {
        String filePath = fileService.uploadImg(file);
        return R.success("上传成功", filePath);
    }

    public boolean retireApplyStatus() {
        Source source1 = new Source();
        source1.setDeleteFlag(Constant.UNDELETE_STATUS);
        source1.setApplyStatus(Constant.APPLY_STATUS);
        source1.setUserId(getSessionUserId());
        Source one = sourceService.getOne(source1);
        if (Objects.nonNull(one)) {
            one.setApplyStatus(Constant.SPARE_STATUS);
            return sourceService.saveOrUpdate(one);
        }
        return true;
    }

    /**
     * 主页配置
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/getSourceImage")
    public Result doProjectMainPage() {
        Source source = new Source();
        source.setDeleteFlag(Constant.UNDELETE_STATUS);
        source.setApplyStatus(Constant.APPLY_STATUS);
        source.setUserId(getSessionUserId());
        Source dbSource = sourceService.getOne(source);
        source.setApplyStatus(Constant.SPARE_STATUS);
        List<Source> list = sourceService.getList(source);
        if (Objects.nonNull(dbSource) && !list.isEmpty()) {
            MainSource mainSource = new MainSource(1L, dbSource, list);
            return R.dataResult(true, mainSource);
        }
        return R.error("主页背景默认配置");
    }

    /**
     * 上传临时文件分片
     * @param req
     * @param resp
     * @return
     */
    @ResponseBody
    @PostMapping("/uploadVideoSource")
    public Result uploadVideo(HttpServletRequest req, HttpServletResponse resp) {
        int code = fileService.uploadVideo(req, resp);
        if (code==200){
            return R.success("视频上传完成，进行解析！");
        }
        if (code==201){
            return R.success("正在上传视频！");
        }
         return R.error("视频上传失败！");
    }

    /**
     *
     * @param uuid
     * @param newFileName
     * @return
     */
    @ResponseBody
    @GetMapping("/mergeVideoSource")
    public Result mergeVideo( String uuid, String newFileName){
        int code = fileService.mergeTempFile(uuid, newFileName);
       return R.dataResult(code==200,"视频解析失败","视频解析成功");
    }
    /**
     * 冒泡排序
     */


}

