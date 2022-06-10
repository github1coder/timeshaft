package com.timeshaft.after_end.aop;

import com.timeshaft.after_end.entity.RequestInfo;
import com.timeshaft.after_end.service.RequestInfoService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 日志记录
 *
 * @author ouyangyz
 * @create 2022-03-07 10:50
 */
@Aspect
@Component
public class RequestLogAOP {

    @Autowired
    private RequestInfoService requestInfoService;

    @Pointcut("@annotation(com.timeshaft.after_end.annotation.RequestLog)")
    public void logPointCut() {

    }

    //统计请求的处理时间
    Long startTime = null;

    @Before("logPointCut()")
    public void beforeRequest() {
        System.out.println("请求切面");
        startTime = System.currentTimeMillis();
    }

    @AfterReturning(value = "logPointCut()", returning = "result")
    public void saveLog(JoinPoint joinPoint, Object result) {
        //保存日志
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();

        String user_id = request.getHeader("user_id");
        if (user_id == null || user_id.equals("")) {
            user_id="-1";
        }

        RequestInfo requestInfo = new RequestInfo(Integer.valueOf(user_id), request.getRequestURL().toString(), new Date(startTime), System.currentTimeMillis()-startTime, 0);
        requestInfoService.insert(requestInfo);
    }
}

