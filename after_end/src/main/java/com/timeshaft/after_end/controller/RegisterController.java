package com.timeshaft.after_end.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class RegisterController {

    @RequestMapping("")
    public String register(@RequestParam(value = "account") String account,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "username") String username,
                           Model model,
                           HttpSession session
    ) {

        return "";
    }
}
