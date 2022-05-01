package com.timeshaft.after_end.controller;

import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.addressList.FriendOp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseService getMessagesList(@RequestBody Map<String, Object> requestMap) {
        int sourceId = (Integer) requestMap.get("srcId");
        List<Friends> friendsList = friendOp.getFriends(sourceId);
        List<HashMap<String, Object>> res = new ArrayList<>();
        for (Friends friends : friendsList) {
            HashMap<String, Object> map = new HashMap<>();
            Integer friendId = friends.getUserId1().equals(sourceId)? friends.getUserId2():friends.getUserId1();
            String chatName = friends.getUserId1().equals(sourceId)? friends.getNickname2():friends.getNickname1();
            String url = "/user/" + friendId + "-" + sourceId;
            String chatAvatar = "mdi-emoticon-kiss-outline";
            map.put("id", friendId);
            map.put("chatName", chatName);
            map.put("url", url);
            map.put("chatAvatar", chatAvatar);
            //拉取双方加起来最近20条聊天记录
            List<HashMap<String, Object>> data = new ArrayList<>();
            map.put("data", data);
            res.add(map);
        }
        return new ResponseService(res);
    }

    @RequestMapping(value = "/getSubscribeUrlList")
    public ResponseService getSubscribeUrlList(@RequestBody Map<String, Object> requestMap) {
        int sourceId = (Integer) requestMap.get("srcId");
        List<Friends> friendsList = friendOp.getFriends(sourceId);
        List<HashMap<String, Object>> data = new ArrayList<>();
        for (Friends friends : friendsList) {
            HashMap<String, Object> map = new HashMap<>();
            Integer friendId = friends.getUserId1().equals(sourceId) ? friends.getUserId2():friends.getUserId1();
            String chatName = friends.getUserId1().equals(sourceId) ? friends.getNickname2():friends.getNickname1();
            String url = "/user/" + friendId + "-" + sourceId;
            map.put("id", friendId);
            map.put("url", url);
            data.add(map);
        }
        return new ResponseService(data);
    }
}
