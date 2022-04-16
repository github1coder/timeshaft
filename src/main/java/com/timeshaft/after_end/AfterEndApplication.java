package com.timeshaft.after_end;

import com.timeshaft.after_end.entity.User;
import com.timeshaft.after_end.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@SpringBootApplication
@MapperScan("com.timeshaft.after_end.mapper")
@Controller
@CrossOrigin
public class AfterEndApplication {
    @Resource(name="UserService")
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(AfterEndApplication.class, args);
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        User user = new User("test","test","test","test");

        userService.insert(user);
        return "Hello World";
    }

}
