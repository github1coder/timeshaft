package com.timeshaft.after_end.controller;

import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.entity.MessageStateType;
import com.timeshaft.after_end.entity.PersonalMessage;
import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.impl.MessageStateServiceImpl;
import com.timeshaft.after_end.service.impl.PersonalMessageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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
    @Autowired
    private PersonalMessageServiceImpl personalMessageService;
    @Autowired
    private MessageStateServiceImpl messageStateService;

    @RequestMapping(value = "/getMessagesList")
    public ResponseService getMessagesList(@RequestBody Map<String, Object> requestMap) {
        int sourceId = (Integer) requestMap.get("srcId");
        List<Friends> friendsList = friendOp.getFriends(sourceId);
        List<HashMap<String, Object>> res = new ArrayList<>();
        for (Friends friends : friendsList) {
            HashMap<String, Object> map = new HashMap<>();
            Integer friendId = friends.getUserId1().equals(sourceId)? friends.getUserId2():friends.getUserId1();
            String chatName = friends.getUserId1().equals(sourceId)? friends.getNickname2():friends.getNickname1();
            String url = "/user/" + friendId + "/" + sourceId;
            String chatAvatar = "mdi-emoticon-kiss-outline";
            map.put("id", friendId);
            map.put("chatName", chatName);
            map.put("url", url);
            map.put("chatAvatar", chatAvatar);

            //拉取所有未读消息
            List<HashMap<String, Object>> data = new ArrayList<>();
            PersonalMessage messageQuery = new PersonalMessage();
            MessageStateType state = MessageStateType.UNREAD;
            messageQuery.setState(messageStateService.EnumToString(state));
            messageQuery.setSenderId(friendId);
            messageQuery.setFriendsId(sourceId);
            List<PersonalMessage> notReadMessages = personalMessageService.queryAll(messageQuery);
            int index = -1;
            if (notReadMessages != null && !notReadMessages.isEmpty()) {
                index = notReadMessages.get(0).getId();
            }
            for (PersonalMessage message : notReadMessages) {
                if (message.getId() < index) {
                    index = message.getId();
                }
                HashMap<String, Object> dataMap = new HashMap<>();
                dataMap.put("msgFromName", chatName);
                dataMap.put("msgFromAvatar", chatAvatar);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                dataMap.put("msg", message.getMessage());
                dataMap.put("time", sdf.format(message.getSendtime()));
                dataMap.put("srcId", message.getSenderId());
                dataMap.put("dstId", message.getFriendsId());
                data.add(dataMap);
            }
            //若没有已读消息，index应该为双方最近的一条消息
            if (index == -1) {
                PersonalMessage messageTo= personalMessageService.queryLatestById(friendId, sourceId);
                PersonalMessage messageFrom = personalMessageService.queryLatestById(sourceId, friendId);
                index = messageFrom.getId() > messageTo.getId() ? messageFrom.getId() : messageTo.getId();
            }
            map.put("data", data);
            map.put("index", index);
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
            String url = "/user/" + friendId + "/" + sourceId;
            map.put("id", friendId);
            map.put("url", url);
            data.add(map);
        }
        return new ResponseService(data);
    }

    @RequestMapping(value = "/getHistoryMessage")
    public ResponseService getHistoryMessage(@RequestParam(value = "srcId") Integer srcId,
                                             @RequestParam(value = "dstId") Integer dstId,
                                             @RequestParam(value = "index") Integer index) {


        return new ResponseService();
    }

    @RequestMapping(value = "/haveRead")
    public ResponseService markMessages(@RequestBody Map<String, Object> requestMap) {
        String time = (String) requestMap.get("time");
        int srcId = (Integer) requestMap.get("srcId");
        int dstId = (Integer) requestMap.get("dstId");
        PersonalMessage messageQuery = new PersonalMessage();
        MessageStateType state = MessageStateType.UNREAD;
        messageQuery.setState(messageStateService.EnumToString(state));
        messageQuery.setSenderId(srcId);
        messageQuery.setFriendsId(dstId);
        List<PersonalMessage> notReadMessages = personalMessageService.queryAll(messageQuery);
        PersonalMessage messageToSet = new PersonalMessage();
        messageToSet.setState(messageStateService.EnumToString(MessageStateType.READ));
        for (PersonalMessage message : notReadMessages) {
            int id = message.getId();
            messageToSet.setId(id);
            personalMessageService.update(messageToSet);
        }
        return new ResponseService();
    }
}
