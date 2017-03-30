package com.chenx.service.front;

import com.chenx.model.Blog;
import com.chenx.model.UserInfo;
import com.chenx.model.dto.EditUserInfo;
import com.chenx.utils.dto.SessionInfo;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
public interface IBlogService {
    String insertBlog(Blog article);
}
