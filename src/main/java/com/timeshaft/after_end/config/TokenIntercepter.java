package com.timeshaft.after_end.config;

import com.timeshaft.after_end.entity.UserToken;
import com.timeshaft.after_end.service.UserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


@Component
@Slf4j
@CrossOrigin
public class TokenIntercepter implements HandlerInterceptor {
    @Autowired
    private UserTokenService userTokenService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            System.out.println("OPTIONS请求，放行");
            return true;
        }

        if(userTokenService == null){
            BeanFactory factory = WebApplicationContextUtils
                    .getRequiredWebApplicationContext(request.getServletContext());
            userTokenService = (UserTokenService) factory
                    .getBean("userTokenService");
        }

        Integer user_id = Integer.parseInt(request.getHeader("user_id"));
        String token = request.getHeader("ACCESS_TOKEN");
        boolean flag = userTokenService.isLogin(user_id, token);
        if(!flag){
            log.info("ACCESS_TOKEN不对哦");
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"code\":40001,\"msg\":\"ACCESS_TOKEN不对哦\"}");
            response.getWriter().flush();
            return false;
        }
        else {
            log.info("ACCESS_TOKEN居然通过咯");
            return true;
        }
    }
}
