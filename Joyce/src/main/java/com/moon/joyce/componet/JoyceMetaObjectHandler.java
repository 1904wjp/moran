package com.moon.joyce.componet;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Xing Dao Rong
 * @date 2021/9/2 8:57
 * @desc  mybatis-plus createTime和updateTime自动填充的配置
 */
@Configuration
public class JoyceMetaObjectHandler implements MetaObjectHandler {
    /**
     * 当插入数据是时，则会出发填充插入当前时间
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        String createTimeValue="createTime";
        if (metaObject.hasSetter(createTimeValue)){
            setInsertFieldValByName(createTimeValue, LocalDateTime.now(),metaObject);
        }
    }

    /**
     * 当数据发生变化时就会触发，如果updateTime为空则填充当前时间
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
            String updateTimeValue = "updateTime";
            if (metaObject.hasSetter(updateTimeValue)){
                Object updateTime = getFieldValByName(updateTimeValue, metaObject);
                if (Objects.nonNull(updateTime)){
                    setUpdateFieldValByName(updateTimeValue,updateTime,metaObject);
                }else {
                    setUpdateFieldValByName(updateTimeValue,LocalDateTime.now(),metaObject);
                }
            }
    }
}
