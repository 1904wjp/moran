package com.moon.joyce.example.controller;


import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.utils.ResultUtils;
import com.moon.joyce.example.entity.ChatRecord;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.service.ChatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @author Joyce
 * @since 2021-09-25
 */
@Controller
@RequestMapping("/example/chatRecord")
public class ChatRecordController extends BaseController {
    @Autowired
    private ChatRecordService chatRecordService;
    //redis缓存
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取所有用户的聊天记录
     * @param userBId
     * @return
     */
    @ResponseBody
    @GetMapping("/getAllRecord")
    public Result getAllRecord(@RequestParam Long userBId){
        /*List list;
        String uniqueValue = ChatRecord.uniqueAppend + userBId + getSessionUserId() + "value";
        String uniqueList = ChatRecord.uniqueAppend + userBId + getSessionUserId() + "list";
        ListOperations opsForList = redisTemplate.opsForList();
        if (Objects.isNull(redisTemplate.boundValueOps(uniqueValue).get())){
            redisTemplate.boundValueOps(uniqueValue).set(uniqueValue);
            redisTemplate.boundValueOps(uniqueValue).expire(1,TimeUnit.DAYS);
            list = chatRecordService.getAll(new ChatRecord(getSessionUser().getId(),userBId));
            opsForList.rightPushAll(uniqueList,list);
            redisTemplate.expire(uniqueList,1, TimeUnit.DAYS);
        }else{
            list = opsForList.range(uniqueList, 0, -1);
        }*/
        List<ChatRecord> list = chatRecordService.getAll(new ChatRecord(getSessionUser().getId(), userBId));
        return ResultUtils.dataResult(!list.isEmpty(),list);
    }
}

