package com.moon.joyce.example.controller;


import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
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
       source.setUserId(getSessionUser().getId());
       List<Source> list =  sourceService.getList(source);
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
        System.out.println("====>"+source.toString());
        if (source.getApplyStatus().equals(Constant.APPLY_STATUS)){
            boolean b = retireApplyStatus();
            if (!b){
                return ResultUtils.error();
            }
        }
        Source source1 = new Source();
        source1.setSourceName(source.getSourceName());
        source1.setUserId(getSessionUserId());
        Source one = sourceService.getOne(source1);
        if (Objects.nonNull(one)){
            return ResultUtils.error("资源名已存在，请修改资源名重新上传");
        }
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
        boolean rs = sourceService.saveOrUpdate(source);
        return ResultUtils.dataResult(rs);
    }

    /**
     * 保存资源
     * @return
     */
    @ResponseBody
    @PostMapping("/setMainSource")
    public Result addSource(@RequestParam("id") Long id){
        Source source = sourceService.getById(id);
        if (Objects.isNull(source)){
            return ResultUtils.error(Constant.NULL_CODE);
        }
        source.setUpdateBy(getSessionUser().getUsername());
        source.setUpdateTime(new Date());
        boolean b = retireApplyStatus();
        if (!b){
            return ResultUtils.error();
        }
        source.setApplyStatus(Constant.APPLY_STATUS);
        boolean rs = sourceService.saveOrUpdate(source);
        return ResultUtils.dataResult(rs,"修改失败","修改成功",source);
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

    public boolean retireApplyStatus(){
        Source source1 = new Source();
        source1.setDeleteFlag(Constant.UNDELETE_STATUS);
        source1.setApplyStatus(Constant.APPLY_STATUS);
        source1.setUserId(getSessionUserId());
        Source one = sourceService.getOne(source1);
        if (Objects.nonNull(one)){
            one.setApplyStatus(Constant.SPARE_STATUS);
            return sourceService.saveOrUpdate(one);
        }
        return  true;
    }
}

