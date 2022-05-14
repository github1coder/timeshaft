package com.timeshaft.after_end.annotation;

import java.lang.annotation.*;
//自定义权限注解
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionAnnotation {
    int level() default -1;
}
