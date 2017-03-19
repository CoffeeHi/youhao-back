package com.chenx.gateway.web.login;

import com.chenx.gateway.commons.BasicController;
import com.chenx.service.login.ILoginService;
import com.chenx.utils.dto.SessionInfo;
import com.fjhb.commons.exception.BasicRuntimeException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
@RestController
@RequestMapping("login")
public class LoginController extends BasicController{

    @Resource(name = "loginService")
    private ILoginService loginService;

    @RequestMapping(value = "front/{account}/{password}", method = RequestMethod.GET)
    public SessionInfo frontLogin(@PathVariable String account, @PathVariable String password){
        return loginService.validateLogin(account, password, request.getSession().getId());
    }

    @RequestMapping(value = "frontCheck", method = RequestMethod.GET)
    public SessionInfo CheckLogin(){
        String userId = "";
        for (Cookie ck : request.getCookies()){
            if(ck.getName().equalsIgnoreCase("userId")){
                userId = ck.getValue();
                break;
            }
        }
        if (StringUtils.isEmpty(userId))
            throw new BasicRuntimeException("没有保存cookie");
        return loginService.checkLogin(userId, request.getRequestedSessionId());
    }

    @RequestMapping(value = "frontExit", method = RequestMethod.DELETE)
    public boolean frontExit(){
        return loginService.exitLogin(request.getRequestedSessionId());
    }
}
