package com.chenx.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
@Data
@NoArgsConstructor
@Alias("tourVisitMark")
public class TourVisitMark implements Serializable {
    private String id;
    private String userId;
    private Date visitTime;
    private String tourId;

    public TourVisitMark(String userId, Date visitTime, String tourId) {
        this.userId = userId;
        this.visitTime = visitTime;
        this.tourId = tourId;
    }
}
