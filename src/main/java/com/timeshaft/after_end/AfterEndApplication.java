package com.timeshaft.after_end;

import com.timeshaft.after_end.service.ResponseService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@MapperScan("com.timeshaft.after_end.mapper")
@Controller
@CrossOrigin
public class AfterEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(AfterEndApplication.class, args);
    }

    @RequestMapping("/test")
    @ResponseBody
    public ResponseService test() {
//        User user = new User("test","test","test","test");
//        userService.insert(user);
        Map<String, Integer> temp = new HashMap<>();
        temp.put("test", 0);
        Map<String, Map<String, Integer>> res = new HashMap<>();
        res.put("res", temp);
        return new ResponseService<>(ResponseService.Code.SUCCESS, res, "成功");
    }

}
