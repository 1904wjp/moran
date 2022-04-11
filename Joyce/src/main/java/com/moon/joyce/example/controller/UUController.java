package com.moon.joyce.example.controller;

import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.R;
import com.moon.joyce.example.entity.UU;
import com.moon.joyce.example.entity.User;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.service.UUService;
import com.moon.joyce.example.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/24
 * 好友关系绑定界面
 */
@Controller
@RequestMapping("/example/uu")
public class UUController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UUService uuService;
    @Autowired
    private UserService userService;
    //redis缓存
    @Autowired
    private RedisTemplate redisTemplate;

    private final String  pagePrefix = "user/";
    /**
     * 申请好友列表页面
     * @param
     * @return
     */
    @RequestMapping("/addFriendListPage")
    public String applyFriendListPage(){
        return pagePrefix+"/addFriendListPage";
    }

    /**
     * 添加好友页面好友页面
     * @param
     * @return
     */
    @RequestMapping("/searchFriendPage")
    public String searchFriendPage(){
        return pagePrefix+"/searchFriendPage";
    }

    /**
     * 申请好友列表
     * @param
     * @return
     */
    @ResponseBody
    @GetMapping("/applyFriendList")
    public Result applyFriendList(){
        String  uniqueLista = UU.uniqueAppend+"MSGlIST"+getSessionUserId();
        ListOperations<String, UU> operations = redisTemplate.opsForList();
        List<UU> list = operations.range(uniqueLista, 1, -1);
        List<UU> uus = list.stream().filter(
                x -> getSessionUserId().equals(x.getUserBId())&&null!=x.getUserAId()).collect(
                        Collectors.collectingAndThen(
                        Collectors.toCollection(
                                ()-> new TreeSet<>(Comparator.comparing(UU::getUserAId))
                        ),ArrayList::new));
        return R.success(uus);
    }

    /**
     * 发送添加好友请求
     * @param uu
     * @return
     */
    @ResponseBody
    @PostMapping("/addFriend")
    public Result addFriend(UU uu){
        String  uniqueLista = UU.uniqueAppend+"MSGlIST"+getSessionUserId();
        uu.setUserAId(getSessionUser().getUserTypeId());
        UU one = uuService.getOne(uu);
        User userB = userService.getById(uu.getUserBId());
        if (Objects.isNull(userB)){
            return R.error(Constant.NULL_CODE,Constant.CHINESE_SELECT_BLANK_USERNAME_MESSAGE);
        }
        String  uniqueListb = UU.uniqueAppend+"MSGlIST"+uu.getUserBId();
        ListOperations<String, UU> operations = redisTemplate.opsForList();
        if (Objects.nonNull(one)){
            return R.error("该用户已是你的好友");
        }
        //存入redis
        uu.setUsernameA(getSessionUserName());
        uu.setUserAId(getSessionUserId());
        uu.setCreateTime(new Date());
        uu.setUserFileUrlA(getSessionUser().getFileUrl());
        uu.setUsernameB(userB.getUsername());
        uu.setDeleteFlag(Constant.UNDELETE_STATUS);
        uu.setResultStr("1");
        operations.rightPush(uniqueLista,uu);
        operations.rightPush(uniqueListb,uu);
        logger.info("===============>",operations.range(uniqueLista,1,-1).toString());
        return R.success("等待对方同意");
    }

    /**
     * 是否同意好友
     * @param isAgree
     * @return
     */
    @ResponseBody
    @RequestMapping("/agreeFriend")
    public Result agreeFriend(@RequestParam("id") Long userAId,@RequestParam("type") Integer isAgree){ ;


            String  uniqueLista = UU.uniqueAppend+"MSGlIST"+getSessionUserId();
            ListOperations<String, UU> operations = redisTemplate.opsForList();
            List<UU> uus = operations.range(uniqueLista, 1, -1);
            for (int i = 0; i < uus.size(); i++) {
                UU tempUU = uus.get(i);
                if (userAId.equals(tempUU.getUserAId())){
                    uus.remove(tempUU);
                    tempUU.setResultStr(isAgree.toString());
                    uus.add(tempUU);
                }
            }
            redisTemplate.delete(uniqueLista);
            operations.leftPushAll(uniqueLista,uus);
            logger.info("====>",operations.leftPushAll(uniqueLista,uus),operations.range(uniqueLista, 0, -1).toString());
            return R.dataResult(isAgree==0,"已拒绝","添加成功");
    }

    @RequestMapping("/webrtc/{id}.html")
    public ModelAndView socketChartPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/webrtc.html");
        modelAndView.addObject("id",id);
        return modelAndView;
    }

}
