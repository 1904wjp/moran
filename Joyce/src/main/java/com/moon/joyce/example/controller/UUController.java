package com.moon.joyce.example.controller;

import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.utils.R;
import com.moon.joyce.example.entity.doma.UU;
import com.moon.joyce.example.entity.doma.User;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.service.UUService;
import com.moon.joyce.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;
import java.util.concurrent.TimeUnit;
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
    private RedisTemplate<String,Object> redisTemplate;
    //总的请求储存
    public static String  uniqueListSum = UU.uniqueAppend+"MSGlIST_SUM";
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
        //发起请求存储返回请求人列表
        String  uniqueListSend = UU.uniqueAppend+"MSGlIST_SEND"+getSessionUserId();
        //被申请好友接收的存储列表
        String  uniqueList = UU.uniqueAppend+"MSGlIST"+getSessionUserId();
        List<UU> sendList = (List<UU>) getRedisValueOperation().get(uniqueListSend);
        List<UU> uus = (List<UU>) getRedisValueOperation().get(uniqueList);
       /* if (Objects.isNull(uus)){
            if (getExpireTime(uniqueList)<1){
                List<UU> collect = uus.stream().filter(x -> x.getResultStr().equals("0") && x.getIsSendMan().equals("1")).collect(Collectors.toList());
                boolean rs = uuService.saveBatch(collect);
                if (!rs){
                   return error("好友关系数据保存异常");
                }
                redisTemplate.delete(uniqueList);
            }
        }*/
        if (Objects.isNull(sendList) && Objects.nonNull(uus)){
            sendList = uus;
        }

        if ((Objects.isNull(uus) && Objects.isNull(sendList))||(uus.isEmpty() && sendList.isEmpty())){
           return error("暂无信息");
        }

        if (Objects.nonNull(uus) && Objects.nonNull(sendList)){
            for (UU value : uus) {
                int flag = 0;
                for (UU uu : sendList) {
                    if (uu.getUserAId().equals(value.getUserAId())
                            && uu.getUserBId().equals(value.getUserBId())) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    sendList.add(value);
                }
            }
        }
        getRedisValueOperation().set(uniqueListSum,sendList,30,TimeUnit.DAYS);
        getRedisValueOperation().set(uniqueListSend,sendList,30,TimeUnit.DAYS);
        return R.success(sendList);
    }

    /**
     * 发送添加好友请求
     * @param uu
     * @return
     */
    @ResponseBody
    @PostMapping("/addFriend")
    public Result addFriend(UU uu){
        String  uniqueList = UU.uniqueAppend+"MSGlIST"+uu.getUserBId();
        String  uniqueList2 = UU.uniqueAppend+"MSGlIST"+getSessionUserId();
        String  uniqueListSend = UU.uniqueAppend+"MSGlIST_SEND"+getSessionUserId();
        User dbBUser = userService.getById(uu.getUserBId());
        uu.setUserAId(getSessionUserId());
        uu.setResultStr("2");
        uu.setUsernameA(getSessionUser().getNickname());
        uu.setUserFileUrlA(getSessionUser().getFileUrl());
        uu.setCreateTime(new Date());
        uu.setUsernameB(dbBUser.getNickname());
        uu.setUserFileUrlB(dbBUser.getFileUrl());
        uu.setDeleteFlag(0);
        uu.setType("1");

        List<UU> list = (List<UU>)getRedisValueOperation().get(uniqueList);
        List<UU> list2 = (List<UU>)getRedisValueOperation().get(uniqueList2);
        List<UU> sendList = (List<UU>)getRedisValueOperation().get(uniqueListSend);
        List<UU> l1 = new ArrayList<>();
        if (Objects.nonNull(list)){
           l1 = list.stream().filter(x -> x.getUserAId().equals(getSessionUserId()) && x.getUserBId().equals(uu.getUserBId()) && x.getResultStr().equals("0")).collect(Collectors.toList());
        }


        if (Objects.nonNull(list2)){
             l1.addAll(list2.stream().filter(x -> x.getUserAId().equals(getSessionUserId()) && x.getUserBId().equals(uu.getUserBId()) && x.getResultStr().equals("0")).collect(Collectors.toList()));
        }

        List<UU> dbUus = uuService.getFriend(getSessionUserId(),uu.getUserBId());
        if (Objects.nonNull(dbUus)&&!dbUus.isEmpty()&&!l1.isEmpty()){
            return success("该用户已是你的好友");
        }
        uu.setIsSendMan("1");
        sendAddFriendMessage(uniqueList,list,uu);
        uu.setIsSendMan("0");
        sendAddFriendMessage(uniqueListSend,sendList,uu);
        return R.success("等待对方同意");
    }

    /**
     * 是否同意好友
     * @param isAgree 0：同意 1：拒绝
     * @return
     */
    @ResponseBody
    @RequestMapping("/agreeFriend")
    public Result agreeFriend(@RequestParam("id") Long userAId,@RequestParam("type") Integer isAgree){
          String  uniqueList = UU.uniqueAppend+"MSGlIST"+getSessionUserId();
          String  uniqueListSend = UU.uniqueAppend+"MSGlIST_SEND"+getSessionUserId();
        List<UU> uus = (List<UU>) getRedisValueOperation().get(uniqueList);
        List<UU> uuList = uus.stream().filter(x ->
                x.getUserAId().equals(userAId)
                        && x.getUserBId().equals(getSessionUserId())).collect(Collectors.toList());
        UU uu = uuList.get(0);
        uus.remove(uu);
        uu.setResultStr(isAgree.toString());
        uus.add(uu);
        long expireTime = getExpireTime(uniqueList);
        if (expireTime<1){
            expireTime = 60*60*24*30;
        }
        getRedisValueOperation().set(uniqueList,uus,expireTime,TimeUnit.SECONDS);
        List<UU> sendList = (List<UU>)getRedisValueOperation().get(uniqueListSend);
        if (Objects.nonNull(sendList)){
            for (int i = 0; i < sendList.size(); i++) {
                if (sendList.get(i).getUserAId().equals(uu.getUserAId())&&sendList.get(i).getUserBId().equals(uu.getUserBId())){
                    sendList.remove(sendList.get(i));
                    uu.setIsSendMan("0");
                    sendList.add(uu);
                }
            }
        }
        List<UU> appList = (List<UU>)getRedisValueOperation().get(uniqueList);
        if (Objects.nonNull(appList)){
            for (int i = 0; i < appList.size(); i++) {
                if (appList.get(i).getUserAId().equals(uu.getUserAId())&&appList.get(i).getUserBId().equals(uu.getUserBId())){
                    appList.remove(appList.get(i));
                    appList.add(uu);
                }
            }
        }
        boolean rs = false;
        if (isAgree==0){
            UU uu1 = new UU();
            setBaseField(uu1);
            uu1.setUserAId(userAId);
            User userA = userService.getById(userAId);
            uu1.setUsernameA(userA.getUsername());
            uu1.setUserBId(getSessionUserId());
            uu1.setUsernameB(getSessionUserName());
            rs = uuService.saveOrUpdate(uu1);
        }
        getRedisValueOperation().set(uniqueListSend,sendList,expireTime,TimeUnit.SECONDS);
        getRedisValueOperation().set(uniqueList,appList,expireTime,TimeUnit.SECONDS);
        if (isAgree==1){
           return success("已拒绝申请好友");
        }
        return R.dataResult(rs,"添加失败" ,"添加成功");
    }

    @RequestMapping("/webrtc/{id}.html")
    public ModelAndView socketChartPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/webrtc.html");
        modelAndView.addObject("id",id);
        return modelAndView;
    }



    private void sendAddFriendMessage(String listStr,List<UU> list,UU uu){
        List<UU> friendApplys = new ArrayList<>();
        long expireTime = getExpireTime(listStr);
        if (Objects.isNull(list)){
            friendApplys.add(uu);
            logger.info("建立好友申请列表");
            getRedisValueOperation().set(listStr,friendApplys,30, TimeUnit.DAYS);
        }
            friendApplys =(List<UU>) getRedisValueOperation().get(listStr);

        List<UU> uus = friendApplys.stream().filter(x ->
                x.getUserAId().equals(getSessionUserId())
                        && x.getUserBId().equals(uu.getUserBId())
                        && x.getResultStr().equals(uu.getResultStr())).collect(Collectors.toList());
        if (uus.isEmpty()){
            friendApplys.add(uu);
        }
        if (expireTime<1){
            expireTime = 60*60*24*30;
        }
        getRedisValueOperation().set(listStr,friendApplys,expireTime,TimeUnit.SECONDS);
    }

}
