package com.moon.joyce.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.doma.Album;
import com.moon.joyce.example.entity.vo.PageVo;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/07/20-- 14:55
 * @describe:相册列表
 */
public interface AlbumService extends IService<Album> {
    /**
     * 相册分页列表
     * @param album
     * @return
     */
    PageVo getPage(Album album);
    /**
     * 解析配置
     * @param album
     * @return
     */
    Album analyAlbumConfig(Album album);
}
