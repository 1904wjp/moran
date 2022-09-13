package com.moon.joyce.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.joyce.example.entity.doma.ChatRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */
@Mapper
public interface ChatRecordMapper  extends BaseMapper<ChatRecord>{
    /**
     * 获取所有对应的数据
     * @param chatRecord
     * @return
     */
    List<ChatRecord> selectAll(ChatRecord chatRecord);
}
