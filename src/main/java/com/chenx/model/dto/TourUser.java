package com.chenx.model.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/24 0024.
 */
@Data
@Alias("tourUser")
public class TourUser implements Serializable {

    private String userId;

    private String userName;

    private String userImage;

    private int userRole;
}
