package com.moon.joyce.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.doma.Project;


import java.util.List;

/**
 * @author Joyce
 * @since 2021-09-25
 */

public interface ProjectService extends IService<Project> {
    /**
     * 获取项目集合
     * @param project
     * @return
     */
    List<Project> getList(Project project);

    /**
     * 获取项目集合条数
     * @param project
     * @return
     */
    int getCount(Project project);

    /**
     * 根据名称获取项目
     * @param projectName
     * @return
     */
    Project getOne(String projectName);
}
