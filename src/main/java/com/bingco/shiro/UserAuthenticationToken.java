package com.bingco.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UserAuthenticationToken extends UsernamePasswordToken {

    public static String VALIDATE_CODE__KEY = "validate_code__key";

    private String validateCode;

    public UserAuthenticationToken() {}

    public UserAuthenticationToken(final String username, final String password, final boolean rememberMe, final String validateCode, final String host) {
        super(username, password != null ? password.toCharArray() : null, rememberMe, host);
        this.validateCode = validateCode;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    @Override
    public Object getPrincipal() {
        return getUsername();
    }

    @Override
    public Object getCredentials() {
        return getPassword();
    }

}
