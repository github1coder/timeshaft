package com.timeshaft.after_end;

import com.timeshaft.after_end.controller.ChatController;
import com.timeshaft.after_end.controller.MessageController;
import com.timeshaft.after_end.service.GroupUserService;
import com.timeshaft.after_end.service.UserService;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.addressList.GroupOp;
import com.timeshaft.after_end.service.userop.MailService;
import com.timeshaft.after_end.service.userop.UserOp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;


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



    @Test
    void contextLoads() {

    }
}
