package com.timeshaft.after_end.controller;

import com.timeshaft.after_end.entity.*;
import com.timeshaft.after_end.service.GroupUserService;
import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.addressList.GroupOp;
import com.timeshaft.after_end.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private GroupOp groupOp;
    @Autowired
    private PersonalMessageServiceImpl personalMessageService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private FriendsServiceImpl friendsService;
    @Autowired
    private GroupMessageStateServiceImpl groupMessageStateService;
    @Autowired
    private GroupMessageServiceImpl groupMessageService;
    @Autowired
    private GroupUserServiceImpl groupUserService;
    @Autowired
    private GroupServiceImpl groupService;

    @Value("${type.messageRead}")
    private String READ;
    @Value("${type.messageNotRead}")
    private String UNREAD;
    @Value("${type.groupType}")
    private String GROUP;
    @Value("${type.friendType}")
    private String FRIEND;
    @Value("${type.textType}")
    private String TEXT;
    @Value("${type.timeShaftType}")
    private String TIMESHAFT;
    @Value("${meeting.on}")
    private String onMeeting;
    @Value("${meeting.off}")
    private String offMeeting;

    @RequestMapping(value = "/getMessagesList")
    public ResponseService getMessagesList(@RequestBody Map<String, Object> requestMap) {
        int sourceId = (Integer) requestMap.get("srcId");
        //获取私聊相关信息
        List<Friends> friendsList = friendOp.getFriends(sourceId);
        List<HashMap<String, Object>> res = new ArrayList<>();
        for (Friends friends : friendsList) {
            HashMap<String, Object> map = new HashMap<>();
            Integer friendUserId = friends.getUserId1().equals(sourceId)? friends.getUserId2():friends.getUserId1();
            Integer friendId = friends.getId();
            String chatName = friends.getUserId1().equals(sourceId)? friends.getNickname2():friends.getNickname1();
            String chatAvatar = userService.queryById(friendUserId).getPhoto();
            map.put("id", friendId);
            map.put("isMeeting", friends.getStatus().equals(onMeeting));
            map.put("chatName", chatName);
            map.put("chatAvatar", chatAvatar);
            map.put("type", FRIEND);
            PersonalMessage messageTo= personalMessageService.queryLatestById(friendId, sourceId);
            PersonalMessage messageFrom = personalMessageService.queryLatestById(friendId, friendUserId);
            //拉取最近一条消息
            HashMap<String, Object> lastMessage = new HashMap<>();
            PersonalMessage last = null;
            Date lastTime = new Date(System.currentTimeMillis());
            if (messageTo == null) {
                if (messageFrom != null) {
                    last = messageFrom;
                }
            } else {
                if (messageFrom == null) {
                    last = messageTo;
                } else {
                    if (messageFrom.getId() < messageTo.getId()) {
                        last = messageTo;
                    } else {
                        last = messageFrom;
                    }
                }
            }
            String msg = null;
            Date time = null;
            int msgId = 0;
            String msgType = null;
            if (last != null) {
                msg = last.getMessage();
                time = last.getSendtime();
                msgId = last.getId();
                if (msg.startsWith("#")) {
                    msgType = TIMESHAFT;
                } else {
                    msgType = TEXT;
                }
            }
            lastMessage.put("msg", msg);
            lastMessage.put("time", time);
            lastMessage.put("msgId", msgId);
            lastMessage.put("msgType", msgType);
            PersonalMessage messageQuery = new PersonalMessage();
            messageQuery.setState(UNREAD);
            messageQuery.setSenderId(friendUserId);
            messageQuery.setFriendsId(friendId);
            List<PersonalMessage> notReadMessages = personalMessageService.queryAll(messageQuery);
            int number = notReadMessages.size();
            map.put("number", number);
            //若没聊过天，index为-1——加好友会打招呼，此种情况不会发生
            map.put("lastMessage", lastMessage);
            map.put("lastTime", lastTime);
            res.add(map);
        }
        //获取群聊相关信息
        List<Group> groupList = groupOp.getGroup(sourceId);
        for (Group group : groupList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", group.getId());
            map.put("chatName", group.getName());
            map.put("chatAvatar", group.getPhoto());
            map.put("type", GROUP);
            map.put("isMeeting", group.getStatus().equals(onMeeting));
            GroupMessage latest = groupMessageService.queryLatestById(group.getId());
            Date lastTime = new Date(System.currentTimeMillis());
            HashMap<String, Object> lastMessage = new HashMap<>();
            String msg = null;
            Date time = null;
            int msgId = 0;
            String msgType = null;
            if (latest != null) {
                msg = latest.getMessage();
                time = latest.getSendtime();
                msgId = latest.getId();
                if (msg.startsWith("#")) {
                    msgType = TIMESHAFT;
                } else {
                    msgType = TEXT;
                }
            }
            lastMessage.put("msg", msg);
            lastMessage.put("time", time);
            lastMessage.put("msgId", msgId);
            lastMessage.put("msgType", msgType);
            List<GroupMessage> notReadMessages = groupMessageService.queryNotReadMessage(sourceId, group.getId(), UNREAD);
            int number = notReadMessages.size();
            map.put("number", number);
            map.put("lastMessage", lastMessage);
            map.put("lastTime", lastTime);
            res.add(map);
        }
        return new ResponseService(res);
    }

    @RequestMapping(value = "/getListenerList")
    public ResponseService getListenerList(@RequestHeader("user_id") Integer user_id) {
        //消息url
        String chatUrl = "/user/chat/" + user_id;
        HashMap<String, Object> chatMap = new HashMap<>();
        chatMap.put("type", 0);
        chatMap.put("url", chatUrl);
        //好友通知url
        String friendUrl = "/user/contact/" + user_id;
        HashMap<String, Object> friendMap = new HashMap<>();
        friendMap.put("type", 1);
        friendMap.put("url", friendUrl);
        //会议通知url
        String timeShaftUrl = "/user/timeshaft/" + user_id;
        HashMap<String, Object> timeShaftMap = new HashMap<>();
        friendMap.put("type", 2);
        friendMap.put("url", timeShaftUrl);

        ArrayList<HashMap<String, Object>> res = new ArrayList<>();
        res.add(chatMap);
        res.add(friendMap);
        res.add(timeShaftMap);
        return new ResponseService(res);
    }

    @RequestMapping(value = "/getHistoryMessage")
    public ResponseService getHistoryMessage(@RequestParam("userId") Integer userId,
                                             @RequestParam("lastTime") String lastTime,
                                             @RequestParam("chatId") Integer chatId,
                                             @RequestParam("type") String type,
                                             @RequestParam("first") Integer first) {
        HashMap<String, Object> res = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = new Date(System.currentTimeMillis());
        try {
            date = simpleDateFormat.parse(lastTime);
        } catch (Exception e) {
            return new ResponseService();
        }
        System.out.println(first);
        if (first == 1) {
            date = new Date(date.getTime() + 10 * 1000);
        }
        res.put("serverTime", date);
        if (type.equals(GROUP)) {
            List<GroupMessage> groupMessageList = groupMessageService.queryHistoryById(chatId, date);
            //还要同时拉取所有相同时间的消息
            int length = Math.min(groupMessageList.size(), 20);
            boolean more = (length != 0);
            //还要同时拉取所有相同时间的消息
            if (length == 20) {
                GroupMessage temp = groupMessageList.get(19);
                int count = 0;
                for (int i = 20; i < groupMessageList.size(); i++) {
                    if (groupMessageList.get(i).getSendtime().equals(temp.getSendtime())) {
                        count++;
                    }
                }
                length += count;
            }
            List<HashMap<String, Object>> data = new ArrayList<>();
            for (int i = length - 1; i >= 0; i--) {
                HashMap<String, Object> messageMap = new HashMap<>();
                GroupMessage message = groupMessageList.get(i);
                if (message.getSendtime().equals(date)) {
                    continue;
                }
                messageMap.put("userId", message.getSenderId());
                messageMap.put("chatId", message.getGroupId());
                User user = userService.queryById(message.getSenderId());
                GroupUser queryGroupUser = new GroupUser();
                queryGroupUser.setGroupId(chatId);
                queryGroupUser.setUserId(message.getSenderId());
                List<GroupUser> groupUserList = groupUserService.queryAll(queryGroupUser);
                GroupUser groupUser = groupUserList.get(0);
                messageMap.put("msgFromName", groupUser.getUserNickname());
                messageMap.put("msgFromAvatar", user.getPhoto());
                messageMap.put("msg", message.getMessage());
                messageMap.put("time", message.getSendtime());
                messageMap.put("msgId", message.getId());
                if (message.getMessage().startsWith("#")) {
                    messageMap.put("msgType", TIMESHAFT);
                } else {
                    messageMap.put("msgType", TEXT);
                }
                data.add(messageMap);
            }
            res.put("data", data);
            Date newLastTime = date;
            //若还有剩余的历史消息，则更新index
            if (more) {
                newLastTime = groupMessageList.get(length - 1).getSendtime();
            }
            //若没有后续的消息了，lastTime置为0
            if (length == groupMessageList.size()) {
                newLastTime = new Date(0);
            }

            res.put("lastTime", newLastTime);
            res.put("more", more);
        } else {
            Friends friends = friendsService.queryById(chatId);
            int dstId = Objects.equals(friends.getUserId1(), userId) ? friends.getUserId2() : friends.getUserId1();
            String dstNickName = friends.getUserId1() == dstId ? friends.getNickname1() : friends.getNickname2();
            User userSrc = userService.queryById(userId);
            User userDst = userService.queryById(dstId);
            List<PersonalMessage> historyMessage = new ArrayList<>();
            historyMessage.addAll(personalMessageService.queryHistoryById(chatId, userId, date));
            historyMessage.addAll(personalMessageService.queryHistoryById(chatId, dstId, date));

            historyMessage.sort(new Comparator<PersonalMessage>() {
                @Override
                public int compare(PersonalMessage o1, PersonalMessage o2) {
                    return o2.getId().compareTo(o1.getId());
                }
            });
            int length = Math.min(historyMessage.size(), 20);
            boolean more = (length != 0);
            //还要同时拉取所有相同时间的消息
            if (length == 20) {
                PersonalMessage temp = historyMessage.get(19);
                int count = 0;
                for (int i = 20; i < historyMessage.size(); i++) {
                    if (historyMessage.get(i).getSendtime().equals(temp.getSendtime())) {
                        count++;
                    }
                }
                length += count;
            }
            List<HashMap<String, Object>> data = new ArrayList<>();
            for (int i = length - 1; i >= 0; i--) {
                HashMap<String, Object> messageMap = new HashMap<>();
                PersonalMessage message = historyMessage.get(i);
                if (message.getSendtime().equals(date)) {
                    continue;
                }
                messageMap.put("userId", message.getSenderId());
                messageMap.put("chatId", message.getFriendsId());
                messageMap.put("msg", message.getMessage());
                if (Objects.equals(message.getSenderId(), userId)) {
                    messageMap.put("msgFromName", userSrc.getUsername());
                    messageMap.put("msgFromAvatar", userSrc.getPhoto());
                } else {
                    messageMap.put("msgFromName", dstNickName);
                    messageMap.put("msgFromAvatar", userDst.getPhoto());
                }
                messageMap.put("time", message.getSendtime());
                messageMap.put("msgId", message.getId());
                if (message.getMessage().startsWith("#")) {
                    messageMap.put("msgType", TIMESHAFT);
                } else {
                    messageMap.put("msgType", TEXT);
                }
                data.add(messageMap);
            }
            res.put("data", data);
            Date newLastTime = date;
            //若还有剩余的历史消息，则更新index
            if (more) {
                newLastTime = historyMessage.get(length - 1).getSendtime();
            }
            //若没有后续的消息了，lastTime置为0
            if (length == historyMessage.size()) {
                newLastTime = new Date(0);
            }
            res.put("lastTime", newLastTime);
            res.put("more", more);
        }
        return new ResponseService(res);
    }

    @RequestMapping(value = "/haveRead")
    public ResponseService markMessages(@RequestBody Map<String, Object> requestMap) {
        String time = (String) requestMap.get("time");
        int userId = (Integer) requestMap.get("userId");
        int chatId = (Integer) requestMap.get("chatId"); //需要type字段表示群聊还是私聊
        String type = (String) requestMap.get("type");
        if (type.equals(GROUP)) {
            List<GroupMessage> notReadMessages = groupMessageService.queryNotReadMessage(userId, chatId, UNREAD);
            GroupMessageState groupMessageState = new GroupMessageState();
            for (GroupMessage message : notReadMessages) {
                groupMessageState.setUserId(userId);
                groupMessageState.setState(UNREAD);
                groupMessageState.setMessageId(message.getId());
                List<GroupMessageState> updateList = groupMessageStateService.queryAll(groupMessageState);
                for (GroupMessageState messageState : updateList) {
                    messageState.setState(READ);
                    groupMessageStateService.update(messageState);
                }
            }
        } else {
            PersonalMessage messageQuery = new PersonalMessage();
            messageQuery.setState(UNREAD);
            Friends friends = friendsService.queryById(chatId);
            int senderId = friends.getUserId1() == userId? friends.getUserId2():friends.getUserId1();
            messageQuery.setFriendsId(chatId);
            messageQuery.setSenderId(senderId);
            List<PersonalMessage> notReadMessages = personalMessageService.queryAll(messageQuery);
            PersonalMessage messageToSet = new PersonalMessage();
            messageToSet.setState(READ);
            for (PersonalMessage message : notReadMessages) {
                int id = message.getId();
                messageToSet.setId(id);
                personalMessageService.update(messageToSet);
            }
        }
        return new ResponseService();
    }


    @RequestMapping(value = "/searchHistory")
    public ResponseService searchMessageByKeyword(@RequestParam("chatId") Integer chatId,
                                                  @RequestParam("type") String type,
                                                  @RequestParam("text") String text) {
        List<HashMap<String, Object>> data = new ArrayList<>();
        if (type.equals(GROUP)) {
            List<GroupMessage> list = groupMessageService.queryByKeyword(chatId, text);
            for (GroupMessage message : list) {
                HashMap<String, Object> map = new HashMap<>();
                GroupUser queryGroupUser = new GroupUser();
                queryGroupUser.setGroupId(chatId);
                queryGroupUser.setUserId(message.getSenderId());
                List<GroupUser> groupUserList = groupUserService.queryAll(queryGroupUser);
                GroupUser groupUser = groupUserList.get(0);
                User user = userService.queryById(message.getSenderId());
                map.put("msg", message.getMessage());
                map.put("msgFromName", groupUser.getUserNickname());
                if (message.getMessage().startsWith("#")) {
                    map.put("msgType", TIMESHAFT);
                } else {
                    map.put("msgType", TEXT);
                }
                map.put("msgFromAvatar", user.getPhoto());
                map.put("time", message.getSendtime());
                data.add(map);
            }
        } else {
            List<PersonalMessage> list = personalMessageService.queryByKeyword(chatId, text);
            for (PersonalMessage message : list) {
                HashMap<String, Object> map = new HashMap<>();
                Friends friends = friendsService.queryById(chatId);
                User user = userService.queryById(message.getSenderId());
                String senderNickName = Objects.equals(friends.getUserId1(), message.getSenderId()) ?
                        friends.getNickname1() : friends.getNickname2();
                map.put("msg", message.getMessage());
                if (message.getMessage().startsWith("#")) {
                    map.put("msgType", TIMESHAFT);
                } else {
                    map.put("msgType", TEXT);
                }
                map.put("msgFromName", senderNickName);
                map.put("msgFromAvatar", user.getPhoto());
                map.put("time", message.getSendtime());
                data.add(map);
            }
        }
        return new ResponseService(data);
    }
}
