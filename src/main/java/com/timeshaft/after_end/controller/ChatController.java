package com.timeshaft.after_end.controller;

import com.timeshaft.after_end.annotation.RequestLog;
import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.addressList.GroupOp;
import com.timeshaft.after_end.service.chatOp.HistoryMessageOp;
import com.timeshaft.after_end.service.chatOp.InitChatOp;
import com.timeshaft.after_end.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private InitChatOp initChatOp;
    @Autowired
    private HistoryMessageOp historyMessageOp;

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
    @RequestLog
    public ResponseService getMessagesList(@RequestHeader("user_id") Integer userId) {
        List<HashMap<String, Object>> res = new ArrayList<>();
        res.addAll(initChatOp.getFriendMessageList(userId));
        res.addAll(initChatOp.getGroupMessageList(userId));
        return new ResponseService(res);
    }

    @RequestMapping(value = "/getListenerList")
    @RequestLog
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
    @RequestLog
    public ResponseService getHistoryMessage(@RequestParam("userId") Integer userId,
                                             @RequestParam("lastTime") String lastTime,
                                             @RequestParam("chatId") Integer chatId,
                                             @RequestParam("type") String type,
                                             @RequestParam("first") Integer first) {
        HashMap<String, Object> res;
        if (type.equals(GROUP)) {
            res = historyMessageOp.getGroupHistoryMessage(lastTime, chatId, first);
        } else {
            res = historyMessageOp.getFriendHistoryMessage(userId, lastTime, chatId, first);
        }
        return new ResponseService(res);
    }

    @RequestMapping(value = "/haveRead")
    @RequestLog
    public ResponseService markMessages(@RequestBody Map<String, Object> requestMap) {
        int userId = (Integer) requestMap.get("userId");
        int chatId = (Integer) requestMap.get("chatId"); //需要type字段表示群聊还是私聊
        String type = (String) requestMap.get("type");
        if (type.equals(GROUP)) {
            historyMessageOp.readGroupMessage(userId, chatId);
        } else {
            historyMessageOp.readFriendMessage(userId, chatId);
        }
        return new ResponseService();
    }

    @RequestMapping(value = "/searchHistory")
    @RequestLog
    public ResponseService searchMessageByKeyword(@RequestParam("chatId") Integer chatId,
                                                  @RequestParam("type") String type,
                                                  @RequestParam("text") String text) {
        List<HashMap<String, Object>> res;
        if (type.equals(GROUP)) {
           res = historyMessageOp.searchGroupMessageByKeyword(chatId, text);
        } else {
            res = historyMessageOp.searchFriendMessageByKeyword(chatId, text);
        }
        return new ResponseService(res);
    }
}
