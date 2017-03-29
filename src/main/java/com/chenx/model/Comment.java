package com.chenx.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
@Data
@Alias("comment")
public class Comment implements Serializable {
    /*评论id*/
    private String comId;
    /*评论目标id(旅单id、文章id)*/
    private String comTarget;
    /*评论类型(0-旅单、1-文章、2-全景)*/
    private int comType;
    /*评论内容*/
    private String comContent;
    /*回复评论id*/
    private String comParent;
    /*评论者id*/
    private String comAuthor;
    /*评论时间*/
    private Date comTime;
    /*评论点赞数量*/
    private long comGoods;
}
