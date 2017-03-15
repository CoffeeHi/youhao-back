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
    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_image")
    private String userImage;
    @Column(name = "user_signature")
    private String userSignature;
    @Column(name = "user_address")
    private String userAddress;
    @Column(name = "user_job")
    private String userJob;
    @Column(name = "user_introduce")
    private String userIntroduce;
    @Column(name = "user_evaluation")
    private double userEvaluation;


}
