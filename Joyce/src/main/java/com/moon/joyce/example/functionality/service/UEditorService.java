package com.moon.joyce.example.functionality.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.Article;
import com.moon.joyce.example.functionality.entity.WebEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * web实体类
 */
public interface UEditorService extends IService<Article> {
    /**
     * 文章集合
     * @param article
     * @return
     */
    List<Article> getList(Article article,List<Long> idList);

    /**
     * 获取文章数量
     * @param article
     * @return
     */
    int getTotal(Article article,List<Long> idList);
}
