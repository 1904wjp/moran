package com.moon.joyce.example.controller;


import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.enums.RE;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.doma.Password;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.functionality.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Objects;

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

    @RequestMapping("/getListPage")
    public String getListPage(){
        return "/password/getListPage";
    }

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
        StringBuilder pwd = new StringBuilder(password.getPassword());
        char[] chars = pwd.toString().toCharArray();
        pwd = new StringBuilder();
        for (char aChar : chars) {
            pwd.append((char) (aChar - 1));
        }
        password.setPassword(pwd.toString());
        return getResult(RE.SELECT,password);
    }

    /**
     * 保存一条数据
     * @param password
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Password password){
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
        boolean b = passwordService.saveOrUpdate(password);
        return getResult(b, RE.ADD);
    }
}

