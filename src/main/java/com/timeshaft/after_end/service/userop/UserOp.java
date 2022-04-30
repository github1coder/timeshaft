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


    public User register(String email, String password, String username) throws Exception {
        User u = new User(email, null, null, null);
        List<User> users = userService.queryAll(u);
        if (users.size() > 0) {
            throw new Exception("邮箱已被注册");
        }
        String savePassword = myPasswordEncoder.encode(password);
//        String baseURL = "http://182."
        User user = new User(email, savePassword, username, null);
        userService.insert(user);
        return user;
    }

    public User login(String email, String password) throws Exception {
        User u = new User(email, null, null, null);
        List<User> users = userService.queryAll(u);
        User user = users.get(0);
        boolean log = myPasswordEncoder.matches(password, user.getPassword());
        if (!log) {
            throw new Exception("密码错误");
        }
        return user;
    }

    public void changePwd(Integer user_id, String oldPassword, String newPassword) throws Exception {
        User user = userService.queryById(user_id);
        boolean flag = myPasswordEncoder.matches(oldPassword, user.getPassword());
        if (flag) {
            user.setPassword(myPasswordEncoder.encode(newPassword));
            userService.update(user);
        }
        else {
            throw new Exception("密码错误");
        }
    }
}
