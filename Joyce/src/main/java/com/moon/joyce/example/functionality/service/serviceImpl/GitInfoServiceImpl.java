package com.moon.joyce.example.functionality.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.example.functionality.entity.DTO.GitInfoDTO;
import com.moon.joyce.example.functionality.entity.doma.Accounting;
import com.moon.joyce.example.functionality.entity.vo.GitInfoVO;
import com.moon.joyce.example.functionality.service.AccountingService;
import com.moon.joyce.example.functionality.service.GitInfoService;
import com.moon.joyce.example.mapper.AccountingMapper;
import com.moon.joyce.example.mapper.GitInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/07/02-- 11:15
 * @describe:
 */
@Service
public class GitInfoServiceImpl extends ServiceImpl<GitInfoMapper, GitInfoVO> implements GitInfoService {
    @Autowired
    private GitInfoMapper gitInfoMapper;


    @Override
    public List<GitInfoVO> getList(GitInfoDTO gitInfoDTO) {
        //return gitInfoMapper.getList(gitInfoDTO);
        return null;
    }

    @Override
    public long getTotal(GitInfoDTO gitInfoDTO) {
        //return gitInfoMapper.getTotal(gitInfoDTO);
        return 0;
    }

    @Override
    public GitInfoVO getLatestOne(GitInfoDTO gitInfoDTO) {
       // return gitInfoMapper.getLatestOne(gitInfoDTO);
        return null;
    }

}