package com.chenx.service.login;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
public interface IRegisterService {

    /**
     * 用户注册
     * @param account
     * @param password
     * @param type 注册账号类型 1-邮箱 2-手机号
     * @return
     */
    int register(String account, String password, int type);
}
