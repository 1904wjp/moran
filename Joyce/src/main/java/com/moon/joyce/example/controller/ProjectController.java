package com.moon.joyce.example.controller;


import com.moon.joyce.commons.annotation.url.MethodUrl;
import com.moon.joyce.commons.annotation.url.UriPri;
import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.R;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.entity.doma.Project;
import com.moon.joyce.example.entity.doma.User;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.functionality.entity.doma.Uri;
import com.moon.joyce.example.service.ProjectService;
import com.moon.joyce.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Joyce
 * @since 2021-09-25
 */
@Controller
@UriPri(name = "项目接口路径",pri = "/example/project")
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
    //@MethodUrl(name = "获取项目页面",url = "/projectListPage")
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
    //@MethodUrl(name = "获取全部项目信息路径",url = "/getList")
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
    //@MethodUrl(name = "保存项目信息",url = "/saveProjects")
    @RequestMapping("/saveProjects")
    public Result saveProjects(Project project){
        if (Objects.isNull(project.getId())){
            project.setCreateBy(getSessionUser().getUsername());
            project.setCreateTime(new Date());
            project.setUserId(getSessionUser().getId());
        }else {
             project.setUpdateBy(getSessionUser().getUsername());
             project.setUpdateTime(new Date());
        }
        boolean result = projectService.saveOrUpdate(project);
        return R.dataResult(result);
    }

    /**
     * 获取项目信息
     * @param id
     * @return
     */
    @ResponseBody
    //@MethodUrl(name = "获取项目信息",url = "/getProjects")
    @RequestMapping("/getProjects")
    public Result getprojects(@RequestParam Long id){
        Project project = projectService.getById(id);
        if (Objects.isNull(project)){
            return R.error(Constant.NULL_CODE);
        }
        return R.dataResult(Objects.isNull(project),Constant.NULL_CODE,project);
    }

    /**
     * 删除项目信息
     * @param ids
     * @return
     */
    @ResponseBody
    //@MethodUrl(name = "删除项目信息",url = "/delProjects")
    @RequestMapping("/delProjects")
    public Result delProjects(@RequestParam String ids) {
        List<String> list = StringsUtils.strToList(ids);
        boolean result = projectService.removeByIds(list);
        return R.dataResult(result, "删除失败", "删除成功");
    }

    /**
     * 根据名称查询接口
     * @return
     */
    @ResponseBody
    //@MethodUrl(name = "根据名称查询接口",url = "/getUri/{key}")
    @RequestMapping("/getUri/{key}")
    public Result getProjectUri(@PathVariable String key){
        return R.dataResult(!getMap().isEmpty(),getMap().get(key));
    }

    /**
     * 所有接口
     * @return
     */
    @ResponseBody
    //@MethodUrl(name = "所有接口",url = "/getUri")
    @RequestMapping("/getUri")
    public Result getProjectUriMap(){
        return R.dataResult(!getMap().isEmpty(),getMap());
    }

}

