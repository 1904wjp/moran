package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.entity.PackageInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PackageInfoMapper extends BaseMapper<PackageInfo> {
     /**
      * 包所有数据且分页
      * @param packageInfo
      * @return
      */
     List<PackageInfo> getList(PackageInfo packageInfo );

    /**
     * 包所有数据的数目
     * @param packageInfo
     * @return
     */
     int selectCount(PackageInfo packageInfo);
}
