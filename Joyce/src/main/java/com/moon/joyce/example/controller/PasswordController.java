package com.moon.joyce.example.controller;


import com.alibaba.fastjson.JSONObject;
import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.enums.RE;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.doma.Password;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.functionality.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */
@Controller
@RequestMapping("/example/password")
public class PasswordController extends BaseController {
    @Autowired
    private PasswordService passwordService;

    private final static String urlPrefix = "/example/password";

    @RequestMapping("/getListPage")
    public String getListPage(){
        return "/password/getListPage";
    }
    private Set<Long> set ;
    /**
     * 分页密码
     * @param password
     * @param pageNumber
     * @param offset
     * @return
     */
    @ResponseBody
    @GetMapping("/getPage")
    public  PageVo<Password> getList(Password password, @RequestParam int pageNumber,@RequestParam int offset){
         password.setOffset(offset);
         password.setPageNumber(pageNumber);
         password.setCreateIds(getSessionUserId());
         PageVo<Password> list = passwordService.getList(password);
        return list;
    }

    /**
     * 获取一条详细信息
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/{id}")
    public Result getOne(@PathVariable Long id){
        Password password = passwordService.getById(id);
        if (password==null){
            return error("该数据不存在");
        }
        password.setPassword(StringsUtils.decryptPassword(password.getPassword()));
        return getResult(RE.SELECT,password);
    }


    /**
     * 查询二级密码
     * @param twoPassword
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/check")
    public Result check(@RequestParam String twoPassword,@RequestParam Long id){
        String key = getSessionUserId()+"twoPassword";
        Password password = passwordService.getById(id);
        if (StringsUtils.isBlank(twoPassword)){
            return error("密码不能为空");
        }
        if (password==null){
           return dataResult(false,"数据不存在");
        }
        if (StringsUtils.isNoneBlank(password.getTwoPassword())){
            boolean rs = password.getTwoPassword().equals(StringsUtils.encryptionPassword(twoPassword));
            if (rs){
                Object sessionValue = getSessionValue(key);
                if (sessionValue==null){
                  set = new HashSet<>();
                }else {
                    set = (Set<Long>) sessionValue;
                }
                set.add(password.getId());
                setSession(key,set);
            }
          return dataResult(rs,"密码验证失败","密码验证成功");
        }
        return error();
    }

    /**
     * 判断二次密码是否输入过
     * @return
     */
    @ResponseBody
    @GetMapping("/checkTwoPasswordIsEx/{id}")
    public Result  checkTwoPasswordIsEx(@PathVariable Long id){
        String key = getSessionUserId()+"twoPassword";
        Object sessionValue = getSessionValue(key);
        if (sessionValue!=null){
            if (((Set<Long>) sessionValue).contains(id)){
                return dataResult(true);
            }
        }
        return dataResult(false);
    }

    /**
     * 保存一条数据
     * @param password
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Password password){
        String key = getSessionUserId()+"twoPassword";
        setBaseField(password);
        int count = passwordService.getCount(password);
        password.setIsNewV(1);
        int version = 0;
        if (count!=0){
            Password dbPwd = passwordService.getOne(password);
            if (Objects.nonNull(dbPwd)){
                dbPwd.setIsNewV(0);
                version = dbPwd.getVersion()+1;
                passwordService.saveOrUpdate(dbPwd);
            }
        }
        password.setVersion(version);
        StringBuilder pwd = new StringBuilder(password.getPassword());
        char[] chars = pwd.toString().toCharArray();
        pwd = new StringBuilder();
        for (char aChar : chars) {
            pwd.append((char) (aChar + 1));
        }
        password.setPassword(pwd.toString());
        if (StringsUtils.isNoneBlank(password.getTwoPassword())){
            password.setTwoPassword(StringsUtils.encryptionPassword(password.getTwoPassword()));
        }
        boolean b = passwordService.saveOrUpdate(password);
        Object sessionValue = getSessionValue(key);
        if (sessionValue==null){
            set = new HashSet<>();
        }else {
            set = (Set<Long>) sessionValue;
        }
        set.add(password.getId());
        setSession(key,set);
        loggingService.save(getLogging(b,"保存密码", JSONObject.toJSONString(password),urlPrefix+"/save"));
        return getResult(b, RE.ADD);
    }
}

