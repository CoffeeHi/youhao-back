package com.chenx.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
@Data
@Alias("tourState")
public class TourState implements Serializable {

    /*id*/
    private String id;

    /*旅单id*/
    private String tourId;

    /*当前旅单游客数量*/
    private int tourists;

    /*旅单浏览量*/
    private long looks;

    /*旅单评论数量*/
    private long coms;

    /*旅单点赞数量*/
    private long goods;

    /*旅单状态(0-拼团中，1-已组团，2-进行中，3-已完成，4-已解散)*/
    private int state;

}
