package com.chenx;

import com.chenx.utils.UUIDUtils;
import lombok.Data;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
public final class YouHaoConstant {

    //旅单用户状态(0-加入，1-退出)
    public static final int TOUR_USER_STATE_JOIN = 0;
    public static final int TOUR_USER_STATE_EXIT = 1;

    //旅单用户角色(0-发起者，1-拍摄，2-老司机，3-翻译，4-百科全书，5-后勤保障，6-吃瓜群众，7-行程指挥，8-砍价能手)
    public static final int TOUR_USER_ROLE_AUTHOR = 0;
    public static final int TOUR_USER_ROLE_PHOTOR = 1;
    public static final int TOUR_USER_ROLE_DRIVER = 2;
    public static final int TOUR_USER_ROLE_TRANSLATOR = 3;
    public static final int TOUR_USER_ROLE_ENCYCLOPEDIA= 4;
    public static final int TOUR_USER_ROLE_PROTECTOR = 5;
    public static final int TOUR_USER_ROLE_WATCHER = 6;
    public static final int TOUR_USER_ROLE_LEADER = 7;
    public static final int TOUR_USER_ROLE_BARGAINER = 8;


    //旅单图片类型
    public static final int TOUR_IMAGE = 1; //说明图片
    public static final int TOUR_COVER = 2; //封面图片

//    默认头像地址
    public static final String DEFAULT_USER_IMG = "upload/default/user/images/default_head.png";
//    默认封面地址
    public static final String DEFAULT_COVER_IMG = "upload/default/tour/cover/default_cover.jpg";

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
