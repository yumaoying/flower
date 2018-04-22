package com.flower.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Created by yumaoying on 2018/4/1.
 */
public class UserEamil {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

//    @RequestMapping("/forgetPwd")
//    public ModelAndView getPwd(HttpServletResponse response, String loginName) throws IOException {
//        ModelAndView mv = new ModelAndView();
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setFrom(username);
//        simpleMailMessage.setTo(service.getMail(loginName));//目的邮箱
//        simpleMailMessage.setSubject("重置密码");  //邮箱主题
//        simpleMailMessage.setText("重置的密码为123456789");//邮箱内容 自定义
//        javaMailSender.send(simpleMailMessage);
//        mv.setViewName("login/success");
//        return mv;
//    }
}
