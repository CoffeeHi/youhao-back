package com.chenx.gateway.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fjhb.commons.exception.BasicRuntimeException;
import com.fjhb.commons.exception.ErrCodeConstant;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 在针对json格式返回的业务方法有异常时,统一用{status:false,info:异常信息}
 * 并且对请求信息请求debug级别的打印
 * @author huanghj
 *
 */
@Log4j
public class WrappedHandlerExceptionResolver extends DefaultHandlerExceptionResolver {
	
	public WrappedHandlerExceptionResolver() {
		setOrder(HIGHEST_PRECEDENCE);
	}
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

        if(log.isDebugEnabled()) {
			printDebugInfo(request);
		}

		if(handler instanceof HandlerMethod) {
			this.setResponseStatus(request, response, ex);

//			HandlerMethod hm = (HandlerMethod)handler;
//			ResponseBody rb = hm.getMethodAnnotation(ResponseBody.class);
//			if(rb != null){//只要是以json格式返回的都用统一格式

			//如果控制器上有@RestController注解，则将错误信息格式化成json输出
			RestController[] restAnnos = ((HandlerMethod) handler).getBeanType().getAnnotationsByType(RestController.class);
			if (restAnnos.length > 0){
				JSONObject jo = new JSONObject();
				jo.put("status",false);
				jo.put("code", 500);
				if (ex instanceof BasicRuntimeException) {
					ErrCodeConstant code = ((BasicRuntimeException) ex).getErrCode();
					log.error(ex.getMessage());
					if (code != null) {
						jo.put("code", code.getCode());
						jo.put("info", code.getMessage());
					} else {
						try {
							JSONObject jsonObject = JSONObject.parseObject(ex.getMessage());
							if (jsonObject != null&&jsonObject.get("message")!=null) {
								jo.put("info",jsonObject.get("message"));
							}
						} catch (Exception e) {
							jo.put("info", ex.getMessage());
						}
					}
				} else if (ex instanceof IllegalArgumentException) {
					jo.put("info", ex.getMessage());
				}else if(ex instanceof BindException){//如果是JSR校验异常把校验的信息拿出来
					BindException bindException=(BindException)ex;
					List<String> messages=bindException.getAllErrors().stream().map(error->	error.getDefaultMessage()).collect(Collectors.toList());
					jo.put("info",messages);
				}else if(ex instanceof MethodArgumentNotValidException){
					MethodArgumentNotValidException validException=(MethodArgumentNotValidException)ex;
					List<String> messages=validException.getBindingResult().getAllErrors().stream().map(error->error.getDefaultMessage()).collect(Collectors.toList());
					jo.put("info", messages);
				}else {
					ex.printStackTrace();
					jo.put("info", ex);
				}
				try {
					response.setContentType("application/json; charset=utf-8");
					response.getWriter().write(jo.toString());
				} catch (IOException e) {
					log.error(e);
				}
				return new ModelAndView();
			}
		}
		return super.doResolveException(request, response, handler, ex);
	}

	private void setResponseStatus(HttpServletRequest request, HttpServletResponse response, Exception ex){
		if (ex instanceof BasicRuntimeException) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else if (ex instanceof NoSuchRequestHandlingMethodException) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}else if (ex instanceof HttpRequestMethodNotSupportedException) {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}else if (ex instanceof HttpMediaTypeNotSupportedException) {
			response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
		}else if (ex instanceof HttpMediaTypeNotAcceptableException) {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}else if (ex instanceof MissingServletRequestParameterException) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else if (ex instanceof ServletRequestBindingException) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else if (ex instanceof ConversionNotSupportedException) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}else if (ex instanceof TypeMismatchException) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else if (ex instanceof HttpMessageNotReadableException) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else if (ex instanceof HttpMessageNotWritableException) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}else if (ex instanceof MethodArgumentNotValidException) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else if (ex instanceof MissingServletRequestPartException) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else if (ex instanceof BindException) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else if (ex instanceof NoHandlerFoundException) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}else if (ex instanceof IllegalArgumentException){
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
     * **
     * 打印调试信息
     *
     * @param ip
     * @param uri
     */
    private void printDebugInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String ip=request.getRemoteAddr();
        String uri=request.getRequestURI();
        Enumeration<String> requestKeys = request.getParameterNames();
        String requestInfo = "";
        while (requestKeys.hasMoreElements()) {
            String key = requestKeys.nextElement();
            requestInfo += key + "=" + request.getParameter(key) + ";";
        }

        Enumeration<String> sessionKeys = session.getAttributeNames();
        String sessionInfo = "";
        while (sessionKeys.hasMoreElements()) {
            String key = sessionKeys.nextElement();
            sessionInfo += key + "=" + session.getAttribute(key).toString() + ";";
        }

        log.debug("来自" + ip + "的请求在访问" + uri + "时调试信息如下：\r\n" + "request:" + requestInfo + "\r\n" + "session:" + sessionInfo);
    }
    
}
