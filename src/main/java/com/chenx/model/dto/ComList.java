package com.chenx.model.dto;

import com.chenx.model.Comment;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
@Data
@Alias("comList")
public class ComList {
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
    /*评论者姓名*/
    private String comAuthorName;
    /*评论者头像*/
    private String comAuthorImg;
    /*评论时间*/
    private Date comTime;
    /*评论点赞数量*/
    private long comGoods;
    /*评论列表*/
    private List<ComList> commentList;
}
