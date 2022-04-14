package com.timeshaft.after_end;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@MapperScan
@Controller
@CrossOrigin
public class AfterEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(AfterEndApplication.class, args);
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "Hello";
    }

}
