package com.timeshaft.after_end.service.userop;

import com.timeshaft.after_end.entity.User;
import com.timeshaft.after_end.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOp {
    @Autowired
    private UserService userService;
    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public User register(String email, String password, String username) {
        String savePassword = myPasswordEncoder.encode(password);
        User user = new User(email, savePassword, username, null);
        userService.insert(user);
        return user;
    }

    public User login(String email, String password) {
        logger.info("开始登录");
        User u = new User(email, null, null, null);
        List<User> users = userService.queryAll(u);
        User user = users.get(0);
        boolean log = myPasswordEncoder.matches(password, user.getPassword());
        if (log) return user;
        else return null;
    }

    public void changePwd(Integer user_id, String oldPassword, String newPassword) throws Exception {
        User user = userService.queryById(user_id);
        boolean flag = myPasswordEncoder.matches(oldPassword, user.getPassword());
        if (flag) {
            user.setPassword(myPasswordEncoder.encode(newPassword));
            userService.update(user);
        }
        else {
            throw new Exception();
        }
    }
}
