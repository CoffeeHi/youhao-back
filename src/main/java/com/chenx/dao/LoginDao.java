package com.chenx.dao;

import com.chenx.dao.dto.ValidateLoginDto;
import com.chenx.model.User;
import com.chenx.utils.dto.SessionInfo;
import com.fjhb.mybatis.dao.BaseMybatisDao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
public interface LoginDao {
    String validateAccount(ValidateLoginDto dto);
    User selectUserInfo(String userId);
}
