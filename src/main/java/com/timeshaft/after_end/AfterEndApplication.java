package com.timeshaft.after_end;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan
public class AfterEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(AfterEndApplication.class, args);
    }

}
