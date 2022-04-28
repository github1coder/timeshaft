package com.timeshaft.after_end.controller;

import com.timeshaft.after_end.entity.User;
import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.userop.MailService;
import com.timeshaft.after_end.service.userop.UserOp;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


//@Controller
@RestController //等价于@Controller+@ResponseBody的结合，里面的方法都以json格式输出
@RequestMapping("/user")  //映射URL
@CrossOrigin
@Slf4j
public class UserController {
    @Autowired
    private UserOp userOp;
    @Autowired
    private MailService mailService;

    @RequestMapping("/getCheckCode")
    public ResponseService getCheckCode(@RequestParam(value = "email") String email){
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的注册验证码为："+checkCode;
        mailService.sendSimpleMail(email, "注册验证码", message);
        Map<String, String> res = new HashMap<>();
        res.put("checkCode", checkCode);
        return new ResponseService(res);
    }

    @RequestMapping("/register")
    public ResponseService register(@RequestBody Map<String, String> requestMap, HttpSession session) throws Exception {
        log.info("注册开始");
        String email = requestMap.get("email");
        String password = requestMap.get("password");
        String username = requestMap.get("username");
        User user = userOp.register(email, password, username);
        session.setAttribute("user_id", user.getId());
        log.info("注册成功");
        return new ResponseService(user);
    }

    @RequestMapping(value = "/loginn")
    public ResponseService login(@RequestBody Map<String, String> map, HttpSession session) throws Exception {
        log.info("开始登录");
        User user = userOp.login(map.get("email"), map.get("password"));
        session.setAttribute("user_id", user.getId());
        log.info("登录成功");
        return new ResponseService(user);
    }

    @RequestMapping("/logout")
    public ResponseService logout(HttpSession session) {
        session.removeAttribute("user_id");
        return new ResponseService();
    }

    @RequestMapping(value = "/changePwd")
    public ResponseService changePwd(@RequestBody Map<String, String> map, @SessionAttribute("user_id") Integer user_id) throws Exception {
        //Integer uid = (Integer) session.getAttribute(Const.SESSION_UID);
        userOp.changePwd(user_id, map.get("oldPassword"), map.get("newPassword"));
        return new ResponseService();
    }
}
