package com.chenx.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/25 0025.
 */
@Data
public class TourSimple implements Serializable {

    /**旅单id**/
    private String id;

    private String target;

    private Date dateStart;

    private Date dateOver;

    private String coverPath;

    private Date postTime;

    private String userId;

    private String userName;

    private String userImage;

}
