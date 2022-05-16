package com.timeshaft.after_end.aop;


import com.timeshaft.after_end.annotation.PermissionAnnotation;
import com.timeshaft.after_end.entity.GroupUser;
import com.timeshaft.after_end.service.GroupUserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
@Slf4j
public class PermissionAOP {
    @Autowired
    private GroupUserService groupUserService;

    // 定义一个切面，括号内自定义注解的路径
    @Pointcut("@annotation(com.timeshaft.after_end.annotation.PermissionAnnotation)")
    private void permissionCheck() {
    }

    @Before(value = "permissionCheck() && @annotation(permissionAnnotation)")
    public void permission(JoinPoint joinPoint, PermissionAnnotation permissionAnnotation) throws Exception {
        Integer user_id = (Integer) simpleResolver(joinPoint, "user_id");
        Integer group_id = (Integer) simpleResolver(joinPoint, "group_id");
        groupUserService.hasPower(new GroupUser(group_id, user_id, null, null ,null), permissionAnnotation.level());
    }

    private Object simpleResolver(JoinPoint joinPoint, String str) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] names = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < names.length; i++) {
            if (str.equals(names[i])) {
                return args[i];
            }
        }
        return null;
    }
}
