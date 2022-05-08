package com.timeshaft.after_end.config;

import com.timeshaft.after_end.entity.UserToken;
import com.timeshaft.after_end.service.UserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Component
@Slf4j
public class MyHandlerIntercepter implements HandlerInterceptor {
    @Autowired
    private UserTokenService userTokenService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(userTokenService == null){
            BeanFactory factory = WebApplicationContextUtils
                    .getRequiredWebApplicationContext(request.getServletContext());
            userTokenService = (UserTokenService) factory
                    .getBean("userTokenService");
        }

        Integer user_id = Integer.parseInt(request.getHeader("user_id"));
        String token = request.getHeader("ACCESS_TOKEN");
        List<UserToken> tokens = userTokenService.queryAll(new UserToken(user_id, token));
        boolean flag = tokens.size() > 0;
        if(!flag){
//            request.getRequestDispatcher("/signin").forward(request, response);
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
