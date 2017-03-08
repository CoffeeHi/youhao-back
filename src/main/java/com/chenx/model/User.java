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
@Entity
@Alias("user")
@Table(name = "sys_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @Column(name = "user_id")
    private String id;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_signature")
    private String signature;
    @Column(name = "user_address")
    private String address;
    @Column(name = "user_job")
    private String job;
    @Column(name = "user_introduce")
    private String introduce;

}
