package com.chenx.dao.impl;

import com.chenx.dao.ILoginDao;
import com.chenx.model.User;
import com.fjhb.mybatis.dao.BaseMybatisDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
@Repository("loginDao")
public class LoginDaoImpl extends BaseMybatisDaoImpl<User, String> implements ILoginDao{

    public User validateLogin(String account, String password){
        Map param = new HashMap<>();
        param.put("account", account);
        param.put("password", password);
        return this.getSqlSession().selectOne("validateLogin", param);
    }
}
