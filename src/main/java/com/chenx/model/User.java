package com.chenx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.beans.ConstructorProperties;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/1 0001.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias("user")
public class User implements Serializable {
    private String id;
    private String name;
    private String image;
    private String signature;
    private String address;
    private String job;
    private String introduce;
    private double evaluation;

}
