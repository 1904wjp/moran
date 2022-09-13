package com.moon.joyce.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.joyce.commons.annotation.RedisValueComponet;
import com.moon.joyce.commons.utils.DateUtils;
import com.moon.joyce.example.entity.doma.ChatRecord;
import com.moon.joyce.example.mapper.ChatRecordMapper;
import com.moon.joyce.example.service.ChatRecordService;
import com.moon.joyce.example.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */
@Service
@RedisValueComponet("ChatRecordService")
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordMapper, ChatRecord> implements ChatRecordService , CommonService {
    @Autowired
    private ChatRecordMapper chatRecordMapper;
    @Override
    public List<ChatRecord> getAll(ChatRecord chatRecord) {
        List<ChatRecord> allRecord = chatRecordMapper.selectAll(chatRecord);
        if (allRecord.isEmpty()){
            return allRecord;
        }
        List<ChatRecord> chatRecords = new ArrayList<>();
        for (ChatRecord record : allRecord) {
            if (Objects.isNull(record)){
                continue;
            }
            record.setCreateTimeValue(DateUtils.showDate(record.getCreateTime()));
            chatRecords.add(record);
        }
        return chatRecords;
    }
}
