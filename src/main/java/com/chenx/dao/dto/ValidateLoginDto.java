package com.chenx.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * Created by Administrator on 2017/3/10 0010.
 */
@Data
@AllArgsConstructor
@Alias("validateLogin")
public class ValidateLoginDto {
    private String account;
    private String password;
    private int type;
}
