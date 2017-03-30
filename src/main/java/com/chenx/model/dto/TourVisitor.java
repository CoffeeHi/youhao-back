package com.chenx.model.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
@Data
@Alias("tourVisitor")
public class TourVisitor implements Serializable {
    private String userId;

    private String userName;

    private String userImage;

    private String userSig;

    private Date visitTime;
}
