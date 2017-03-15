package com.chenx.gateway.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局的拦截器,拦截所有的spring mvc的请求
 * @author huanghj
 *
 */
public class GlobalInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(HandlerMethod.class.getName(),handler);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "false");
		response.setCharacterEncoding("utf-8");
		return true;
	}
}
