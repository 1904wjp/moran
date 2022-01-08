package com.moon.joyce.example.controller;


import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.utils.DateUtils;
import com.moon.joyce.commons.utils.ResultUtils;
import com.moon.joyce.example.entity.ChatRecord;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.service.ChatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Joyce
 * @since 2021-09-25
 */
@Controller
@RequestMapping("/example/chatRecord")
public class ChatRecordController extends BaseController {
    @Autowired
    private ChatRecordService chatRecordService;
    /**
     * 获取所有用户的聊天记录
     * @param userBId
     * @return
     */
    @ResponseBody
    @GetMapping("/getAllRecord")
    public Result getAllRecord(@RequestParam Long userBId){
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setUserBId(userBId);
        chatRecord.setUserAId(getSessionUser().getId());
        return ResultUtils.success(chatRecordService.getAll(chatRecord));
    }
}

