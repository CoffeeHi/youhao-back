package com.chenx.dao;

import com.chenx.model.Account;
import com.chenx.model.User;
import com.fjhb.mybatis.dao.BaseMybatisDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
public interface RegisterDao {
    int registerAccount(Account account);
    String validateAccount(String account);
    int createUser(User user);
}
