package com.chenx.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/24 0024.
 */
@Data
public class TourTourists implements Serializable {

    private List<TourUser> tourUsers;

    private boolean ifJoin; //当前用户是否已加入

}
