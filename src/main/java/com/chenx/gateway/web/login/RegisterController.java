package com.chenx.gateway.web.login;

import com.chenx.service.login.IRegisterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
@RestController
@RequestMapping("register")
public class RegisterController {

    @Resource(name = "registerService")
    private IRegisterService registerService;

    @RequestMapping(value = "account/{account}/{password}/{type}", method = RequestMethod.POST)
    public int validateLogin(@PathVariable String account, @PathVariable String password, @PathVariable int type){
        return registerService.register(account, password, type);
    }


}
