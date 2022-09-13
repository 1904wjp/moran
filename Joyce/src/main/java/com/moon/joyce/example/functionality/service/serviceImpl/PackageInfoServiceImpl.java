package com.moon.joyce.example.functionality.service.serviceImpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.example.entity.doma.PackageInfo;

import com.moon.joyce.example.functionality.service.PackageInfoService;
import com.moon.joyce.example.mapper.PackageInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * @Author: XingDaoRong
 * @Date: 2021/11/24
 * 数据源service实现了
 */
@Service
public class PackageInfoServiceImpl extends ServiceImpl<PackageInfoMapper, PackageInfo> implements PackageInfoService {
   @Autowired
   private PackageInfoMapper packageInfoMapper;
    @Override
    public int updateRetire(Long userId) {
        PackageInfo aPackageInfo = new PackageInfo();
        aPackageInfo.setApplyStatus(0);
        UpdateWrapper<PackageInfo> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id",userId);
        return baseMapper.update(aPackageInfo,wrapper);
    }

    @Override
    public PackageInfo getMain(PackageInfo packageInfo) {
        QueryWrapper<PackageInfo> wrapper = new QueryWrapper<>();
        if (Objects.nonNull(packageInfo.getUserId())){
            wrapper.eq("user_id",packageInfo.getUserId());
        }
        if (StringUtils.isNoneBlank(packageInfo.getPackageName())){
            wrapper.eq("package_name",packageInfo.getPackageName());
        }
        if (StringUtils.isNoneBlank(packageInfo.getPackageValue())){
            wrapper.eq("package_value",packageInfo.getPackageValue());
        }
        wrapper.eq("apply_status",1);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<PackageInfo> getPackageList(PackageInfo packageInfo) {
        return packageInfoMapper.getList(packageInfo);
    }

    @Override
    public int getCount(PackageInfo packageInfo) {
        return packageInfoMapper.selectCount(packageInfo);
    }
}
