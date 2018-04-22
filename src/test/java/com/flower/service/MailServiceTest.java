package com.flower.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Created by yumaoying on 2018/4/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {
    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testSimpleMail() {
        mailService.sendSimpleMail("13368254735@163.com", "重置密码", "找回密码");
    }

    @Test
    public void testHtmlMail() {
        String content = "<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail("xxx@139.com", "test simple mail", content);
    }

    @Test
    public void sendAttachmentsMail() {
        String filePath = "e:\\tmp\\application.log";
        mailService.sendAttachmentsMail("ityouknow@126.com", "主题：带附件的邮件", "有附件，请查收！", filePath);
    }

    @Test
    public void sendInlineResourceMail() {
        String rscId = "neo006";
        String content = "<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "C:\\Users\\summer\\Pictures\\favicon.png";
        mailService.sendInlineResourceMail("ityouknow@126.com", "主题：这是有图片的邮件", content, imgPath, rscId);
    }

    //发送模版消息
    @Test
    public void sendTemplateMail() {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("username", "LingDu");
        String emailContent = templateEngine.process("email", context);

        System.out.println(emailContent);
        mailService.sendHtmlMail("821300801@qq.com", "主题：这是模板邮件", emailContent);
    }
}
