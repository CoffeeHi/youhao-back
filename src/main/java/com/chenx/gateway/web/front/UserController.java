package com.chenx.gateway.web.front;

import com.chenx.gateway.commons.BasicController;
import com.chenx.model.UserInfo;
import com.chenx.model.dto.EditUserInfo;
import com.chenx.service.front.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
@RestController
@RequestMapping("user")
public class UserController extends BasicController{

    @Resource(name = "userService")
    private IUserService userService;

    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    public int edit(@RequestBody EditUserInfo editUserInfo){
        return userService.editUserInfo(editUserInfo, request.getRequestedSessionId());
    }

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public UserInfo detail(){
        return userService.getUserInfo(request.getRequestedSessionId());
    }
}
