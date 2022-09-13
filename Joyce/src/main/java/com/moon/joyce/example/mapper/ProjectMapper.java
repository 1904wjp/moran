package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.entity.doma.Project;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */
public interface ProjectMapper extends BaseMapper<Project> {
    /**
     * 查找所有的项目信息集合
     * @param project
     * @return
     */
    List<Project> getList(Project project);
    /**
     * 查找所有的项目信息集合条数
     * @param project
     * @return
     */
    int getCount(Project project);
}
