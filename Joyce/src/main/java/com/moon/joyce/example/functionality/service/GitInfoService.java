package com.moon.joyce.example.functionality.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.functionality.entity.DTO.GitInfoDTO;
import com.moon.joyce.example.functionality.entity.doma.Accounting;
import com.moon.joyce.example.functionality.entity.vo.GitInfoVO;

import java.util.Date;
import java.util.List;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/07/02-- 11:09
 * @describe:
 */
public interface GitInfoService extends IService<GitInfoVO> {
    /**
     * 记帐户的数据列表
     * @param gitInfoDTO
     * @return
     */
    List<GitInfoVO> getList(GitInfoDTO gitInfoDTO);
    /**
     * 记帐户的数据数量
     * @param gitInfoDTO
     * @return
     */
    long getTotal(GitInfoDTO gitInfoDTO);


    GitInfoVO getLatestOne(GitInfoDTO gitInfoDTO);
}
