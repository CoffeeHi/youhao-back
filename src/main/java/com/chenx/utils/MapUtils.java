package com.chenx.utils;

import lombok.extern.log4j.Log4j;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/10 0010.
 */
@Log4j
public class MapUtils {
    /**
     * 使用reflect进行转换
     */

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }

        return map;
    }

    public static Map<String, Serializable> objectSeriablizeToMap(Object obj) {
        if (obj == null) {
            return null;
        }

        Map<String, Serializable> map = new HashMap<String, Serializable>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj).toString().getBytes("UTF-8"));
            } catch (Exception e) {
                log.info("--------对象转Map序列化失败--------");
            }
        }

        return map;
    }
}
