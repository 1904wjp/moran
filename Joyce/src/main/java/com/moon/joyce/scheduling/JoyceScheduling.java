package com.moon.joyce.scheduling;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.example.functionality.entity.doma.Logging;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时器
 */
@Component
public class JoyceScheduling extends BaseController {

    @Scheduled(cron="0 10 0 1 * ?")
    private void test(){
        QueryWrapper<Logging> wrapper = new QueryWrapper<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH,-1);
        Date time = instance.getTime();
        String format = sdf.format(time);
        wrapper.apply("date_format(create_time,'%Y-%m-%d')<'"+format+"'");
        List<Logging> list = loggingService.list(wrapper);
        List<Long> ids = list.stream().map(Logging::getId).collect(Collectors.toList());
        if (!ids.isEmpty()){
            loggingService.deleteByIds(ids);
        }
        //loggingService.save(getLogging("删除成功","ids",""));
        System.err.println("删除成功  "+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
    }
}
