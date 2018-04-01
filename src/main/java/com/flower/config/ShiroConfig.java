package com.flower.config;


import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.Filter;

/**
 * Created by yumaoying on 2018/3/24.
 */
@Configuration
public class ShiroConfig {

    /***
     *ShiroFilterFactoryBean 处理拦截资源文件问题
     * @param securityManager  初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * @return hiroFilterFactory
     */
    @Bean
    public ShiroFilterFactoryBean shireFilter(SecurityManager securityManager) {
        System.out.println("ShiroFilterFactoryBean.shireFilter");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //将自定义 的FormAuthenticationFilter注入shiroFilter中
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("authc", new CustomFormAuthenticationFilter());
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);//拦截器.
        //拦截器.
        LinkedHashMap<String, String> filterChainDefinitionMap;
        filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        // anon：它对应的过滤器里面是空的,什么都没做,可以理解为不拦截
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/createImage", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //配置记住我或认证通过可以访问的地址
        filterChainDefinitionMap.put("/index", "user");
        filterChainDefinitionMap.put("/", "user");
        //对于角色是2,拥有权限userInfo:add才可以访问
        filterChainDefinitionMap.put("/add", "roles[2]，perms[userInfo:add]");
        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");
        // filterChainDefinitionMap.put("/userInfo/**", "authc"); //仅过滤userInfo下的
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面;不止需要这个 还需要在异常拦截器里配置
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    /**
     * 自定义密码配置
     * 由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了）
     * 没有自定义密码配置 则CredentialsMatcher.doCredentialsMatch(token, info)实现明文校验
     *
     * @return hashedCredentialsMatcher, 用来对密码进行散列算法加密
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        //对登陆多次可以打开此注释
        HashedCredentialsMatcher hashedCredentialsMatcher = new RetryLimitCredentialMatcher(ehCacheManager());
        // HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法:这里使用MD5算法加密;还可以使用SHA1，SHA1、SHA512
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，比如散列两次，相当于 md5(md5(""))，加密两次;
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());//加入自定义的密码匹配，若不用，则不加
        return myShiroRealm;
    }

    /***
     * shiro的安全管理,主要是身份认证管理，缓存管理
     * 安全管理器是整个shiro的核心
     * @return securityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //配置权限身份认证
        securityManager.setRealm(myShiroRealm());
        //缓存管理
        securityManager.setCacheManager(ehCacheManager());
        //配置记住我管理
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager securityManager
     * @return authorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor aushauthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 配置异常拦截器，权限不够或其他异常时进行处理
     *
     * @return 异常拦截器
     */
    @Bean(name = "simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver reateSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
        mappings.setProperty("UnauthorizedException", "403");
        r.setExceptionMappings(mappings);  // 定义需要处理的异常，用类名或完全路径名作为key，异常页面名作为value
        r.setDefaultErrorView("error");    // 设置默认的异常处理页面，当没有注册对应的异常类型处理时使用，没有默认值
        r.setExceptionAttribute("ex");     //异常处理页面用来获取异常对象的变量名，如果不设置，默认名为exception
        //r.setWarnLogCategory("example.MvcLogger");     // No default
        return r;
    }

//    /***
//     *如果thymeleaf想使用shiro标签,需要配置,pom引入thymeleaf-extras-shiro，
//     * @return shiroDialect
//     * html中引入相应标签库
//     * <html lang="en" xmlns:th="http://www.thymeleaf.org"
//     * xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3"
//     * xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
//     */
//    //@Bean
//    public ShiroDialect shiroDialect() {
//        return new ShiroDialect();
//    }

    /***
     *如果使用shiro缓存，需配置，pom引入shiro-ehcache
     * 添加ehcache-shiro.xml
     * @return 缓存管理
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        System.out.println("ShiroConfiguration.getEhCacheManager()");
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        return cacheManager;
    }

    /***
     *记住我功能的实现
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     * @return Cookie的生成模版
     */
    @Bean
    public SimpleCookie remberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // 记住我cookie生效时间30-2592000天 ,单位秒;
        simpleCookie.setMaxAge(1800);
        return simpleCookie;
    }

    /***
     * cookie管理对象
     *rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     * @return 生成rememberMe管理器
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(remberMeCookie());
        //rememberMe cookie加密的密钥  默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }
}
