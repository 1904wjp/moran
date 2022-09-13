package com.moon.joyce.example.functionality.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.doma.PackageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据源
 */
@Service
public interface PackageInfoService extends IService<PackageInfo> {
    /**
     * 将所有的包的应用状态置为0
     * @return
     */
    int updateRetire(Long userId);

    /**
     * 查询应用包
     * @param packageInfo
     * @return
     */
    PackageInfo getMain(PackageInfo packageInfo);

    /**
     * 查询所有包
     * @param packageInfo
     * @return
     */
     List<PackageInfo> getPackageList(PackageInfo packageInfo);

    /**
     * 查询所有数据的条数
     * @param packageInfo
     * @return
     */
     int getCount(PackageInfo packageInfo );

}
