package com.moon.joyce.example.functionality.service.serviceImpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.entity.dto.Page;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.doma.Password;
import com.moon.joyce.example.functionality.entity.doma.WebEntity;
import com.moon.joyce.example.functionality.service.PasswordService;
import com.moon.joyce.example.functionality.service.WebEntityService;
import com.moon.joyce.example.mapper.PasswordMapper;
import com.moon.joyce.example.mapper.WebEntityMapper;
import io.jsonwebtoken.lang.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/24
 * web实体类service实现了
 */
@Service
public class PasswordServiceImpl extends ServiceImpl<PasswordMapper, Password> implements PasswordService {

    @Override
    public  PageVo getList(Password password) {
        QueryWrapper<Password> queryWrapper = new QueryWrapper<>();
        if (StringsUtils.isNoneBlank(password.getSearchWord())){
            queryWrapper.lambda().like(Password::getRemark,password.getSearchWord())
                    .or().like(Password::getUsername,password.getSearchWord())
                    .or().like(Password::getAppName,password.getSearchWord());
        }
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Password> page = Page.getPage(password);
        IPage iPage = baseMapper.selectPage(page, queryWrapper);
        return new PageVo(iPage);
    }

    @Override
    public int getCount(Password password) {
        QueryWrapper<Password> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Password::getUsername,password.getUsername());
        queryWrapper.lambda().eq(Password::getAppName,password.getAppName());
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public Password getOne(Password password) {
        QueryWrapper<Password> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Password::getUsername,password.getUsername());
        queryWrapper.lambda().eq(Password::getAppName,password.getAppName());
        queryWrapper.lambda().eq(Password::getIsNewV,1);
        return baseMapper.selectOne(queryWrapper);
    }
}
