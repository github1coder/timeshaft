package com.timeshaft.after_end.controller;

import com.timeshaft.after_end.entity.GroupMessage;
import com.timeshaft.after_end.entity.PersonalMessage;
import com.timeshaft.after_end.service.impl.GroupMessageServiceImpl;
import com.timeshaft.after_end.service.impl.PersonalMessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.Map;

/**
 * feishu
 * 处理客户端与服务器间信息传递
 *
 * @author : dxt
 * @since : 2022-04-17 13:43
 **/
@Controller
public class MessageController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private PersonalMessageServiceImpl personalMessageService;
    @Autowired
    private GroupMessageServiceImpl groupMessageService;

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
        payload.put("time", date);
        personalMessage.setMessage((String) payload.get("message"));
        personalMessage.setFriendsId(Integer.valueOf((String) payload.get("targetId")));
        personalMessage.setSenderId(Integer.valueOf((String) payload.get("senderId")));
        personalMessageService.insert(personalMessage);
        int friendId = personalMessage.getFriendsId();
        int senderId = personalMessage.getSenderId();
        messagingTemplate.convertAndSend("/user/" + friendId + "-" + senderId, payload);
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
        payload.put("time", date);
        groupMessage.setMessage((String) payload.get("message"));
        groupMessage.setGroupId(Integer.valueOf((String) payload.get("targetId")));
        groupMessage.setSenderId(Integer.valueOf((String) payload.get("senderId")));
        groupMessage.setSendtime(date);
        groupMessageService.insert(groupMessage);
        int groupId = groupMessage.getGroupId();
        messagingTemplate.convertAndSend("/group/" + groupId, payload);
    }
}

