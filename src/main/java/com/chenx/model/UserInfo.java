package com.chenx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("userInfo")
public class UserInfo {
    private String id;
    private String name;
    private String job;
    private String sig;
    private String addr;
    private String intro;
    private String image;
}
