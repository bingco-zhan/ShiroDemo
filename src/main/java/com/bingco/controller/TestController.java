package com.bingco.controller;

import com.bingco.Util.MD5;
import com.bingco.shiro.UserAuthenticationToken;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DelegatingSubject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/dddd")
    @ApiOperation(value = "测试 收到顺...", notes = "测试 收到顺丰寄送副食店副食店解放老师的方式的浪费时间发", httpMethod = "POST")
    public String test(@ApiParam( name = "test", required = true) @RequestParam(name = "test", required = true) String test) {
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpSession session, String username, String password) throws NoSuchAlgorithmException {
        try {
            session.setAttribute(UserAuthenticationToken.VALIDATE_CODE__KEY, "123");
            SecurityUtils.getSubject().login(new UserAuthenticationToken(username, MD5.md5(password),
                    true, "123", null));
            System.out.println("isRemamberMe: " + SecurityUtils.getSubject().isRemembered());
            return "redirect:/index.jsp";
        }

        catch(AuthenticationException e) {
            e.printStackTrace();
            return "redirect:/login.jsp";
        }
    }
}
