package com.timeshaft.after_end.config;

import com.timeshaft.after_end.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

/**
 * 自定义异常类
 */
@Slf4j
@RestControllerAdvice
@ControllerAdvice
public class MyExceptionHandlerConfig {
    /**
     * 未知异常处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseService exceptionHandler(Exception e) {
        // 把错误信息输入到日志中
        log.error("出错了，小笨蛋",e);
        String msg = e.getMessage();
        if (msg == null) {
            msg = "你没有权限进行此操作哦";
        }
        return new ResponseService(ResponseService.Code.ERROR, null, msg);
    }
}
