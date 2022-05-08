package com.timeshaft.after_end;

import com.timeshaft.after_end.controller.ChatController;
import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.entity.GroupUser;
import com.timeshaft.after_end.entity.User;
import com.timeshaft.after_end.service.GroupUserService;
import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.UserService;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.userop.UserOp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@Service
class AfterEndApplicationTests {
    @Autowired
    private FriendOp friendOp;
    @Autowired
    private UserOp userOp;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatController chatController;
    @Autowired
    private GroupUserService groupUserService;

    @Test
    void contextLoads() {
        friendOp.apply(18, "user", "new", 23);
    }
}
