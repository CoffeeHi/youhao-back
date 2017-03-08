package com.chenx.gateway.web.admin.dto;

import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


/**
 * Created by Administrator on 2016/11/13 0013.
 */
@Log4j
@Data
public class Product implements Serializable {
    @NotNull
    @Size(min=1, max=10)
    private String name;

    private String description;

    private float price;

    private List<MultipartFile> images;

}
