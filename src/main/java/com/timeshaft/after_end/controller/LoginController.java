package com.timeshaft.after_end.controller;

import com.timeshaft.after_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


//@Controller
@RestController //等价于@Controller+@ResponseBody的结合，里面的方法都以json格式输出
public class LoginController {
    @Autowired
    private UserService userService;
//
//    @RequestMapping("")
//    public Object login(@RequestParam(value = "email") String email,
//                        @RequestParam(value = "password") String password,
//                        Model model,
//                        HttpSession session
//    ) {
////        if (userService.login()) {
////
////        }
////        return "";
//    }

//    @RequestMapping("")
//    public Object logout(Model model,
//                         HttpSession session
//    ) {
//
//        return "";
//    }
}
