package com.moon.joyce.example.service.serviceControllerDetails.SourceControllerDetailServiceImpl;

import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.entity.Source;
import com.moon.joyce.example.service.SourceService;
import com.moon.joyce.example.service.serviceControllerDetails.SourceControllerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2021/09/22-- 0:15
 * @describe: controller细节处理服务层
 */
@Service
public class SourceControllerDetailServiceImpl implements SourceControllerDetailService {
    @Autowired
    private SourceService sourceService;
    @Override
    public boolean retireApplyStatus(String type,Long userId) {
        Source source = new Source();
        source.setDeleteFlag(Constant.UNDELETE_STATUS);
        source.setApplyStatus(Constant.APPLY_STATUS);
        source.setUserId(userId);
        source.setType(type);
        Source one = sourceService.getOne(source);
        if (Objects.nonNull(one)) {
            one.setApplyStatus(Constant.SPARE_STATUS);
            return sourceService.saveOrUpdate(one);
        }
        return true;
    }

    @Override
    public boolean check(Source source) {
        Source newSource = new Source();
        newSource.setSourceName(source.getSourceName());
        newSource.setDeleteFlag(Constant.UNDELETE_STATUS);
        Source dbSource = sourceService.getOne(newSource);
        if (Objects.isNull(dbSource)){
            return true;
        }
        return false;
    }
}
