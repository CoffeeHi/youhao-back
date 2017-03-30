package com.chenx.service.front.impl;

import com.chenx.model.Blog;
import com.chenx.service.front.IBlogService;
import com.fjhb.commons.exception.BasicRuntimeException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/3/30 0030.
 */
@Service("blogService")
public class BlogServiceImpl implements IBlogService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public String insertBlog(Blog blog) {
        int i = sqlSessionTemplate.insert("blog.insert", blog);
        if (i == 1){
            return blog.getId();
        }else {
            throw new BasicRuntimeException("发布文章失败，请重试");
        }
    }
}
