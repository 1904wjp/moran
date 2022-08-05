package com.moon.joyce.example.functionality.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.Article;
import com.moon.joyce.example.functionality.entity.tundish.AlbumSource;

import java.util.List;

/**
 * web实体类
 */
public interface AlbumSourceService extends IService<AlbumSource> {

    List<AlbumSource> getList(AlbumSource albumSource);
}
