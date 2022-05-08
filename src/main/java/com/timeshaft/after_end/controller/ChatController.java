package com.timeshaft.after_end.controller;

import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.entity.MessageStateType;
import com.timeshaft.after_end.entity.PersonalMessage;
import com.timeshaft.after_end.entity.User;
import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.impl.FriendsServiceImpl;
import com.timeshaft.after_end.service.impl.MessageStateServiceImpl;
import com.timeshaft.after_end.service.impl.PersonalMessageServiceImpl;
import com.timeshaft.after_end.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private FriendsServiceImpl friendsService;

    @RequestMapping(value = "/getMessagesList")
    public ResponseService getMessagesList(@RequestBody Map<String, Object> requestMap) {
        int sourceId = (Integer) requestMap.get("srcId");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Friends> friendsList = friendOp.getFriends(sourceId);
        List<HashMap<String, Object>> res = new ArrayList<>();
        for (Friends friends : friendsList) {
            HashMap<String, Object> map = new HashMap<>();
            Integer friendUserId = friends.getUserId1().equals(sourceId)? friends.getUserId2():friends.getUserId1();
            Integer friendId = friends.getId();
            String chatName = friends.getUserId1().equals(sourceId)? friends.getNickname2():friends.getNickname1();
            String url = "/user/" + friendId + "/" + sourceId;
            String chatAvatar = userService.queryById(friendUserId).getPhoto();
            map.put("id", friendId);
            map.put("chatName", chatName);
            map.put("url", url);
            map.put("chatAvatar", chatAvatar);
            String recent = null;
            //拉取所有未读消息
            List<HashMap<String, Object>> data = new ArrayList<>();
            PersonalMessage messageQuery = new PersonalMessage();
            MessageStateType state = MessageStateType.UNREAD;
            messageQuery.setState(messageStateService.EnumToString(state));
            messageQuery.setSenderId(friendUserId);
            messageQuery.setFriendsId(friendId);
            List<PersonalMessage> notReadMessages = personalMessageService.queryAll(messageQuery);
            int index = -1;
            if (notReadMessages != null && !notReadMessages.isEmpty()) {
                index = notReadMessages.get(0).getId();
            }
            if (notReadMessages != null && notReadMessages.size() > 0) {
                for (PersonalMessage message : notReadMessages) {
                    HashMap<String, Object> dataMap = new HashMap<>();
                    dataMap.put("msgFromName", chatName);
                    dataMap.put("msgFromAvatar", chatAvatar);
                    dataMap.put("msg", message.getMessage());
                    dataMap.put("time", sdf.format(message.getSendtime()));
                    dataMap.put("srcId", message.getSenderId());
                    dataMap.put("dstId", message.getFriendsId());
                    data.add(dataMap);
                }
                recent = sdf.format(notReadMessages.get(notReadMessages.size()-1).getSendtime());
                index = notReadMessages.get(0).getId();
            }
            //若没有已读消息，index应该为双方最近的一条消息+1 (因为索引的时候是使用 < 查询)
            if (index == -1) {
                PersonalMessage messageTo= personalMessageService.queryLatestById(friendId, sourceId);
                PersonalMessage messageFrom = personalMessageService.queryLatestById(friendId, friendUserId);
                if (messageTo == null) {
                    if (messageFrom != null) {
                        index = messageFrom.getId() + 1;
                        recent = sdf.format(messageFrom.getSendtime());
                    } else {
                        PersonalMessage messageRecent = personalMessageService.queryLatest();
                        index = messageRecent.getId() + 1;
                        recent = sdf.format(messageRecent.getSendtime());
                    }
                } else {
                    if (messageFrom == null) {
                        index = messageTo.getId() + 1;
                        recent = sdf.format(messageTo.getSendtime());
                    } else {
                        index = Math.max(messageFrom.getId(), messageTo.getId()) + 1;
                        recent = messageFrom.getId() > messageTo.getId() ? sdf.format(messageFrom.getSendtime()) : sdf.format(messageTo.getSendtime());
                    }
                }
            }
            //若没聊过天，index为-1——加好友会打招呼，此种情况不会发生
            map.put("data", data);
            map.put("index", index);
            map.put("recent", recent);
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
            Integer friendId = friends.getId();
            String url = "/user/" + friendId + "/" + sourceId;
            map.put("id", friendId);
            map.put("url", url);
            data.add(map);
        }
        return new ResponseService(data);
    }

    @RequestMapping(value = "/getHistoryMessage")
    public ResponseService getHistoryMessage(@RequestBody Map<String, Object> requestMap) {
        int srcId = (Integer) requestMap.get("srcId");
        int friendId = (Integer) requestMap.get("dstId");
        int index = (Integer) requestMap.get("index");
        Friends friends = friendsService.queryById(friendId);
        int dstId = friends.getUserId1() == srcId? friends.getUserId2():friends.getUserId1();
        String srcNickName = friends.getUserId1() == srcId? friends.getNickname1():friends.getNickname2();
        String dstNickName = friends.getUserId1() == dstId? friends.getNickname1():friends.getNickname2();
        User userSrc = userService.queryById(srcId);
        User userDst = userService.queryById(dstId);
        List<PersonalMessage> historyMessage = new ArrayList<>();
        historyMessage.addAll(personalMessageService.queryHistoryById(friendId, srcId, index));
        historyMessage.addAll(personalMessageService.queryHistoryById(friendId, dstId, index));
        historyMessage.sort(new Comparator<PersonalMessage>() {
            @Override
            public int compare(PersonalMessage o1, PersonalMessage o2) {
                return o2.getId().compareTo(o1.getId());
            }
        });
        int length = Math.min(historyMessage.size(), 20);
        boolean more = (length != 0);
        List<HashMap<String, Object>> data = new ArrayList<>();
        for (int i = length - 1; i >= 0; i--) {
            HashMap<String, Object> messageMap = new HashMap<>();
            PersonalMessage message = historyMessage.get(i);
            messageMap.put("srcId", message.getSenderId());
            messageMap.put("dstId", message.getFriendsId());
            messageMap.put("msg", message.getMessage());
            if (message.getSenderId() == srcId) {
                messageMap.put("msgFromName", srcNickName);
                messageMap.put("msgFromAvatar", userSrc.getPhoto());
            } else {
                messageMap.put("msgFromName", dstNickName);
                messageMap.put("msgFromAvatar", userDst.getPhoto());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            messageMap.put("time", sdf.format(message.getSendtime()));
            data.add(messageMap);
        }
        HashMap<String, Object> res = new HashMap<>();
        res.put("data", data);
        int newIndex = index;
        //若还有剩余的历史消息，则更新index
        if (more) {
            newIndex = historyMessage.get(length-1).getId();
        }
        res.put("index", newIndex);
        res.put("more", more);
        return new ResponseService(res);
    }

    @RequestMapping(value = "/haveRead")
    public ResponseService markMessages(@RequestBody Map<String, Object> requestMap) {
        String time = (String) requestMap.get("time");
        int srcId = (Integer) requestMap.get("srcId");
        int dstId = (Integer) requestMap.get("dstId");
        int userId = (Integer) requestMap.get("userId");
        PersonalMessage messageQuery = new PersonalMessage();
        MessageStateType state = MessageStateType.UNREAD;
        messageQuery.setState(messageStateService.EnumToString(state));
        int senderId;
        Friends friends;
        if (srcId == userId) {
            friends = friendsService.queryById(dstId);
        } else {
            friends = friendsService.queryById(srcId);
        }
        senderId = friends.getUserId1() == userId? friends.getUserId2():friends.getUserId1();
        messageQuery.setFriendsId(friends.getId());
        messageQuery.setSenderId(senderId);
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
