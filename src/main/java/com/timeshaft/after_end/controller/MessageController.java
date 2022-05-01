package com.timeshaft.after_end.controller;

import com.timeshaft.after_end.entity.GroupMessage;
import com.timeshaft.after_end.entity.GroupMessageState;
import com.timeshaft.after_end.entity.MessageStateType;
import com.timeshaft.after_end.entity.PersonalMessage;
import com.timeshaft.after_end.service.MessageStateService;
import com.timeshaft.after_end.service.impl.GroupMessageServiceImpl;
import com.timeshaft.after_end.service.impl.MessageStateServiceImpl;
import com.timeshaft.after_end.service.impl.PersonalMessageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
    private MessageStateServiceImpl messageStateService;

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
        //payload.put("time", date);
        personalMessage.setMessage((String) payload.get("msg"));
        personalMessage.setFriendsId(Integer.valueOf(payload.get("dstId").toString()));
        personalMessage.setSenderId(Integer.valueOf(payload.get("srcId").toString()));
        MessageStateType state = MessageStateType.UNREAD;
        personalMessage.setState(messageStateService.EnumToString(state));
        personalMessageService.insert(personalMessage);
        int friendId = personalMessage.getFriendsId();
        int senderId = personalMessage.getSenderId();
        messagingTemplate.convertAndSend("/user/" + senderId + "/" + friendId, payload);
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
        //GroupMessageState groupMessageState = new GroupMessageState();
        //payload.put("time", date);
        groupMessage.setMessage((String) payload.get("msg"));
        groupMessage.setGroupId(Integer.valueOf(payload.get("dstId").toString()));
        groupMessage.setSenderId(Integer.valueOf(payload.get("srcId").toString()));
        groupMessage.setSendtime(date);
        groupMessageService.insert(groupMessage);
        int groupId = groupMessage.getGroupId();
        messagingTemplate.convertAndSend("/group/" + groupId, payload);
    }

}

