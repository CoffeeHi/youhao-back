package com.chenx.service.login;

import com.chenx.model.User;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
public interface LoginService {

    User validateLogin(String account, String password);
}
