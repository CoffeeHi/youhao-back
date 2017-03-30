package com.chenx.model.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/30 0030.
 */
@Data
@Alias("tourRecentPost")
public class TourRecentPost implements Serializable {
    private String id;
    private String coverPath;
    private String target;
}
