package com.chenx.service.login.impl;

import com.chenx.YouHaoConstant;
import com.chenx.dao.RegisterDao;
import com.chenx.model.Account;
import com.chenx.model.User;
import com.chenx.service.login.IRegisterService;
import com.chenx.utils.SHA1Util;
import com.chenx.utils.UUIDUtils;
import com.fjhb.commons.exception.BasicRuntimeException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

@Service("registerService")
public class RegisterServiceImpl implements IRegisterService {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    @Transactional
    public int register(String account, String password, int type) {
        Account a = new Account();
        String defaultName = "User_" + System.currentTimeMillis();
        //验证账号是否重复
        if(!StringUtils.isEmpty(sqlSessionTemplate.selectOne("register.validateAccount",account))){
            if (type == YouHaoConstant.ACCOUNT_TYPE_EMAIL)
                throw new BasicRuntimeException("该邮箱已注册");
            else
                throw new BasicRuntimeException("该手机号已注册");
        }
        User user = new User();
        String userId = UUIDUtils.getUUID();
        user.setUserName(defaultName);
        user.setUserId(userId);
        user.setUserImage("images/pp.jpg");
        user.setUserEvaluation(1);
        sqlSessionTemplate.insert("register.createUser",user); //创建用户

        if (type == YouHaoConstant.ACCOUNT_TYPE_EMAIL){
            a.setEmail(account);
        }else {
            a.setPhone(account);
        }
        a.setUserId(userId);
        a.setPwd(SHA1Util.getSHA1(password));
        a.setRegTime(new Date());
        a.setType(YouHaoConstant.LOGIN_TYPE_FRONT);
        a.setStatus(YouHaoConstant.USER_STATUS_ENABLE);
        return sqlSessionTemplate.insert("register.registerAccount", a); //创建账号
    }
}
