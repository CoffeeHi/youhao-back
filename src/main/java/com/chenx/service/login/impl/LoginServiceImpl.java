package com.chenx.service.login.impl;

import com.chenx.dao.ILoginDao;
import com.chenx.model.User;
import com.chenx.service.login.LoginService;
import com.chenx.utils.SHA1Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Resource(name = "loginDao")
    private ILoginDao loginDao;

    @Override
    public User validateLogin(String account, String password) {
        password = SHA1Util.getSHA1(password);
        return loginDao.validateLogin(account, password);
    }
}
