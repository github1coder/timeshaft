package com.timeshaft.after_end.aop;


import com.timeshaft.after_end.annotation.PermissionAnnotation;
import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.entity.GroupUser;
import com.timeshaft.after_end.entity.Timeshaft;
import com.timeshaft.after_end.service.FriendsService;
import com.timeshaft.after_end.service.GroupUserService;
import com.timeshaft.after_end.service.TimeshaftService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;


@Aspect
@Component
@Slf4j
public class PermissionAOP {
    @Autowired
    private GroupUserService groupUserService;
    @Autowired
    private TimeshaftService timeshaftService;
    @Autowired
    private FriendsService friendsService;

    @Value("${type.friendType}")
    private String FRIEND;
    @Value("${type.groupType}")
    private String GROUP;
    @Value("${groupIdentity.manager}")
    private String MANAGER;
    @Value("${groupIdentity.master}")
    private String MASTER;

    // 定义一个切面，括号内自定义注解的路径
    @Pointcut("@annotation(com.timeshaft.after_end.annotation.PermissionAnnotation)")
    private void permissionCheck() {
    }

    @Before(value = "permissionCheck() && @annotation(permissionAnnotation)")
    public void permission(JoinPoint joinPoint, PermissionAnnotation permissionAnnotation) throws Exception {
        if (permissionAnnotation.level() > 10 && permissionAnnotation.level() < 20) { //zzy
            Integer user_id = (Integer) simpleResolver(joinPoint, "user_id");
            Integer group_id = (Integer) simpleResolver(joinPoint, "group_id");
            groupUserService.hasPower(new GroupUser(group_id, user_id, null, null, null), permissionAnnotation.level());
        } else if (permissionAnnotation.level() > 20 && permissionAnnotation.level() < 30) { //dxt

        } else if (permissionAnnotation.level() > 30 && permissionAnnotation.level() < 40) {  //czr
            if (permissionAnnotation.level() < 38) {
                Integer user_id = (Integer) simpleResolver(joinPoint, "user_id");
                Integer timeshaft_id = (Integer) simpleResolver(joinPoint, "timeshaft_id");
                if (permissionAnnotation.level() == 31) {
                    timeshaftPermission_1(user_id, timeshaft_id);
                } else if (permissionAnnotation.level() == 32) {
                    timeshaftPermission_2(user_id, timeshaft_id);
                }
            } else {
                if (permissionAnnotation.level() == 38) {
                    Integer user_id = (Integer) simpleResolver(joinPoint, "user_id");
                    Integer group_id = (Integer) simpleResolver(joinPoint, "group_id");
                    String type = (String) simpleResolver(joinPoint, "type");
                    timeshaftPermission_8(user_id, group_id, type);
                }
            }
        }
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

    private void timeshaftPermission_1(Integer user_id, Integer timeshaft_id) throws Exception {
        Timeshaft timeshaft = timeshaftService.queryById(timeshaft_id);
        if (timeshaft.getPrivate1() == 1) {
            if (timeshaft.getType().equals(FRIEND)) {
                Friends friend = friendsService.queryById(timeshaft.getGroupId());
                if(!user_id.equals(friend.getUserId1()) && !user_id.equals(friend.getUserId2())) {
                    throw new Exception();
                }
            } else if (timeshaft.getType().equals(GROUP)) {
                List<GroupUser> groupUsers = groupUserService.queryAll(new GroupUser(timeshaft.getGroupId(), user_id, null, null, "accept"));
                if (groupUsers.isEmpty()) {
                    throw new Exception();
                }
            } else {
                throw new Exception("type参数错误");
            }
        }
    }

    private void timeshaftPermission_2(Integer user_id, Integer timeshaft_id) throws Exception {
        Timeshaft timeshaft = timeshaftService.queryById(timeshaft_id);
        if (timeshaft.getType().equals(FRIEND)) {
            Friends friend = friendsService.queryById(timeshaft.getGroupId());
            if(!user_id.equals(friend.getUserId1()) && !user_id.equals(friend.getUserId2())) {
                throw new Exception();
            }
        } else if (timeshaft.getType().equals(GROUP)) {
            List<GroupUser> groupUsers = groupUserService.queryAll(new GroupUser(timeshaft.getGroupId(), user_id, null, MANAGER, null));
            groupUsers.addAll(groupUserService.queryAll(new GroupUser(timeshaft.getGroupId(), user_id, null, MASTER, null)));
            if (groupUsers.isEmpty()) {
                throw new Exception();
            }
        } else {
            throw new Exception("type参数错误");
        }
    }

    private void timeshaftPermission_8(Integer user_id, Integer group_id, String type) throws Exception {
        if (FRIEND.equals(type)) {
            Friends friend = friendsService.queryById(group_id);
            if(!user_id.equals(friend.getUserId1()) && !user_id.equals(friend.getUserId2())) {
                throw new Exception();
            }
        } else if (GROUP.equals(type)) {
            List<GroupUser> groupUsers = groupUserService.queryAll(new GroupUser(group_id, user_id, null, MANAGER, null));
            groupUsers.addAll(groupUserService.queryAll(new GroupUser(group_id, user_id, null, MASTER, null)));
            if (groupUsers.isEmpty()) {
                throw new Exception();
            }
        } else {
            throw new Exception("type参数错误");
        }
    }
}
