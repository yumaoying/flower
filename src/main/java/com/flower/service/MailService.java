package com.flower.service;

/**
 * Created by yumaoying on 2018/4/1.
 */
public interface MailService {
    public void sendSimpleMail(String to, String subject, String content);

    //发送html邮件
    public void sendHtmlMail(String to, String subject, String content);

    //发送带附件邮件
    public void sendAttachmentsMail(String to, String subject, String content, String filePath);

    //嵌入静态资源的邮件-邮件中的静态资源一般就是指图片
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);
}
