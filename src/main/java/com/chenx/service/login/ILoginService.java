package com.chenx.service.login;

import com.chenx.model.User;
import com.chenx.utils.dto.SessionInfo;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
public interface ILoginService {

    SessionInfo validateLogin(String account, String password, String sessionId);

    SessionInfo checkLogin(String userId, String sessionId);
}
