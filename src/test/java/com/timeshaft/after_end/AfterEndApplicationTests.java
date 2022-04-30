package com.timeshaft.after_end;

import com.timeshaft.after_end.controller.ChatController;
import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.userop.UserOp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

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
    private ChatController chatController;

    @Test
    void contextLoads() {
        ResponseService responseService = chatController.getMessagesList(15);
        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) responseService.getData();
        System.out.println(list.get(0).get("id"));
    }

}
