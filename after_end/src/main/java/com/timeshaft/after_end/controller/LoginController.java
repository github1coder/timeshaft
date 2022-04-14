package com.timeshaft.after_end.controller;

import com.timeshaft.after_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
public class LoginController {
//    @Autowired
//    UserService userService;
//
//    @GetMapping("")
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
//
//    @GetMapping("")
//    public Object logout(Model model,
//                         HttpSession session
//    ) {
//
//        return "";
//    }
}
