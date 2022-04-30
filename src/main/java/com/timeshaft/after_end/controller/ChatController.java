package com.timeshaft.after_end.controller;

import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.addressList.FriendOp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * feishu
 * 处理用户聊天
 *
 * @author : dxt
 * @since : 2022-04-29 13:25
 **/
@RestController //等价于@Controller+@ResponseBody的结合，里面的方法都以json格式输出
@RequestMapping("/message")  //映射URL
@CrossOrigin
@Slf4j
public class ChatController {
    @Autowired
    private FriendOp friendOp;

    @RequestMapping(value = "/getMessagesList")
    public ResponseService getMessagesList(@RequestParam(value = "sourceId") Integer sourceId) {
        List<Friends> friendsList = friendOp.getFriends(sourceId);
        List<HashMap<String, Object>> data = new ArrayList<>();
        for (Friends friends : friendsList) {
            HashMap<String, Object> map = new HashMap<>();
            Integer friendId = friends.getUserId1().equals(sourceId) ? friends.getUserId2():friends.getUserId1();
            String chatName = friends.getUserId1().equals(sourceId) ? friends.getNickname2():friends.getNickname1();
            String url = "/user/" + friendId + "-" + sourceId;
            String chatAvatar = "mdi-emoticon-kiss-outline";
            map.put("id", friendId);
            map.put("chatName", chatName);
            map.put("url", url);
            map.put("chatAvatar", chatAvatar);
            data.add(map);
        }
        return new ResponseService(data);
    }
}
