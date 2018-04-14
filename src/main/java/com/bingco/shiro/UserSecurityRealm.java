package com.bingco.shiro;

import com.bingco.Util.MD5;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import java.security.MessageDigest;

public class UserSecurityRealm extends AuthorizingRealm {

    private String username = "admin";

    private String password = "admin";

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("1");
        info.addRole("2");
        info.addRole("3");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (token instanceof UserAuthenticationToken) {
            UserAuthenticationToken userToken = (UserAuthenticationToken) token;
            Session session = SecurityUtils.getSubject().getSession();

            String validateCode = (String) session.getAttribute(UserAuthenticationToken.VALIDATE_CODE__KEY);
            if (validateCode == null) {
                throw new ValidateCodeAuthenticationException("验证码已过期!");
            }

            if (!validateCode.equalsIgnoreCase(userToken.getValidateCode())) {
                throw new ValidateCodeAuthenticationException("验证码不正确!");
            }

            if (!username.equals(userToken.getUsername())) {
                throw new UnknownAccountException("用户不存在!");
            }

            if (!MD5.md5(password).equalsIgnoreCase(String.valueOf(userToken.getPassword()))) {
                throw new UnknownAccountException("密码不正确!");
            }

            return new SimpleAuthenticationInfo(username, userToken.getPassword(), getName());
        } else throw new IllegalArgumentException(token.getClass() + " can not cast " + UserAuthenticationToken.class);
    }

    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {

        if (token != null && token instanceof UserAuthenticationToken) {
            if (MessageDigest.isEqual(token.getPrincipal().toString().getBytes(),
                    info.getPrincipals().getPrimaryPrincipal().toString().getBytes())) {
                return;
            }
        }

        super.assertCredentialsMatch(token, info);
    }
}
