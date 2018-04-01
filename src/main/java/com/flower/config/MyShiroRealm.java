package com.flower.config;

import com.flower.entity.SysRole;
import com.flower.entity.UserInfo;
import com.flower.entity.SysPermission;
import com.flower.service.UserInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

/**
 * Created by yumaoying on 2018/3/24.
 * 用于身份信息权限信息的验证
 */
public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private UserInfoService userInfoService;

    /***
     *权限校验
     * @param principals  验证实体
     * @return authorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置:MyShiroRealm.doGetAuthorizationInfo");

        // UserInfo userInfo= (UserInfo) SecurityUtils.getSubject().getPrincipal();
        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
        // 权限信息对象authorizationInfo，用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // System.out.println("userInfo" + userInfo);
        for (SysRole role : userInfo.getRoleList()) {
            authorizationInfo.addRole(role.getRole());
            for (SysPermission sysPermission : role.getPermissions()) {
                authorizationInfo.addStringPermission(sysPermission.getPermission());
            }
        }
        return authorizationInfo;
    }

    /**
     * 用来做身份认证的-验证用户输入的账号和密码是否正确
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("AuthorizationInfo.doGetAuthenticationInfo");
        //获取用户的输入的账号.
        String userName = (String) token.getPrincipal();
        System.out.println("用户的输入的账号userName" + userName);
        System.out.println("token.getCredentials()" + token.getCredentials());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserInfo userInfo = userInfoService.findByUsername(userName);
        System.out.println("=========" + userInfo);
        if (userInfo == null) throw new UnknownAccountException();
        if (userInfo.getState() == 0) throw new LockedAccountException(); // 帐号锁定
        // 从这里传入的password（这里是从数据库获取的）和token（filter中登录时生成的）中的password做对比，如果相同就允许登录，不相同就抛出异常。
        //如果验证成功，最终这里返回的信息authenticationInfo 的值与传入的第一个字段的值相同（我这里传的是user对象
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo,//用户名
                userInfo.getPassword(),//密码-从数据库中获取的密码
                ByteSource.Util.bytes(userInfo.credentialsSalt()),//salt=username+salt,因为配置了凭证匹配器，认证时密码是加密的
                getName());//当前realm的名字
        // 当验证都通过后，把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userSession", userInfo);
        session.setAttribute("userSessionId", userInfo.getUid());
        return authenticationInfo;
    }
}
