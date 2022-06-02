package com.timeshaft.after_end.service.chatOp;

import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.entity.Group;
import com.timeshaft.after_end.entity.GroupMessage;
import com.timeshaft.after_end.entity.PersonalMessage;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.addressList.GroupOp;
import com.timeshaft.after_end.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * feishu
 * 聊天功能初始化相关接口
 *
 * @author : dxt
 * @since : 2022-06-02 10:40
 **/
@Service
@PropertySource("classpath:content.properties")
public class InitChatOp {
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

    public List<HashMap<String, Object>> getFriendMessageList(int userId) {
        //获取私聊相关信息
        List<Friends> friendsList = friendOp.getFriends(userId);
        List<HashMap<String, Object>> res = new ArrayList<>();
        for (Friends friends : friendsList) {
            HashMap<String, Object> map = new HashMap<>();
            Integer friendUserId = friends.getUserId1().equals(userId)? friends.getUserId2():friends.getUserId1();
            Integer friendId = friends.getId();
            String chatName = friends.getUserId1().equals(userId)? friends.getNickname2():friends.getNickname1();
            String chatAvatar = userService.queryById(friendUserId).getPhoto();
            map.put("id", friendId);
            map.put("isMeeting", friends.getStatus().equals(onMeeting));
            map.put("chatName", chatName);
            map.put("chatAvatar", chatAvatar);
            map.put("type", FRIEND);
            PersonalMessage messageTo= personalMessageService.queryLatestById(friendId, userId);
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
        return res;
    }

    public List<HashMap<String, Object>> getGroupMessageList(int userId) {
        List<HashMap<String, Object>> res = new ArrayList<>();
        List<Group> groupList = groupOp.getGroup(userId);
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
            List<GroupMessage> notReadMessages = groupMessageService.queryNotReadMessage(userId, group.getId(), UNREAD);
            int number = notReadMessages.size();
            map.put("number", number);
            map.put("lastMessage", lastMessage);
            map.put("lastTime", lastTime);
            res.add(map);
        }
        return res;
    }
}
