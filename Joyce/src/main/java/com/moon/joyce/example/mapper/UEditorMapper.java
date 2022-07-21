package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.entity.Article;
import com.moon.joyce.example.functionality.entity.WebEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * web实体类mapper层
 */
@Mapper
public interface UEditorMapper extends BaseMapper<Article>{
    /**
     * 获取文章集合
     * @param article
     * @return
     */
    List<Article> selectList(@Param("article") Article article,@Param("idList") List<Long> idList);
    /**
     * 获取文章数量
     * @param article
     * @return
     */
    long getTotal(@Param("article") Article article,@Param("idList") List<Long> idList);
}
