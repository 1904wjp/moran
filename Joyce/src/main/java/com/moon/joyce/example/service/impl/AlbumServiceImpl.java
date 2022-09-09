package com.moon.joyce.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.entity.Album;
import com.moon.joyce.example.entity.Source;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.service.AlbumSourceService;
import com.moon.joyce.example.mapper.AlbumMapper;
import com.moon.joyce.example.service.AlbumService;
import com.moon.joyce.example.service.SourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/07/20-- 14:57
 * @describe:
 */
@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {
    @Autowired
    private SourceService sourceService;

    @Override
    public PageVo getPage(Album album) {
        QueryWrapper<Album> qw = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(album.getName())){
            qw.like("name",album.getName());
        }
        Page<Album> page = new Page<>(album.getOffset(), album.getPageNumber());
        IPage<Album> albumIPage = baseMapper.selectPage(page, qw);
        return new PageVo(albumIPage);
    }

    @Override
    public Album analyAlbumConfig(Album album) {
        Map<String, String> map = new HashMap<>();
        Map<String, Source> sourceMap = new HashMap<>();
        if (StringUtils.isNoneBlank(album.getSourceConfig())){
           /* JSONObject jsonObject = (JSONObject) JSON.parse(config);
            List<String> siteList = StringsUtils.StrToList(jsonObject.get("site").toString());
            QueryWrapper<AlbumSource> wrapper = new QueryWrapper<>();
            wrapper.eq("album_id",album.getId());
            List<Long> list = albumSourceService.list(wrapper).stream().map(AlbumSource::getSourceId).collect(Collectors.toList());
            List<Source> sources = new ArrayList<>(sourceService.listByIds(list));*/

            JSONObject jsonObject = (JSONObject) JSON.parse(album.getSourceConfig());
            List<Long> longs = StringsUtils.strListToOther(StringsUtils.strToList(jsonObject.get("ids").toString()));
            List<Source> sources = new ArrayList<>(sourceService.listByIds(longs));
            List<String> idList = StringsUtils.strToList(jsonObject.get("ids").toString());
            List<String> siteList = StringsUtils.strToList(jsonObject.get("site").toString());
            String bg = jsonObject.get("bg").toString();
            album.setBackGround(bg);
            for (int i = 0; i < siteList.size(); i++) {
                map.put(siteList.get(i),idList.get(i));
            }
            for (Map.Entry<String, String> entry : map.entrySet()) {
                for (Source source : sources) {
                    if (entry.getValue().equals(source.getId().toString())) {
                        sourceMap.put(entry.getKey(), source);
                    }
                }
            }
        }
        album.setMap(sourceMap);
        return album;
    }
}
