package com.chenx.gateway.web.login;

import com.chenx.model.User;
import com.chenx.service.login.LoginService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Resource(name = "loginService")
    private LoginService loginService;

    @RequestMapping("validate/{account}/{password}")
    public User validateLogin(@PathVariable String account, @PathVariable String password){
        return loginService.validateLogin(account, password);
    };


}
