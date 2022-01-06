package com.moon.joyce.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.example.entity.Project;
import com.moon.joyce.example.mapper.ProjectMapper;
import com.moon.joyce.example.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    @Override
    public List<Project> getList(Project project) {
        return projectMapper.getList(project);
    }

    @Override
    public int getCount(Project project) {
        return projectMapper.getCount(project);
    }

    @Override
    public Project getOne(String projectName) {
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(projectName)){
            wrapper.eq("project_name",projectName);
        }
        return baseMapper.selectOne(wrapper);
    }
}
