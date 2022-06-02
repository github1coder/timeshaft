package com.timeshaft.after_end.service.chatOp;

import com.timeshaft.after_end.entity.*;
import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.addressList.GroupOp;
import com.timeshaft.after_end.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * feishu
 * 聊天历史消息相关接口
 *
 * @author : dxt
 * @since : 2022-06-02 10:38
 **/
@Service
@PropertySource("classpath:content.properties")
public class HistoryMessageOp {
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


    public HashMap<String, Object> getFriendHistoryMessage(Integer userId, String lastTime, Integer chatId, Integer first) {
        HashMap<String, Object> res = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = new Date(System.currentTimeMillis());
        try {
            date = simpleDateFormat.parse(lastTime);
        } catch (Exception e) {
            return null;
        }
        if (first == 1) {
            date = new Date(date.getTime() + 10 * 1000);
        }
        res.put("serverTime", date);
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
        return res;
    }

    public HashMap<String, Object> getGroupHistoryMessage(String lastTime, Integer chatId, Integer first) {
        HashMap<String, Object> res = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = new Date(System.currentTimeMillis());
        try {
            date = simpleDateFormat.parse(lastTime);
        } catch (Exception e) {
            return null;
        }
        if (first == 1) {
            date = new Date(date.getTime() + 10 * 1000);
        }
        res.put("serverTime", date);
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
        return res;
    }

    public void readFriendMessage(int userId, int chatId) {
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

    public void readGroupMessage(int userId, int chatId) {
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
    }

    public List<HashMap<String, Object>> searchFriendMessageByKeyword(Integer chatId, String text) {
        List<HashMap<String, Object>> res = new ArrayList<>();
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
            res.add(map);
        }
        return res;
    }

    public List<HashMap<String, Object>> searchGroupMessageByKeyword(Integer chatId, String text) {
        List<HashMap<String, Object>> res = new ArrayList<>();
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
            res.add(map);
        }
        return res;
    }
}
