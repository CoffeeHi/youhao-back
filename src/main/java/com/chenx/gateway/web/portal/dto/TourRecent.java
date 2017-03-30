package com.chenx.gateway.web.portal.dto;

import com.chenx.model.dto.TourRecentPost;
import com.chenx.model.dto.TourVisitor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/30 0030.
 */
@Data
public class TourRecent implements Serializable {
    private List<TourVisitor> tourVisitors;
    private List<TourRecentPost> tourRecentPosts;
}
