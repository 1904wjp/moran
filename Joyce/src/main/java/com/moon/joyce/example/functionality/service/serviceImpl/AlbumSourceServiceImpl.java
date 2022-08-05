package com.moon.joyce.example.functionality.service.serviceImpl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.example.entity.Article;
import com.moon.joyce.example.functionality.entity.tundish.AlbumSource;
import com.moon.joyce.example.functionality.service.AlbumSourceService;
import com.moon.joyce.example.functionality.service.UEditorService;
import com.moon.joyce.example.mapper.AlbumSourceMapper;
import com.moon.joyce.example.mapper.UEditorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/24
 * web实体类service实现了
 */
@Service
public class AlbumSourceServiceImpl extends ServiceImpl<AlbumSourceMapper, AlbumSource> implements AlbumSourceService {

    @Override
    public List<AlbumSource> getList(AlbumSource albumSource) {
        QueryWrapper<AlbumSource> qw = new QueryWrapper<>();
        if (Objects.nonNull(albumSource.getSourceId())){
            qw.eq("source_id",albumSource.getSourceId());
        }
        if (Objects.nonNull(albumSource.getAlbumId())){
            qw.eq("album_id",albumSource.getAlbumId());
        }
        return baseMapper.selectList(qw);
    }
}