package com.flower.config;

import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.SizeLimitExceededException;


/**
 * Created by yumaoying on 2018/4/1.
 * 全局异常处理
 * ControllerAdvice用来监控Multipart上传的文件大小是否受限
 * 当出现此异常时在前端页面给出提示。
 */
@ControllerAdvice
public class GlobalExceptionHandler {

//    /***
//     * 自定义文件上传错误
//     * @param e
//     * @param redirectAttributes
//     * @return
//     */
//
//    @ExceptionHandler(MultipartException.class)
//    public String handlerError(MultipartException e, RedirectAttributes redirectAttributes) {
//        redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
//        return "redirect:/uploadStatus";
//    }


    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public String handlerMultipartError(HttpServletRequest request, Throwable ex) {
        String message = "";
        MultipartException mEx = (MultipartException) ex;
        Throwable cause = ex.getCause().getCause();
        if (cause instanceof FileUploadBase.SizeLimitExceededException) {
            SizeLimitExceededException flEx = (SizeLimitExceededException) cause;
            float permittedSize = flEx.getPermittedSize() / 1024 / 1024;
            message = "单个请求超过" + permittedSize + "MB";
        } else if (cause instanceof FileSizeLimitExceededException) {
            FileSizeLimitExceededException flEx = (FileSizeLimitExceededException) mEx.getCause().getCause();
            float permittedSize = flEx.getPermittedSize() / 1024 / 1024;
            message = "单个文件上传最大超过" + permittedSize + "MB";
        } else {
            message = "上传出现错误,请联系管理员: " + ex.getMessage();
        }
        return message;
    }


    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("e", e.getMessage());
        mv.addObject("uri", req.getRequestURI());
        return mv;
    }
}
