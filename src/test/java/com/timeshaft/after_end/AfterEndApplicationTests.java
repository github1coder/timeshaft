package com.timeshaft.after_end;

import com.timeshaft.after_end.service.UserService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@SpringBootTest
@MapperScan
class AfterEndApplicationTests {

    @Test
    void contextLoads() {
    }

}
