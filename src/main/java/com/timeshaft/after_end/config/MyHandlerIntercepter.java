package com.timeshaft.after_end.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyHandlerIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Object user_id = request.getSession().getAttribute("user_id");
        if(user_id == null){
            //request.getRequestDispatcher("/signin").forward(request, response);
            System.out.println("请求鉴权未通过");
            return false;
        }
        else {
            System.out.println("请求鉴权通过");
            return true;
        }
    }
}
