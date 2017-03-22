package com.chenx.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
@Data
@Alias("tourUserState")
public class TourUserState implements Serializable {

    /*id*/
    private String id;

    /*旅单id*/
    private String tourId;

    /*用户id*/
    private String userId;

    /*用户状态 0-加入，1-退出*/
    private int userState;

    /*用户角色(0-发起者，1-拍摄，2-老司机，3-翻译，4-百科全书，5-后勤保障，6-吃瓜群众，7-行程指挥，8-砍价能手)*/
    private int userRole;

    /*加入时间*/
    private Date userJoinTime;

}
