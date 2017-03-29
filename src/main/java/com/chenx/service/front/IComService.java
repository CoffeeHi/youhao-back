package com.chenx.service.front;

import com.chenx.model.Comment;
import com.chenx.utils.Page;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public interface IComService {
    int insertCom(Comment comment);
    Page selectComs(int pageNo, int pageSize, String tourId);
    int deleteCom(String comId, String userId);
}
