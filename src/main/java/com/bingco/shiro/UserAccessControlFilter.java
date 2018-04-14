package com.bingco.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class UserAccessControlFilter extends FormAuthenticationFilter {

    private final String AJAX_HEADER__KEY = "X-Requested-With";

    private final String AJAX_HEADER__VALUE = "XMLHttpRequest";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return AJAX_HEADER__VALUE.equalsIgnoreCase(((HttpServletRequest) request).getHeader(AJAX_HEADER__KEY)) ?
                false : super.isAccessAllowed(request, response, mappedValue);
    }

}
