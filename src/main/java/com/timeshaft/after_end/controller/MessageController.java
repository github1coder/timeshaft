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
    @Value("${type.messageRead}")
    private String READ;
    @Value("${type.messageNotRead}")
    private String UNREAD;

    /**
     * 接收客户端发送的私信类型消息，将其存入数据库，并发送至指定的用户路径
     *
     * @param payload 前端传来的私信
     */
    @MessageMapping("/personalMessage")
    public void receivePersonalMessage(@Payload Map<String, Object> payload) {
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
        if (friends == null) {
            payload.put("msg", "你被删啦，呜呜呜，重新登录可以去掉负心人");
            messagingTemplate.convertAndSend("/user/" + senderId, payload);
            return;
        }
        PersonalMessage inserted = personalMessageService.insert(personalMessage);
        int targetId = friends.getUserId1() == senderId? friends.getUserId2():friends.getUserId1();
        payload.put("type", "private");
        payload.put("index", inserted.getId() + 1);
        messagingTemplate.convertAndSend("/user/" + targetId, payload);
    }

    /**
     * 接收客户端发送的群聊类型消息，将其存入数据库，并发送至指定的群组路径
     *
     * @param payload 前端传来的群组消息
     */
    @MessageMapping("/groupMessage")
    public void receiveGroupMessage(@Payload Map<String, Object> payload) {
        Date date = new Date(System.currentTimeMillis());
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setMessage((String) payload.get("msg"));
        groupMessage.setGroupId(Integer.valueOf(payload.get("chatId").toString()));
        groupMessage.setSenderId(Integer.valueOf(payload.get("userId").toString()));
        groupMessage.setSendtime(date);
        GroupMessage insertMessage = groupMessageService.insert(groupMessage);
        int messageId = insertMessage.getId();
        int groupId = groupMessage.getGroupId();
        GroupUser groupUser = new GroupUser();
        groupUser.setGroupId(groupId);
        List<GroupUser> userInGroup = groupUserService.queryAll(groupUser);
        GroupMessageState groupMessageState = new GroupMessageState();
        groupMessageState.setMessageId(messageId);
        payload.put("type", "group");
        payload.put("index", insertMessage.getId() + 1);
        for (GroupUser user : userInGroup) {
            if (user.getUserId().equals(groupMessage.getSenderId())) {
                groupMessageState.setState(READ);
            } else {
                groupMessageState.setState(UNREAD);
                messagingTemplate.convertAndSend("/group/" + user.getUserId(), payload);
            }
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

