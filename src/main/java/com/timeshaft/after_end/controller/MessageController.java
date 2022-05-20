package com.timeshaft.after_end.controller;

import com.timeshaft.after_end.entity.*;
import com.timeshaft.after_end.service.GroupHeatService;
import com.timeshaft.after_end.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * feishu
 * 处理客户端与服务器间信息传递
 *
 * @author : dxt
 * @since : 2022-04-17 13:43
 **/
@RestController //等价于@Controller+@ResponseBody的结合，里面的方法都以json格式输出
@CrossOrigin
@Slf4j
public class MessageController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private PersonalMessageServiceImpl personalMessageService;
    @Autowired
    private GroupMessageServiceImpl groupMessageService;
    @Autowired
    private GroupMessageStateServiceImpl groupMessageStateService;
    @Autowired
    private GroupUserServiceImpl groupUserService;
    @Autowired
    private FriendsServiceImpl friendsService;
    @Autowired
    private GroupHeatService groupHeatService;
    @Value("${type.groupType}")
    private String GROUP;
    @Value("${type.friendType}")
    private String FRIEND;
    @Value("${type.messageRead}")
    private String READ;
    @Value("${type.messageNotRead}")
    private String UNREAD;
    @Value("${type.textType}")
    private String TEXT;
    @Value("${type.timeShaftType}")
    private String TIMESHAFT;

    /**
     * 接收客户端发送的私信类型消息，将其存入数据库，并发送至指定的用户路径
     *
     * @param payload 前端传来的私信
     */
    @MessageMapping("/personalMessage")
    public void receivePersonalMessage(@Payload Map<String, Object> payload) {
        payload.put("type", FRIEND);
        Date date = new Date(System.currentTimeMillis());
        PersonalMessage personalMessage = new PersonalMessage();
        personalMessage.setSendtime(date);
        personalMessage.setMessage((String) payload.get("msg"));
        personalMessage.setFriendsId(Integer.valueOf(payload.get("chatId").toString()));
        personalMessage.setSenderId(Integer.valueOf(payload.get("userId").toString()));
        personalMessage.setState(UNREAD);
        int friendId = personalMessage.getFriendsId();
        int senderId = personalMessage.getSenderId();
        Friends friends = friendsService.queryById(friendId);
        if (personalMessage.getMessage() == null || personalMessage.getMessage().length() == 0) {
            return;
        }
        if (friends == null) {
            payload.put("msg", "你们不是好友哦");
            messagingTemplate.convertAndSend("/user/chat/" + senderId, payload);
            return;
        }
        PersonalMessage inserted = personalMessageService.insert(personalMessage);
        int targetId = friends.getUserId1() == senderId? friends.getUserId2():friends.getUserId1();
        payload.put("msgId", inserted.getId());
        if (personalMessage.getMessage().startsWith("#")) {
            payload.put("msgType", TIMESHAFT);
        } else {
            payload.put("msgType", TEXT);
        }
        messagingTemplate.convertAndSend("/user/chat/" + targetId, payload);
        messagingTemplate.convertAndSend("/user/chat/" + senderId, payload);
    }

    /**
     * 接收客户端发送的群聊类型消息，将其存入数据库，并发送至指定的群组路径
     *
     * @param payload 前端传来的群组消息
     */
    @MessageMapping("/groupMessage")
    public void receiveGroupMessage(@Payload Map<String, Object> payload) {
        payload.put("type", GROUP);
        Date date = new Date(System.currentTimeMillis());
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setMessage((String) payload.get("msg"));
        groupMessage.setGroupId(Integer.valueOf(payload.get("chatId").toString()));
        groupMessage.setSenderId(Integer.valueOf(payload.get("userId").toString()));
        groupMessage.setSendtime(date);
        if (groupMessage.getMessage() == null || groupMessage.getMessage().length() == 0) {
            return;
        }
        GroupUser queryGroupUser = new GroupUser();
        queryGroupUser.setUserId(groupMessage.getSenderId());
        queryGroupUser.setGroupId(groupMessage.getGroupId());
        List<GroupUser> queryResult = groupUserService.queryAll(queryGroupUser);
        if (queryResult.size() == 0) {
            payload.put("msg", "你不在群里哦");
            messagingTemplate.convertAndSend("/user/chat/" + groupMessage.getSenderId(), payload);
            return;
        }
        GroupMessage insertMessage = groupMessageService.insert(groupMessage);
        int messageId = insertMessage.getId();
        int groupId = groupMessage.getGroupId();
        GroupUser groupUser = new GroupUser();
        groupUser.setGroupId(groupId);
        List<GroupUser> userInGroup = groupUserService.queryAll(groupUser);
        GroupMessageState groupMessageState = new GroupMessageState();
        groupMessageState.setMessageId(messageId);
        payload.put("msgId", insertMessage.getId());
        if (groupMessage.getMessage().startsWith("#")) {
            payload.put("msgType", TIMESHAFT);
        } else {
            payload.put("msgType", TEXT);
        }
        for (GroupUser user : userInGroup) {
            if (user.getUserId().equals(groupMessage.getSenderId())) {
                groupMessageState.setState(READ);
            } else {
                groupMessageState.setState(UNREAD);
            }
            messagingTemplate.convertAndSend("/user/chat/" + user.getUserId(), payload);
            groupMessageState.setUserId(user.getUserId());
            groupMessageStateService.insert(groupMessageState);
        }

        //群热度
        List<GroupHeat> groupHeats = groupHeatService.queryAll(new GroupHeat(groupId, null, null, GROUP));
        for(GroupHeat groupHeat : groupHeats) {
            groupHeat.upMessageCount();
            groupHeatService.update(groupHeat);
        }

    }

}

