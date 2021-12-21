package com.moon.joyce.example.controller;


import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.utils.ResultUtils;
import com.moon.joyce.example.entity.Source;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.functionality.service.FileService;
import com.moon.joyce.example.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
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
    private final String  pagePrefix = "source/";
    @Autowired
    private SourceService sourceService;
    @Autowired
    private FileService fileService;
    /**
     * 资源页面
     * @return
     */
    @RequestMapping("/sourcePage")
    public String addSourcePage(){
      return pagePrefix+"sourcePage";
  }

    /**
     * 获取资源列表
     * @return
     */
    @ResponseBody
    @GetMapping("/getList")
    public PageVo getList(Source source){
       List<Source> list =  sourceService.getList(source);
       source.setUserId(getSessionUser().getId());
       int total = sourceService.getCount(source);
       return new PageVo(list,total);
    }

    /**
     * 保存资源
     * @return
     */
    @ResponseBody
    @PostMapping("/saveSource")
    public Result addSource(Source source){
        if (Objects.isNull(source.getId())){
            source.setCreateBy(getSessionUser().getUsername());
            source.setUserId(getSessionUser().getId());
            source.setCreateTime(new Date());
            source.setDeleteFlag(0);
        }else{
            source.setUpdateBy(getSessionUser().getUsername());
            source.setUserId(getSessionUser().getId());
            source.setUpdateTime(new Date());
        }
        boolean update = sourceService.saveOrUpdate(source);
        if (update){
            return ResultUtils.success();
        }
        return ResultUtils.error();
    }

    /**
     * 上传资源
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping("/uploadSource")
    public Result uploadSource(@RequestParam(value = "file", required = true) MultipartFile file){
        String filePath = fileService.uploadImg(file);
        return ResultUtils.success("上传成功",filePath);
    }

}

