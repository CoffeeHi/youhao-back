package com.chenx.dao;

import com.chenx.model.User;
import com.fjhb.mybatis.dao.BaseMybatisDao;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
public interface ILoginDao extends BaseMybatisDao<User, String> {
    User validateLogin(String account, String password);
}
