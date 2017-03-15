package com.chenx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/1 0001.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Alias("account")
@Table(name = "user_account")
public class Account implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid.hex")
    @Column(name = "acc_id")
    private String id;
    @Column(name = "acc_user_id")
    private String userId;
    @Column(name = "acc_email")
    private String email;
    @Column(name = "acc_phone")
    private String phone;
    @Column(name = "acc_pwd")
    private String pwd;
    /**
     `acc_status` int(2) DEFAULT NULL COMMENT '状态  1、启用 2、禁用  3、删除',
     */
    @Column(name = "acc_status")
    private int status;
    /**
     * `acc_type` int(2) DEFAULT NULL COMMENT '账户登录平台  1、前台  2、管理后台 ',
     */
    @Column(name = "acc_type")
    private int type;
    @Column(name = "acc_regtime")
    private Date regTime;

}
