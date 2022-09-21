package com.moon.joyce.example.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.enums.RE;
import com.moon.joyce.commons.utils.FileUtils;
import com.moon.joyce.commons.utils.MD5Utils;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.doma.PayConfig;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.functionality.service.PayConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
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
    @Value("${file.config.path}")
    private String filePath;
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
             config.setPrivateKey(null);
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(false,RE.SELECT);
        }
        return getResult(true,RE.SELECT,config);
    }

    @ResponseBody
    @RequestMapping("/save")
    public Result getById(PayConfig payConfig){
        setBaseField(payConfig);
        if (Objects.isNull(payConfig.getId())){
            payConfig.setPrivateKeyPassword(MD5Utils.getMD5Str(payConfig.getPrivateKey()));
        }
        filePath = filePath + "/" + getSessionUserId() +"/"+ "privateKey.json";
        FileUtils.createNewFile(true,filePath, false);
        String text = "{\"key\":\""+payConfig.getPrivateKey()+"\"}";
        FileUtils.writeFile(filePath,text);
        payConfig.setPrivateKey(filePath);
        boolean rs = payConfigService.saveOrUpdate(payConfig);
        return getResult(rs, RE.ADDORUPDATE,payConfig);
    }

    @ResponseBody
    @RequestMapping("/privateKey")
    public Result getById(@RequestParam("password") String password,@RequestParam("id") Long id){
        PayConfig payConfig = payConfigService.getById(id);
        String text = "";
        if (password.equals(payConfig.getPrivateKeyPassword())){
           if (StringsUtils.isBlank(payConfig.getPrivateKey())){
               File file = FileUtils.createFile(payConfig.getPrivateKey());
               try {
                   text = FileUtils.readFileToString(file, "utf-8");
               } catch (IOException e) {
                   e.printStackTrace();
               }
               JSONObject jsonObject = JSON.parseObject(text);
               text = jsonObject.get("key").toString();
           }
        }else {
            return error("密码错误");
        }
        return getResult(true, RE.SELECT,text);
    }

    @ResponseBody
    @RequestMapping("/del")
    public Result del(String ids){
        List<String> list = StringsUtils.strToList(ids);
        boolean b = payConfigService.removeByIds(list);
        return getResult(b,RE.DELETE);
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
        return getResult(rs1&&rs2,RE.UPDATE);
    }
}

