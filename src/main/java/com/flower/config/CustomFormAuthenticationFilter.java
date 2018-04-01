package com.flower.config;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by yumaoying on 2018/4/1.
 * 验证输入验证码，shiro使用FormAuthenticationFilter进行表单认证
 * 验证校验的功能应该加在FormAuthenticationFilter中，在认证之前进行验证码校验。
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        //获取短信验证码
        String validateCode = (String) session.getAttribute("validateCode");
        System.out.println("validateCode:"+validateCode);
        //获取输入的短信验证码
        String randomCode = httpServletRequest.getParameter("randomCode");
        System.out.println("randomCode:"+randomCode);
        if (randomCode != null && validateCode != null && !randomCode.equals(validateCode)) {
            //如果校验失败，将验证码错误失败信息，通过shiroLoginFailure设置到request中
            httpServletRequest.setAttribute("shiroLoginFailure", "kaptchaValidateFailed");
            //拒绝访问,不在校验账号和密码
            return true;
        }
        return super.onAccessDenied(request, response);
    }
}
