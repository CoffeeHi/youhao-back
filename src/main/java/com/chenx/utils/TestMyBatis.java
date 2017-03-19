package com.chenx.utils;

import com.alibaba.fastjson.JSON;
import com.chenx.dao.dto.ValidateLoginDto;
import com.chenx.model.dto.EditUserInfo;
import com.chenx.model.User;
import com.chenx.service.redis.IRedisService;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/1 0001.
 */
@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring/spring-annotation.xml","classpath:spring/spring-mybatis.xml",
        "classpath:spring/spring-datasource.xml","classpath:spring/spring-sys-properties.xml",
        "classpath:spring/spring-data-redis.xml"})
@Log4j
public class TestMyBatis{

    @Resource(name = "redisService")
    private IRedisService redisService;

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

    @Test
    public  void testEditUser(){
        EditUserInfo editUserInfo = new EditUserInfo();
        editUserInfo.setId("9f7598b8562740ca845505a4f4204f98");
        editUserInfo.setName("海贼王路飞");
        editUserInfo.setAddr("福建师范大学");
        editUserInfo.setIntro("我是海贼王");
        editUserInfo.setSig("滴滴学生卡");
        editUserInfo.setJob("海贼");
        sqlSessionTemplate.update("user.editUserInfo", editUserInfo);
    }

    @Test
    public void testGetUserInfo(){
        String userId = "95d415fde2ff4f0fafe74c17914f1df4";
//        System.out.println(JSON.toJSONString(sqlSessionTemplate.selectOne("user.getUserInfo", userId)));
        Map param = new HashMap<>();
        param.put("userId", userId);
        param.put("imagePath", "sadasd");
        sqlSessionTemplate.update("user.updateUserImage", param);
    }

    @Test
    public void testRedis(){
        System.out.println("");
    }
}
