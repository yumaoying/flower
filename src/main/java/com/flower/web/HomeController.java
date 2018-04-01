package com.flower.web;

import com.flower.Util.VertityUtil;
import com.flower.entity.UserInfo;

import org.apache.shiro.authc.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;


/**
 * Created by yumaoying on 2018/3/24.
 */
@Controller
public class HomeController {

    @RequestMapping({"/", "/index"})
    public String index() {
        return "/index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, UserInfo user, Map<String, Object> map) {
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        System.out.println(user);
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                System.out.println("账号不存在：");
                msg = "账号不存在!";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                msg = "密码不正确：";
            } else if (LockedAccountException.class.getName().equals(exception)) {
                msg = "用户已经被锁定不能登录，请与管理员联系！：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                msg = "验证码错误";
            } else if (ExcessiveAttemptsException.class.getName().equals(exception)) {
                msg = "登录失败次数过多,已超过5次";
            } else {
                msg = exception;
            }
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return "/login";
    }


    @RequestMapping("/403")
    public String unauthorizedRole() {
        System.out.println("------没有权限-------");
        return "403";
    }


    //生成短信验证码图片
    @GetMapping("/createImage")
    public void createImage(HttpServletResponse response, HttpSession session) throws IOException {
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VertityUtil.createImage();
        //将验证码存入Session
        session.setAttribute("validateCode", objs[0]);
        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }
}
