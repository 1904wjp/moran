package com.moon.joyce.example.controller;


import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.DateUtils;
import com.moon.joyce.commons.utils.R;
import com.moon.joyce.example.entity.DayTask;
import com.moon.joyce.example.entity.Project;
import com.moon.joyce.example.entity.User;
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

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
        setSession(getSessionUser().getUsername()+"excel",list);
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
                return R.error("非本人无法修改");
            }
           if (Objects.isNull(dayTask.getUpdateBy())){
               dayTask.setUpdateBy(getSessionUser().getUsername());
               dayTask.setUpdateTime(new Date());
           }else {
               return R.error("该数据只允许修改一次");
           }
        }
        boolean update = dayTaskService.saveOrUpdate(dayTask);
        return R.dataResult(update);
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
        return R.success(Objects.isNull(dayTask),Constant.NULL_CODE);
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
            return R.dataResult(rs,dayTask);
        }
        return R.error();
    }

    /**
     * 导出表格
     */
    @ResponseBody
    @GetMapping("/exportTasks")
    public void exportTasks(HttpServletResponse response){
        List<DayTask> list= (List<DayTask>) getSessionValue(getSessionUser().getUsername() + "excel");
        dayTaskService.exportTable(list,response);
    }

    /**
     * 导出表格
     */
    @ResponseBody
    @GetMapping("/exportTasksByManySheet")
    public void exportTasksByManySheet(HttpServletResponse response){
        List<DayTask> list= (List<DayTask>) getSessionValue(getSessionUser().getUsername() + "excel");
        Map<String, List<DayTask>> map = list.stream().collect(Collectors.groupingBy(DayTask::getNickname));
         dayTaskService.exportTableByManySheet(map,response);
    }

    /**
     * 导入表格
     * @param path
     * @return
     */
    @ResponseBody
    @PostMapping("/importExcel")
    public Result importExcel(@RequestParam String path){
        String str = dayTaskService.importDayTaskData(path);
        return R.dataResult(Objects.isNull(str),Constant.ERROR_CODE,"操作失败","操作成功",str);
    }
}








