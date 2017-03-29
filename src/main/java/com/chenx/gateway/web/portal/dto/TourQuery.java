package com.chenx.gateway.web.portal.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
@Data
public class TourQuery implements Serializable {

    private String target;

    private Date dateStart;

    private Date dateOver;
}
