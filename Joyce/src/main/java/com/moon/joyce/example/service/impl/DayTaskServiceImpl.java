package com.moon.joyce.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.commons.utils.DateUtils;
import com.moon.joyce.commons.utils.FileUtils;
import com.moon.joyce.example.entity.DayTask;
import com.moon.joyce.example.mapper.DayTaskMapper;
import com.moon.joyce.example.service.DayTaskService;
import com.sun.deploy.net.HttpResponse;
import joyce.example.entity.UserType;
import joyce.example.mapper.UserTypeMapper;
import joyce.example.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Joyce
 * @since 2021-09-25
 */
@Service
public class DayTaskServiceImpl extends ServiceImpl<DayTaskMapper, DayTask> implements DayTaskService {
    @Autowired
    private DayTaskMapper dayTaskMapper;
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
    public void exportTableByManySheet(Map<String, List<DayTask>> map, HttpServletResponse response) {
        List<String> titleList = Arrays.asList("序号","姓名","创建时间","今日任务","最终任务","完成时间","所属项目");
        Map<String, List<List<String>>> hashMap = new HashMap<>();
        for (Map.Entry<String, List<DayTask>> entry : map.entrySet()) {
            hashMap.put(entry.getKey(),getExportTableDataList(entry.getValue()));
        }
        FileUtils.exporExcel2(response,"每日看板.xlsx",titleList,hashMap);

    }

    /**
     * 需要导出的数据进行罗列
     * @param list
     * @return
     */
    private static List<List<String>> getExportTableDataList(List<DayTask> list){
        List<List<String>> dataList = new ArrayList(list.size());
        for (DayTask dayTask : list) {
            List<String> dayTaskFlies = new ArrayList<>();
            dayTaskFlies.add(dayTask.getNickname());
            dayTaskFlies.add(DateUtils.dateForMat("d",dayTask.getCreateTime()));
            dayTaskFlies.add(dayTask.getTodayTask());
            dayTaskFlies.add(dayTask.getFinalyTask());
            dayTaskFlies.add(DateUtils.dateForMat("d",dayTask.getEndTimes()));
            dayTaskFlies.add(dayTask.getProjectName());
            dataList.add(dayTaskFlies);
        }
        return dataList;
    }
}
