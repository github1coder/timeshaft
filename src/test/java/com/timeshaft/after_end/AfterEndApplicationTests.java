package com.timeshaft.after_end;

import com.timeshaft.after_end.controller.ChatController;
import com.timeshaft.after_end.entity.Group;
import com.timeshaft.after_end.service.GroupService;
import com.timeshaft.after_end.service.GroupUserService;
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
    private GroupService groupService;
    @Autowired
    private ChatController chatController;
    @Autowired
    private GroupUserService groupUserService;

    @Test
    void contextLoads() {
        System.out.println("aaaaa");
        Group group = groupService.queryById(7);
        System.out.println("aaaaa");
    }
}
