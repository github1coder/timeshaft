package com.timeshaft.after_end.controller;

import com.timeshaft.after_end.entity.User;
import com.timeshaft.after_end.service.userop.MailService;
import com.timeshaft.after_end.service.userop.UserOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Random;


//@Controller
@RestController //等价于@Controller+@ResponseBody的结合，里面的方法都以json格式输出
@RequestMapping("/user")  //映射URL
@SessionAttributes(value={"user_id"})
public class UserController {
    @Autowired
    private UserOp userOp;
    @Autowired
    private MailService mailService;

    @RequestMapping("/getCheckCode")
    public String getCheckCode(@RequestParam(value = "email") String email){
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的注册验证码为："+checkCode;
        try {
            mailService.sendSimpleMail(email, "注册验证码", message);
        }catch (Exception e){
            return "注册验证码发送失败";
        }
        return checkCode;
    }

    @RequestMapping("/register")
    public Boolean register(@RequestParam(value = "email") String email,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "username") String username
    ) {
        User user = userOp.register(email, password, username);
        return true;
    }

    @RequestMapping(value = "/loginn")
    public User login(@RequestBody Map<String, String> map, Model model) {
        User user = userOp.login(map.get("email"), map.get("password"));
        model.addAttribute("user_id", user.getId());
        return user;
    }

    @RequestMapping("/logout")
    public boolean logout(Model model) {
        model.addAttribute("user_id", null);
        return true;
    }

    @RequestMapping(value = "/changePwd")
    public boolean changePwd(@RequestBody Map<String, String> map, @SessionAttribute("user_id") Integer user_id) {
        return userOp.changePwd(user_id, map.get("oldPassword"), map.get("newPassword"));
    }
}
