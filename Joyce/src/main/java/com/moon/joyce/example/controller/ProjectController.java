package com.moon.joyce.example.controller;


import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.ResultUtils;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.entity.Project;
import com.moon.joyce.example.entity.User;
import com.moon.joyce.example.entity.base.entity.BaseEntity;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.service.ProjectService;
import com.moon.joyce.example.service.ProjectService;
import com.moon.joyce.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Joyce
 * @since 2021-09-25
 */
@Controller
@RequestMapping("/example/project")
public class ProjectController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 页面路径前缀
     */
    private final String pagePrefix = "tools/task/";
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    /**
     * 获取项目页面
     * @return
     */
    @RequestMapping("/projectListPage")
    public String getDayTaskPage(ModelMap map){
        List<User> userList = userService.getUserList(null);
        map.addAttribute("users",userList);
        return pagePrefix+"projectListPage";
    }


    /**
     * 项目信息列表
     * @param project
     * @return
     */
    @ResponseBody
    @RequestMapping("/getList")
    public PageVo getProjects(Project project){
        List<Project> list = projectService.getList(project);
        int total = projectService.getCount(project);
        return new PageVo(list,total);
    }
    /**
     * 保存项目信息
     * @param project
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveprojects")
    public Result saveprojects(Project project){
        if (Objects.isNull(project.getId())){
            project.setCreateBy(getSessionUser().getUsername());
            project.setCreateTime(new Date());
            project.setUserId(getSessionUser().getId());
        }else {
             project.setUpdateBy(getSessionUser().getUsername());
             project.setUpdateTime(new Date());
        }
        boolean result = projectService.saveOrUpdate(project);
        if (result){
            return ResultUtils.success();
        }
        return ResultUtils.error();
    }
    /**
     * 获取项目信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getProjects")
    public Result getprojects(@RequestParam Long id){
        Project project = projectService.getById(id);
        if (Objects.isNull(project)){
            return ResultUtils.error(Constant.NULL_CODE);
        }
        return ResultUtils.success(project);
    }
    /**
     * 删除项目信息
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/delProjects")
    public Result delProjects(@RequestParam String ids){
        List<String> list = StringsUtils.StrToList(ids);
        boolean result = projectService.removeByIds(list);
        if (result){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
        }

}

