package com.chenx.gateway.web.login.dto;

import com.fjhb.commons.exception.BasicRuntimeException;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

/**
 * 验证码类型
 *
 * @author chenx
 * @since 0.1.0
 */
@Log4j
public enum ValidateCodeType {


    /**
     * 登入
     */
    LOGIN("LOGIN", 1),

    /**
     * 注册
     */
    REGISTERED("REGISTERED", 2),


    /**
     * 找回密码
     */
    FORGETPASSWORD("FORGETPASSWORD", 3);


    @Getter
    private String title;
    @Getter
    private int value;

    private ValidateCodeType(String title, int value) {
        this.title = title;
        this.value = value;
    }

    public static ValidateCodeType valueOf(int value) {
        ValidateCodeType[] types = ValidateCodeType.values();
        for (ValidateCodeType type : types) {
            if (type.getValue() == value) {
                return type;
            }
        }
        String msg = String.format("找不到对应验证码类型: %d", value);
        log.error(msg);
        throw new BasicRuntimeException(msg);
    }
}
