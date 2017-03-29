package com.chenx.gateway.commons;

import com.alibaba.fastjson.JSON;
import com.fjhb.commons.util.BeanUtil;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2016/12/27 0027.
 */
public abstract class BasicController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    protected <T>T getRequestParam(Class<T> clazz){
        String requestString = request.getParameter("requestString");
        return JSON.parseObject(requestString, clazz);
    }

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }
}
