package com.timeshaft.after_end;

import com.timeshaft.after_end.controller.ChatController;
import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.userop.UserOp;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.OBJECT_NOT_EXIST;
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
    private ChatController chatController;

    @Test
    void contextLoads() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sourceId", 15);
        ResponseService responseService = chatController.getMessagesList(map);
        Object chatName = ((ArrayList<HashMap<String, Object>>) responseService.getData()).get(0);
        System.out.println(((HashMap<String, Object>) chatName).get("chatName"));
    }

}
