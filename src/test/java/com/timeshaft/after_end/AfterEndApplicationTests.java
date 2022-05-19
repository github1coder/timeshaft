package com.timeshaft.after_end;

import com.timeshaft.after_end.controller.ChatController;
import com.timeshaft.after_end.entity.PersonalMessage;
import com.timeshaft.after_end.entity.Timeshaft;
import com.timeshaft.after_end.entity.User;
import com.timeshaft.after_end.service.GroupUserService;
import com.timeshaft.after_end.service.PersonalMessageService;
import com.timeshaft.after_end.service.TimeshaftService;
import com.timeshaft.after_end.service.UserService;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.addressList.GroupOp;
import com.timeshaft.after_end.service.userop.MailService;
import com.timeshaft.after_end.service.userop.MyPasswordEncoder;
import com.timeshaft.after_end.service.userop.UserOp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PersonalMessageService personalMessageService;



    @Test
    void contextLoads() {
        for (int i = 68; i < 97; i++) {
            if(timeshaftService.queryById(i) != null) {
                Timeshaft timeshaft = timeshaftService.queryById(i);
                List<PersonalMessage> personalMessages = personalMessageService.queryTimeshaft(timeshaft);
                if(!personalMessages.isEmpty()) {
                    timeshaft.setStartMsgId(personalMessages.get(0).getId());
                    timeshaft.setEndMsgId(personalMessages.get(personalMessages.size() - 1).getId());
                } else {
                    timeshaft.setStartMsgId(-1);
                    timeshaft.setEndMsgId(-1);
                }
                timeshaftService.update(timeshaft);
            }
        }
    }
}
