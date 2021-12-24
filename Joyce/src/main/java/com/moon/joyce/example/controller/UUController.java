package com.moon.joyce.example.controller;

import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.utils.ResultUtils;
import com.moon.joyce.example.entity.UU;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.service.UUService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/24
 * 好友关系绑定界面
 */
@Controller
@RequestMapping("/example/uu")
public class UUController extends BaseController {
    private UUService uuService;
    /**
     * 发送添加好友请求
     * @param uu
     * @return
     */
    @RequestMapping("/addFriend")
    public Result addArticleFriend(UU uu){
        uu.setUserAId(getSessionUser().getUserTypeId());
        int result = uuService.sendArticleFriendApplication(uu);
        if (result==-1){
            return ResultUtils.error("已是您的好友");
        }
        if (result==1){
            return ResultUtils.success("已发送好友请求");
        }
        return ResultUtils.error();
    }


    /**
     * 好友申请列表
     */
    @RequestMapping("/addFriendList")
    public Result addFriendList(){
        List<UU> list = uuService.getAllList(getSessionUser().getId());
        List<UU> uuList = list.stream().filter(uu ->
                !uu.getType().equals("1")
                        && !uu.getType().equals("2")
                        && !uu.getType().equals("3")
                        && !uu.getType().equals("4")
                        && !uu.getType().equals("5")
                        && !uu.getType().equals("6")
        ).collect(Collectors.toList());
        return ResultUtils.success(uuList);
    }

    /**
     * 同意好友
     * @param id
     * @return
     */
    @RequestMapping("/agreeFriend")
    public Result agreeFriend(@RequestParam Long id,boolean isAgree){
        UU dbUu = uuService.getById(id);
        if (isAgree){
            dbUu.setType("1");
            boolean result = uuService.updateById(dbUu);
            if (result){
                return ResultUtils.success("已为您添加为好友");
            }else {
                return ResultUtils.error();
            }
        }
        return ResultUtils.error("已拒绝");
    }

}
