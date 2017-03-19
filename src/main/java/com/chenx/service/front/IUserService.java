package com.chenx.service.front;

import com.chenx.model.UserInfo;
import com.chenx.model.dto.EditUserInfo;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
public interface IUserService {
    int editUserInfo(EditUserInfo editUserInfo, String sessionId);
    UserInfo getUserInfo(String sessionId);
    int saveImagePath(String userId, String imagePath, String sessionId);
}
