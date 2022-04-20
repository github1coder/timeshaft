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
     * @param personalMessage 前端传来的私信
     */
    @MessageMapping("/personalMessage")
    public void receivePersonalMessage(@Payload PersonalMessage personalMessage) {
        personalMessageService.insert(personalMessage);
        int friendId = personalMessage.getFriendsId();
        messagingTemplate.convertAndSend("/user/" + friendId, personalMessage);
    }

    /**
     * 接收客户端发送的群聊类型消息，将其存入数据库，并发送至指定的群组路径
     *
     * @param groupMessage 前端传来的群组消息
     */
    @MessageMapping("/groupMessage")
    public void receiveGroupMessage(@Payload GroupMessage groupMessage) {
        groupMessageService.insert(groupMessage);
        int groupId = groupMessage.getGroupId();
        messagingTemplate.convertAndSend("/group/" + groupId, groupMessage);
    }
}

