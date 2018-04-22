package com.flower.controller;

import com.flower.util.VertityUtil;
import com.flower.entity.SysUser;

import org.apache.shiro.authc.*;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;


/**
 * Created by yumaoying on 2018/3/24.
 */
@Controller
public class HomeController {

    @RequestMapping({"/", "/index"})
    public String index() {
        return "redirect:/sysUser";
    }


    @RequestMapping("/login")
    public String login(HttpServletRequest request, SysUser user, boolean rememberMe, Map<String, Object> map) {
        // 登录失败从request中获取shiro处理的异常信息
        // shiroLoginFailure:就是shiro异常类的全类名
        System.out.println(user);
        String msg = "";
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println(exception);
        if (exception != null) {
            if ("kaptchaValidateFailed".equals(exception)) {
                msg = "验证码错误";
            } else if (UnknownAccountException.class.getName().equals(exception)) {
                msg = "账号不存在!";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                msg = "密码不正确!";
            } else if (LockedAccountException.class.getName().equals(exception)) {
                msg = "该账户未启用，请与管理员联系!";
            } else if (ExcessiveAttemptsException.class.getName().equals(exception)) {
                msg = "登陆次数过多,账户已锁定,请稍后重试!";
            } else {
                msg = "其他原因登陆失败,请稍后重试!";
            }
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return "/login";
    }


    @RequestMapping("/403")
    public String unauthorizedRole() {
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

    //MultipartFile是Spring上传文件的封装类，包含了文件的二进制流和文件属性等信
    @RequestMapping("/toUpload")
    public String toUpload() {
        return "upload";
    }

    /***
     * spring.http.multipart.enabled=true #默认支持文件上传.
     *spring.http.multipart.file-size-threshold=0 #支持文件写入磁盘.
     * spring.http.multipart.location=# 上传文件的临时目录
     * spring.http.multipart.max-file-size=1Mb # 最大支持文件大小
     * spring.http.multipart.max-request-size=10Mb # 最大支持请求大小
     * @return
     */
    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "请选择一个文件");
            return "redirect:uploadStatus";
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get("F://temp//" + file.getOriginalFilename());
            Files.write(path, bytes);
            redirectAttributes.addFlashAttribute("message",
                    "成功上传了" + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "上传失败了" + file.getOriginalFilename());
        }
        return "redirect:/uploadStatus";
    }


    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

    @GetMapping("/right")
    public String right() {
        return "common/right";
    }

}
