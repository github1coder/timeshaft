package com.timeshaft.after_end;

import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.service.addressList.FriendOp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.List;

@SpringBootTest
@Service
class AfterEndApplicationTests {
    @Autowired
    private FriendOp friendOp;

    @Test
    void contextLoads() {
        List<Friends> friends = friendOp.getFriends(125);
        for(Friends friend : friends) {
            if(friend.getUserId1() != 125) {
                System.out.println(friend.getUserId1());
            } else {
                System.out.println(friend.getUserId2());
            }
        }
    }

}
