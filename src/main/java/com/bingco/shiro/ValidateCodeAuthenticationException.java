package com.bingco.shiro;

import org.apache.shiro.authc.AuthenticationException;

public class ValidateCodeAuthenticationException extends AuthenticationException {

    public ValidateCodeAuthenticationException() {}

    public ValidateCodeAuthenticationException(String errorMsg) {
        super(errorMsg);
    }
}
