package com.chenx.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/10 0010.
 */
@Data
@AllArgsConstructor
public class SessionInfo implements Serializable{
    private String userId;
    private String userName;
    private String userImage;
    private int userType;
}
