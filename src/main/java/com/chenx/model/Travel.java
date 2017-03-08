package com.chenx.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "travel")
public class Travel implements Serializable {

    @Id
    @GenericGenerator(name = "_id", strategy = "uuid.hex")
    private String id;
    /**游玩日期**/
    private Date date;
    /**主题**/
    private String title;
    /**天气**/
    private String weather;
    /**图片路径**/
    private String picturePath;
    /**描述**/
    private String description;
    /**创建时间**/
    private Date createTime;
    /**创建人**/
    private String createUser;

}
