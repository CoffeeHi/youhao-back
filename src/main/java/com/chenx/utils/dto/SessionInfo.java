package com.chenx.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/10 0010.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionInfo implements Serializable{
    private String sessionId;
    private String userId;
    private String userName;
    private String userImage;
    private int userType;
}
