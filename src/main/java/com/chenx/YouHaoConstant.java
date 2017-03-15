package com.chenx;

import com.chenx.utils.UUIDUtils;
import lombok.Data;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
public final class YouHaoConstant {

//    账户登录平台  1、前台  2、管理后台
    public static final int LOGIN_TYPE_FRONT = 1;
    public static final int LOGIN_TYPE_ADMIN = 2;

//    账户类型  1、邮箱  2、手机号
    public static final int ACCOUNT_TYPE_EMAIL = 1;
    public static final int ACCOUNT_TYPE_PHONE = 2;

//    用户状态 1、启用 2、禁用  3、删除
    public static final int USER_STATUS_ENABLE = 1;
    public static final int USER_STATUS_FORBID = 2;
    public static final int USER_STATUS_DELETE = 3;

    /** 验证码失效时间 3分钟 **/
    public static final Long VALIDATION_CODE_EXPIRATION_TIME = 180000L;

}
