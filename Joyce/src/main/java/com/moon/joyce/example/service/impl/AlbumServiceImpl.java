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
import java.util.stream.Collectors;

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
        qw.like("name",album.getName());
        Page<Album> page = new Page<>(album.getOffset(), album.getPageNumber());
        IPage<Album> albumIPage = baseMapper.selectPage(page, qw);
        return new PageVo(albumIPage);
    }

    @Override
    public Map<String, Source> analyAlbumConfig(String config) {

        if (StringUtils.isNoneBlank(config)){
            JSONObject jsonObject = (JSONObject) JSON.parse(config);
            List<Long> longs = StringsUtils.strListToOther(StringsUtils.StrToList(jsonObject.get("ids").toString()));
            List<Source> sources = new ArrayList<>(sourceService.listByIds(longs));
            List<String> idList = StringsUtils.StrToList(jsonObject.get("id").toString());
            List<String> siteList = StringsUtils.StrToList(jsonObject.get("site").toString());
            Map<String, Source> sourceMap = new HashMap<>();
            sources.forEach(x->sourceMap.put(x.getId().toString(),x));
            Map<String, String> siteMap = new HashMap<>();
            for (int i = 0; i < idList.size(); i++) {
                siteMap.put(idList.get(i),siteList.get(i));
            }
            Map<String, Source> map = new HashMap<>();
            for (Map.Entry<String, Source> sourceEntry : sourceMap.entrySet()) {
                for (Map.Entry<String, String> siteEntry : siteMap.entrySet()) {
                    if (sourceEntry.getKey().equals(siteEntry.getKey())){
                        map.put(siteEntry.getValue(),sourceEntry.getValue());
                    }

                }
            }
            return map;
        }
        return null;
    }
}
