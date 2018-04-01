package com.flower.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yumaoying on 2018/4/1.
 * 用户登陆多次错误限制
 * CredentialsMatcher是shiro提供的用于加密密码和验证密码服务的接口，而HashedCredentialsMatcher正是CredentialsMatcher的一个实现类
 */
public class RetryLimitCredentialMatcher extends HashedCredentialsMatcher {
    private Cache<String, AtomicInteger> passwordRetryCache;


    RetryLimitCredentialMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    /***
     *在回调方法doCredentialsMatch(AuthenticationToken token,AuthenticationInfo info)中进行身份认证的密码匹配
     * 这里我们引入了Ehcahe用于保存用户登录次数，如果登录失败retryCount变量则会一直累加，如果登录成功，那么这个count就会从缓存中移除，从而实现了如果登录次数超出指定的值就锁定。
     * @param token AuthenticationToken
     * @param info  AuthenticationInfo
     * @return 匹配
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //获取输入的账户名
        String username = (String) token.getPrincipal();
        System.out.println("RetryLimitCredentialMatcher:" + username);
        //获取缓存的账户名
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            System.out.println("retryCount:" + retryCount);
            passwordRetryCache.put(username, retryCount);
        }
        if (retryCount.incrementAndGet() > 2) {
            System.out.println("retryCount:" + retryCount.incrementAndGet());
            throw new ExcessiveAttemptsException();
        }
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}
