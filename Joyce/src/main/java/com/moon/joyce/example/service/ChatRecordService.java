package com.moon.joyce.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.joyce.example.entity.doma.ChatRecord;

import java.util.List;

/**
 * @author Joyce
 * @since 2021-09-25
 */
public interface ChatRecordService  extends IService<ChatRecord>  {
    /**
     * 获取所有有对应的聊天数据
     * @param chatRecord
     * @return
     */
    List<ChatRecord> getAll(ChatRecord chatRecord);

}
