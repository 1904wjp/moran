package com.moon.joyce.example.controller;


import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.enums.RE;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.doma.PayConfig;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.functionality.service.PayConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */
@RestController
@RequestMapping("/example/payConfig")
public class PayConfigController extends BaseController {
    @Autowired
    private PayConfigService payConfigService;
    @RequestMapping("/payConfigListPage")
    public String payConfigListPage(){
        return "";
    }
    @RequestMapping("/payConfigPage")
    public String payConfigPage(){
        return "";
    }
    @ResponseBody
    @RequestMapping("/getPayConfigList")
    public PageVo<PayConfig> getPayConfigList(PayConfig payConfig){
       return payConfigService.getPage(payConfig);
    }
    @ResponseBody
    @RequestMapping("/{id}")
    public Result getById(@PathVariable Long id){
        PayConfig config = null;
        try {
             config = payConfigService.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return dataResult(false,RE.SELECT,null,1);
        }
        return dataResult(true,RE.SELECT,null,1);
    }
    @ResponseBody
    @RequestMapping("/update")
    public Result getById(PayConfig payConfig){
        setBaseField(payConfig);
        boolean rs = payConfigService.saveOrUpdate(payConfig);
        return dataResult(rs, RE.ADDORUPDATE.getCode(),payConfig);
    }
    @ResponseBody
    @RequestMapping("/del")
    public Result del(String ids){
        List<String> list = StringsUtils.strToList(ids);
        boolean b = payConfigService.removeByIds(list);
        return dataResult(b,RE.DELETE,null,1);
    }

    /**
     * 设置使用的payConfig
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/set")
    public Result set(Long id){
        PayConfig payConfig = new PayConfig();
        payConfig.setCreateIds(getSessionUserId());
        payConfig.setDeleteFlag(Constant.UNDELETE_STATUS);
        payConfig.setStatus(PayConfig.usedStatus);
        PayConfig one = payConfigService.getOne(payConfig);
        boolean rs1 = true;
        if (Objects.nonNull(one)){
            one.setStatus(PayConfig.unusedStatus);
            rs1 = payConfigService.saveOrUpdate(one);
        }
        PayConfig payConfig1 = payConfigService.getById(id);
        payConfig1.setStatus(PayConfig.usedStatus);
        boolean rs2 = payConfigService.saveOrUpdate(payConfig1);
        return dataResult(rs1&&rs2,RE.UPDATE,null,1);
    }
}

