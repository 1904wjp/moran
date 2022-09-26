package com.moon.joyce.example.controller;


import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.example.functionality.entity.doma.Password;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.functionality.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */
@RestController
@RequestMapping("/example/password")
public class PasswordController extends BaseController {
    @Autowired
    private PasswordService passwordService;
    public Result<List<Password>> getList(Password password){
         password.setCreateIds(getSessionUserId());
         List<Password> passwords = passwordService.getList(password);
        return success(password);
    }
}

