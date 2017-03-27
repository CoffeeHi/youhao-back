package com.chenx.gateway.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.chenx.utils.Page;
import com.fjhb.fastJson.AmendJSON;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

/**
 * 将返回值用{status:true,info:returnValue}包起来
 * 并且把Date类型值输出时可以设置format,默认为"yyyy-MM-dd HH:mm:ss"
 */
@Setter
public class WrappedFastJsonHttpMessageConverter extends
		FastJsonHttpMessageConverter {
	protected static String dateFormat="yyyy-MM-dd HH:mm:ss";
	
	public WrappedFastJsonHttpMessageConverter() {
		super.setSupportedMediaTypes(Arrays.asList(MediaType.valueOf("*/*;charset=utf-8"),MediaType.valueOf("application/json;charset=utf-8"), MediaType.valueOf("application/*+json;charset=utf-8")));
	}

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        if(Collection.class.isAssignableFrom(clazz)){//如果是集合类型的
            if(inputMessage instanceof ServletServerHttpRequest) {
                ServletServerHttpRequest request = (ServletServerHttpRequest)inputMessage;
                HandlerMethod handlerMethod=(HandlerMethod)request.getServletRequest().getAttribute(HandlerMethod.class.getName());
                MethodParameter[] parameters=handlerMethod.getMethodParameters();
                MethodParameter mp=Arrays.stream(parameters).filter(parameter->
                        parameter.hasParameterAnnotation(RequestBody.class)&&parameter.getParameterType()==clazz).
                        findFirst().get();
                if(mp!=null) {
                    Type type=mp.getGenericParameterType();
                    Assert.isTrue(type instanceof ParameterizedType,"如果使用了集合类型,必须提供泛型才可以用json反序列化");
                    ParameterizedType genericType=(ParameterizedType)type;
                    Class elementClass=(Class<?>)genericType.getActualTypeArguments()[0];
                    return readListOrArray(inputMessage,elementClass);
                }
            }
        }else if(clazz.isArray()){//如果是数组的要用数组的元素类型进行反序列化
            return readListOrArray(inputMessage, clazz.getComponentType());
        }
        return super.readInternal(clazz, inputMessage);
    }
    protected Object readListOrArray(HttpInputMessage inputMessage,Class<?> elementClass) throws IOException{
        String text=IOUtils.toString(inputMessage.getBody(),getCharset().name());
        return JSON.parseArray(text, elementClass);
    }
    /**
	 * 默认都使用application/json格式输出,如果返回的对象上存在JAXB注解也使用json输出,除非@RequestMapping的producers显示指定输出application/xml
	 */
	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		HttpHeaders headers=outputMessage.getHeaders();
		MediaType contentType=headers.getContentType();
		if(!MediaType.APPLICATION_JSON.isCompatibleWith(contentType)){
			headers.setContentType(MediaType.APPLICATION_JSON);
		}
		JSONObject jo=new JSONObject();
        jo.put("code", 200);
		jo.put("status",true);
		if(obj instanceof Page){
			Page<?> page=(Page<?>)obj;
			jo.put("totalSize",page.getTotalSize());
            jo.put("totalPageSize",page.getTotalPageSize());
			jo.put("info", page.getCurrentPageData());
		}else
			jo.put("info", obj);
		OutputStream out = outputMessage.getBody();
        String text = AmendJSON.toJSONStringWithDateFormat(jo, dateFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect);
        byte[] bytes = text.getBytes(getCharset());
        out.write(bytes);
	}
}
