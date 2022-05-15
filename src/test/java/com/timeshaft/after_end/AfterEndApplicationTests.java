package com.timeshaft.after_end;

import com.timeshaft.after_end.controller.ChatController;
import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.entity.GroupUser;
import com.timeshaft.after_end.entity.User;
import com.timeshaft.after_end.service.GroupUserService;
import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.UserService;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.userop.MailService;
import com.timeshaft.after_end.service.userop.MyPasswordEncoder;
import com.timeshaft.after_end.service.userop.UserOp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
    @Autowired
    private MailService mailService;

    @Test
    void contextLoads() throws Exception {
//        for(int i = 30; i < 162; i++) {
//            User user = userService.queryById(i);
//            MyPasswordEncoder encoder = new MyPasswordEncoder();
//            String str = randomPassword(9);
//            user.setPassword(encoder.encode(str));
//            userService.update(user);
//        }
        userOp.login("123456@qq.com", "123456");
    }

    private static final int MIN_NUMBER = 33;
    private static final int MAX_NUMBER = 126;
    private static final int BOUND = MAX_NUMBER - MIN_NUMBER + 1;

    /**
     * 随机生成指定位数的密码
     *
     * char[33,126]，可表示数字、大小写字母、特殊字符
     *
     * @param length 密码长度
     */
    public static String randomPassword(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char value = (char) (random.nextInt(BOUND) + MIN_NUMBER);
            builder.append(value);
        }
        return builder.toString();
    }

}
