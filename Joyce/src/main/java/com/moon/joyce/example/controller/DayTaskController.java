package com.moon.joyce.example.controller;


import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.DateUtils;
import com.moon.joyce.commons.utils.ResultUtils;
import com.moon.joyce.example.entity.DayTask;
import com.moon.joyce.example.entity.Project;
import com.moon.joyce.example.entity.User;
import com.moon.joyce.example.entity.base.entity.BaseEntity;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.service.DayTaskService;
import com.moon.joyce.example.service.ProjectService;
import com.moon.joyce.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Joyce
 * @since 2021-09-25
 */
@Controller
@RequestMapping("/example/dayTask")
public class DayTaskController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 页面路径前缀
     */
    private final String pagePrefix = "tools/task/";
    @Autowired
    private ProjectService projectService;
    @Autowired
    private DayTaskService dayTaskService;
    @Autowired
    private UserService userService;

    /**
     * 获取用户页面
     * @return
     */
    @RequestMapping("/dayTaskListPage")
    public String getDayTaskPage(ModelMap map){
        List<User> userList = userService.getUserList(null);
        List<Project> projectList = projectService.getList(null);
        map.addAttribute("users",userList);
        map.addAttribute("projects",projectList);
        return pagePrefix+"dayTaskListPage";
    }

    /**
     * 每日任务列表
     * @param dayTask
     * @return
     */
    @ResponseBody
    @RequestMapping("/getList")
    public PageVo getDayTasks(DayTask dayTask){
        List<DayTask> list = dayTaskService.getList(dayTask);
        int total = dayTaskService.getCount(dayTask);
        return new PageVo(list,total);
    }

    /**
     * 保存每日任务
     * @param dayTask
     * @return
     */
    @ResponseBody
    @PostMapping("/saveTasks")
    public Result saveTasks(DayTask dayTask){
        dayTask.setUserId(getSessionUser().getId());
        dayTask.setProjectName(projectService.getById(dayTask.getProjectId()).getProjectName());
        if (Objects.isNull(dayTask.getId())){
            dayTask.setCreateBy(getSessionUser().getUsername());
            dayTask.setCreateTime(new Date());
            dayTask.setUserId(getSessionUser().getId());
            dayTask.setDeleteFlag(0);
        }else {
            if (!getSessionUser().getId().equals(dayTask.getUserId())){
                return ResultUtils.error("非本人无法修改");
            }
           if (Objects.isNull(dayTask.getUpdateBy())){
               dayTask.setUpdateBy(getSessionUser().getUsername());
               dayTask.setUpdateTime(new Date());
           }else {
               return ResultUtils.error("该数据只允许修改一次");
           }
        }
        boolean update = dayTaskService.saveOrUpdate(dayTask);
        if (update){
            return ResultUtils.success();
        }
        return ResultUtils.error();
    }

    /**
     * 获取每日任务
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/getTasks")
    public Result getTasks(@RequestParam Long id){
        DayTask dayTask = dayTaskService.getById(id);
        if (Objects.isNull(dayTask)){
            return ResultUtils.error(Constant.NULL_CODE);
        }
        return ResultUtils.success(dayTask);
    }

    /**
     * 判断日期
     */
    @ResponseBody
    @GetMapping("/justTasks")
    public Result justTasks(){
        DayTask dayTask = dayTaskService.getLastData(getSessionUser().getId());
        if (Objects.nonNull(dayTask)){
            boolean rs = DateUtils.dateCompare(new Date(), dayTask.getEndTimes(), 0l, "yyyy-MM-dd");
            if (rs){
                return ResultUtils.success(dayTask);
            }
        }
        return ResultUtils.error();
    }
}

