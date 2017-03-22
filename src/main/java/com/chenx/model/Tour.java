package com.chenx.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "travel")
public class Tour implements Serializable {

    @Id
    @GenericGenerator(name = "_id", strategy = "uuid.hex")
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

    /**发布者**/
    private String author;

}
