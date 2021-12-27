package com.moon.joyce.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.Project;
import joyce.example.entity.UserType;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
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
}
