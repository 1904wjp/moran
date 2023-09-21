package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.functionality.entity.DTO.GitInfoDTO;
import com.moon.joyce.example.functionality.entity.doma.Accounting;
import com.moon.joyce.example.functionality.entity.vo.GitInfoVO;
import com.sun.management.GcInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/07/02-- 11:15
 * @describe:
 */
@Mapper
public interface GitInfoMapper extends BaseMapper<GitInfoVO> {
    /**
     * 获取记账数据集合
     * @param gitInfoDTO
     * @return
     */
    List<GitInfoVO> getList(GitInfoDTO gitInfoDTO);

    /**
     * 获取记账数量
     * @param gitInfoDTO
     * @return
     */
    long getTotal(GitInfoDTO gitInfoDTO);


    GitInfoVO getLatestOne(GitInfoDTO gitInfoDTO);
}
