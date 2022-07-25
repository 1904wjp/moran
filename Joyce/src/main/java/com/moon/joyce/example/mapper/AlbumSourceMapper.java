package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.entity.Article;
import com.moon.joyce.example.functionality.entity.tundish.AlbumSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * web实体类mapper层
 */
@Mapper
public interface AlbumSourceMapper extends BaseMapper<AlbumSource>{

}
