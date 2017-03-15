package com.chenx.utils;

import com.chenx.dao.dto.ValidateLoginDto;
import com.chenx.model.Account;
import com.chenx.model.User;
import com.fjhb.mybatis.dao.BaseMybatisDaoImpl;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/1 0001.
 */
@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring/spring-annotation.xml","classpath:spring/spring-mybatis.xml",
        "classpath:spring/spring-datasource.xml","classpath:spring/spring-sys-properties.xml"})
@Log4j
public class TestMyBatis{

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Test
    public void TestReg(){
//        log.info(sqlSessionTemplate.selectOne("register.validateAccount", "123"));

//        Account a = new Account();
//        a.setId(UUIDUtils.getUUID());
//        a.setEmail(UUIDUtils.getUUID());
//        a.setType(1);
//        a.setPhone(UUIDUtils.getUUID());
//        a.setPwd("as");
//        a.setRegTime(new Date());
//        log.info(sqlSessionTemplate.insert("register.registerAccount", a));

        User user = new User();
        String userId = UUIDUtils.getUUID();
        user.setUserName("88888888888");
        user.setUserId(userId);
        user.setUserImage("images/pp.jpg");
        user.setUserEvaluation(1);
        sqlSessionTemplate.insert("register.createUser",user); //创建用户
    }

    @Test
    public void TestLogin(){
//        User user = sqlSessionTemplate.selectOne("login.selectUserInfo", "e8d451e450874cb481f87ef5f5ea69d2");
//        log.info(user.toString());

        ValidateLoginDto va = new ValidateLoginDto("150", SHA1Util.getSHA1("666"), 1);
        String id = sqlSessionTemplate.selectOne("login.validateLogin", va);
        System.out.println(id);
    }
}
