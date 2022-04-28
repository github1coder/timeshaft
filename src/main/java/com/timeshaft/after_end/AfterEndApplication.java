package com.timeshaft.after_end;

import com.timeshaft.after_end.service.ResponseService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@MapperScan("com.timeshaft.after_end.mapper")
@RestController
@CrossOrigin
public class AfterEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(AfterEndApplication.class, args);
    }

    @RequestMapping("/test")
    public ArrayList<HashMap<String,String>> test() {
        System.out.println("----------------------------------");
        ArrayList<HashMap<String,String>> res= new ArrayList<>();
        HashMap<String,String> resMap= new HashMap<>();
        resMap.put("id","1");
        resMap.put("name","1");
        resMap.put("content","1");
        resMap.put("status","1");
        res.add(null);
        res.add(resMap);
        return res;
    }

}
