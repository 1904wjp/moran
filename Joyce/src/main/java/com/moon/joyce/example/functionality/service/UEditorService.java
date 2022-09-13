package com.moon.joyce.example.functionality.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.doma.Article;

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
    long getTotal(Article article,List<Long> idList);
}
