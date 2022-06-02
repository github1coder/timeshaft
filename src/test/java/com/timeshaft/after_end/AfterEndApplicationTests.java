package com.timeshaft.after_end;

import com.timeshaft.after_end.controller.ChatController;
import com.timeshaft.after_end.entity.*;
import com.timeshaft.after_end.service.*;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.addressList.GroupOp;
import com.timeshaft.after_end.service.userop.MailService;
import com.timeshaft.after_end.service.userop.MyPasswordEncoder;
import com.timeshaft.after_end.service.userop.UserOp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@SpringBootTest
@Service
class AfterEndApplicationTests {
    @Autowired
    private FriendOp friendOp;
    @Autowired
    private GroupOp groupOp;
    @Autowired
    private UserOp userOp;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatController chatController;
    @Autowired
    private GroupUserService groupUserService;
    @Autowired
    private MailService mailService;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private TimeshaftService timeshaftService;
    @Autowired
    private GroupHeatService groupHeatService;
    @Autowired
    private FriendsService friendsService;
    @Value("${friendState.acceptt}")
    private String ACCEPT;
    @Value("${groupIdentity.member}")
    private String MEMBER;
    @Value("${type.friendType}")
    private String FRIEND;


    @Test
    void contextLoads() {

    }
}
