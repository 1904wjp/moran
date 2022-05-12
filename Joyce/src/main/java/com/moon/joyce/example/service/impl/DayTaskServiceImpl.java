package com.moon.joyce.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.commons.annotation.RedisValueComponet;
import com.moon.joyce.commons.utils.DateUtils;
import com.moon.joyce.commons.utils.FileUtils;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.entity.DayTask;
import com.moon.joyce.example.entity.Project;
import com.moon.joyce.example.entity.User;
import com.moon.joyce.example.mapper.DayTaskMapper;
import com.moon.joyce.example.service.CommonService;
import com.moon.joyce.example.service.DayTaskService;
import com.moon.joyce.example.service.ProjectService;
import com.moon.joyce.example.service.UserService;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Joyce
 * @since 2021-09-25
 */
@Service
@RedisValueComponet("DayTaskService")
public class DayTaskServiceImpl extends ServiceImpl<DayTaskMapper, DayTask> implements DayTaskService , CommonService {
    @Autowired
    private DayTaskMapper dayTaskMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Override
    public List<DayTask> getList(DayTask dayTask) {
        return dayTaskMapper.selectList(dayTask);
    }

    @Override
    public int getCount(DayTask dayTask) {
        return dayTaskMapper.selectCount(dayTask);
    }

    @Override
    public DayTask getLastData(Long id) {
        return dayTaskMapper.getLastData(id);
    }

    @Override
    public void exportTable(List<DayTask> list, HttpServletResponse response) {
        List<String> titleList = Arrays.asList("序号","姓名","创建时间","今日任务","最终任务","完成时间","所属项目");
        List<List<String>> dataList = getExportTableDataList(list);
        FileUtils.exporExcel(response,"每日看板.xlsx",titleList,dataList,null);
    }

    @Override
    public void exportTableByManySheet(Map<Date, List<DayTask>> map, HttpServletResponse response) {
        List<String> titleList = Arrays.asList("序号","创建时间","完成时间","加班时常");
        Map<Date, List<List<String>>> hashMap = new HashMap<>();
        for (Map.Entry<Date, List<DayTask>> entry : map.entrySet()) {
            hashMap.put(entry.getKey(),getExportTableDataList(entry.getValue()));
        }
        FileUtils.exporExcel2(response,"加班表.xlsx",titleList,hashMap);

    }

    @Override
    public String importDayTaskData(String path) {
        ArrayList<DayTask> dayTasks = new ArrayList<>();
        File file = new File(path);
        Workbook workbok = FileUtils.getWorkbok(file);
        for (int numSheet = 0; numSheet < workbok.getNumberOfSheets(); numSheet++) {
            Sheet sheetAt = workbok.getSheetAt(numSheet);
            if (Objects.isNull(sheetAt)){
                continue;
            }
            String name = FileUtils.getCellValueOfTrim(sheetAt.getRow(0).getCell(0));
            User user = new User();
            user.setNickname(name);
            User dbUser = userService.getUser(user, "");
            for (int i = 2; i < sheetAt.getLastRowNum(); i++) {
                Row row = sheetAt.getRow(i);
                DayTask dayTask = new DayTask();
                String projectName = FileUtils.getCellValueOfTrim(row.getCell(0));
                Project dbProject = projectService.getOne(projectName);
                if (Objects.nonNull(dbProject)){
                    dayTask.setProjectName(projectName);
                    dayTask.setProjectId(dbProject.getId());
                }
                dayTask.setUserId(dbUser.getId());
                dayTask.setStartTime(StringsUtils.StringToDate(FileUtils.getCellValueOfTrim(row.getCell(1))));
                dayTask.setTodayTask(FileUtils.getCellValueOfTrim(row.getCell(2)));
                dayTask.setEndTimes(StringsUtils.StringToDate(FileUtils.getCellValueOfTrim(row.getCell(3))));
                dayTask.setFinallyTask(FileUtils.getCellValueOfTrim(row.getCell(4)));
                dayTasks.add(dayTask);
            }
        }
        String str ="";
        for (DayTask dayTask : dayTasks) {
            if (Objects.isNull(dayTask)){
                continue;
            }
            int rs = baseMapper.insert(dayTask);
            if (rs==0){
                str=str+dayTask.getStartTime()+":"+dayTask.getNickname()+"的"+dayTask.getTodayTask()+"任务插入失败";
            }
        }
        return str;
    }

    /**
     * 需要导出的数据进行罗列
     * @param list
     * @return
     */
    private static List<List<String>> getExportTableDataList(List<DayTask> list){


        /*序号","创建时间","完成时间","加班时常"*/
        List<List<String>> dataList = new ArrayList(list.size());
        for (DayTask dayTask : list) {
            List<String> dayTaskFlies = new ArrayList<>();
            //dayTaskFlies.add(dayTask.getNickname());
            dayTaskFlies.add(DateUtils.dateForMat("d",dayTask.getStartTime()));
            //dayTaskFlies.add(dayTask.getTodayTask());
            //dayTaskFlies.add(dayTask.getFinalyTask());
            dayTaskFlies.add(DateUtils.dateForMat("d",dayTask.getEndTimes()));
            dayTaskFlies.add(dayTask.getTime());
            //dayTaskFlies.add(dayTask.getProjectName());
            dataList.add(dayTaskFlies);
        }
        return dataList;
    }
}
