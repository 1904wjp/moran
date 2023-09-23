package com.moon.joyce.example.functionality.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.example.functionality.entity.doma.Logging;
import com.moon.joyce.example.functionality.service.LoggingService;
import com.moon.joyce.example.mapper.LoggingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/19
 */
@Service
public class LoggingServiceImpl extends ServiceImpl<LoggingMapper, Logging> implements LoggingService {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());
   @Autowired
   private LoggingMapper loggingMapper;
   @Override
   public List<Logging> getList(Logging logging) {
      return loggingMapper.getList(logging);
   }

   @Override
   public long getTotal(Logging logging) {
      return loggingMapper.getTotal(logging);
   }

   @Override
   public int deleteByIds(List<Long> ids) {
      return loggingMapper.deleteByIds(ids);
   }
}
