package com.chenx.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/30 0030.
 */
@Data
@Alias("blog")
public class Blog implements Serializable {
    private String id;
    private String title;
    private String content;
    private String author;
    private Date postTime;
}
