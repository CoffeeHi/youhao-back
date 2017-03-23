package com.chenx.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/22 0022.
 */
@Data
public class TourDetail implements Serializable {
    private String id;

    /**目的地**/
    private String target;

    /**开始日期**/
    private Date dateStart;

    /**结束日期**/
    private Date dateOver;

    /**旅游人数**/
    private int touristNum;

    /**费用方式 1:线下aa 2:我买单**/
    private int payway;

    /**旅行说明**/
    private String content;

    /**联系类型 1:QQ 2:微信 3:邮箱 4:手机号**/
    private int contactType;

    /**联系方式**/
    private String contact;

    /**封面路径**/
    private String coverPath;

    /**标签**/
    private String [] tags;

    /**发布时间**/
    private Date postTime;

//    下面的从tour_state表查------------------------------------------------

    /**当前游客数量**/
    private int tourists;

    private long goods;

    private long coms;

    private long looks;

    /**旅单状态(0-拼团中，1-已组团，2-进行中，3-已完成，4-已解散)**/
    private int state;

//    发布者信息-----------------------------
    private String authorName;

    private String authorImage;

    private String authorIntro;
}
