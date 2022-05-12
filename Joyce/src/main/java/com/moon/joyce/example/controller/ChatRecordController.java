package com.moon.joyce.example.controller;


import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.utils.R;
import com.moon.joyce.example.entity.ChatRecord;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.service.ChatRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ListUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author Joyce
 * @since 2021-09-25
 */
@Controller
@RequestMapping("/example/chatRecord")
public class ChatRecordController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ChatRecordService chatRecordService;
    //redis缓存
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    /**
     * 获取所有用户的聊天记录
     * @param userBId
     * @return
     */
    @ResponseBody
    @GetMapping("/getAllRecord")
    public Result getAllRecord(@RequestParam Long userBId){

        String r_chatRecords = "chatRecords"+getSessionUserId()+userBId;
        String r_chatRecords_list = "chatRecordsList"+getSessionUserId()+userBId;
        List<ChatRecord> chatRecords = (List<ChatRecord>)redisTemplate.opsForValue().get(addChatRecords);

        if (redisTemplate.opsForValue().getOperations().getExpire(r_chatRecords_list)>0
                && Objects.nonNull(redisTemplate.opsForValue().get(r_chatRecords_list))
                && (Objects.isNull(chatRecords) || chatRecords.isEmpty())){
            return success(redisTemplate.opsForValue().get(r_chatRecords_list));
        }

        List<ChatRecord> rChatRecords = (List<ChatRecord>)redisTemplate.opsForValue().get(r_chatRecords);
        if (Objects.isNull(rChatRecords)){
            rChatRecords=chatRecordService.getAll(new ChatRecord(getSessionUser().getId(), userBId));
            redisTemplate.opsForValue().set(r_chatRecords,rChatRecords,24, TimeUnit.HOURS);
            if (rChatRecords.isEmpty()){
                return success(rChatRecords);
            }
        }

        if (Objects.nonNull(chatRecords)){
            List<ChatRecord> finalRChatRecords =  (List<ChatRecord>)redisTemplate.opsForValue().get(r_chatRecords);
            chatRecords = chatRecords.stream().filter(x -> !ListUtils.contains(finalRChatRecords, x) && (
                    (x.getUserAId().equals(getSessionUserId()) && (x.getUserBId().equals(userBId))) || (
                            (x.getUserAId().equals(userBId)) && (x.getUserBId().equals(getSessionUserId())))
            )).collect(Collectors.toList());
            for (ChatRecord chatRecord : chatRecords) {
                  rChatRecords.add(chatRecord);
            }
            getRedisValueOperation().set(r_chatRecords_list,rChatRecords);
        }

        return R.dataResult(!rChatRecords.isEmpty(),rChatRecords);
    }
}

