package com.chenx.model.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22 0022.
 */
@Data
@Alias("author")
public class Author implements Serializable {

    private String userName;

    private String userImage;

    private String userSig;
}
