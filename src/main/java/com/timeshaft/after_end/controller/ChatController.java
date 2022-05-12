package com.timeshaft.after_end.controller;

import com.timeshaft.after_end.entity.*;
import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.addressList.GroupOp;
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

    @Value("${type.messageRead}")
    private String READ;
    @Value("${type.messageNotRead}")
    private String UNREAD;
    @Value("${type.groupType}")
    private String GROUP;

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
            map.put("chatName", chatName);
            map.put("chatAvatar", chatAvatar);
            map.put("type", "private");
            Date recent = null;
            //拉取所有未读消息
            List<HashMap<String, Object>> data = new ArrayList<>();
            PersonalMessage messageQuery = new PersonalMessage();
            messageQuery.setState(UNREAD);
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
                    dataMap.put("time", message.getSendtime());
                    dataMap.put("userId", message.getSenderId());
                    dataMap.put("chatId", message.getFriendsId());
                    data.add(dataMap);
                }
                recent = notReadMessages.get(notReadMessages.size()-1).getSendtime();
                index = notReadMessages.get(0).getId();
            }
            //若没有已读消息，index应该为双方最近的一条消息+1 (因为索引的时候是使用 < 查询)
            if (index == -1) {
                PersonalMessage messageTo= personalMessageService.queryLatestById(friendId, sourceId);
                PersonalMessage messageFrom = personalMessageService.queryLatestById(friendId, friendUserId);
                if (messageTo == null) {
                    if (messageFrom != null) {
                        index = messageFrom.getId() + 1;
                        recent = messageFrom.getSendtime();
                    } else {
                        PersonalMessage messageRecent = personalMessageService.queryLatest();
                        if (messageRecent == null) {
                            index = 0;
                            recent = new Date(System.currentTimeMillis());
                        } else {
                            index = messageRecent.getId() + 1;
                            recent = messageRecent.getSendtime();
                        }
                    }
                } else {
                    if (messageFrom == null) {
                        index = messageTo.getId() + 1;
                        recent = messageTo.getSendtime();
                    } else {
                        index = Math.max(messageFrom.getId(), messageTo.getId()) + 1;
                        recent = messageFrom.getId() > messageTo.getId() ? messageFrom.getSendtime() : messageTo.getSendtime();
                    }
                }
            }
            //若没聊过天，index为-1——加好友会打招呼，此种情况不会发生
            map.put("data", data);
            map.put("index", index);
            map.put("recent", recent);
            res.add(map);
        }
        //获取群聊相关信息
        List<Group> groupList = groupOp.getGroup(sourceId);
        for (Group group : groupList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", group.getId());
            map.put("chatName", group.getName());
            map.put("chatAvatar", group.getPhoto());
            map.put("type", "group");
            //拉取所有未读消息
            Date recent = null;
            List<HashMap<String, Object>> data = new ArrayList<>();
            List<GroupMessage> notReadMessages = groupMessageService.queryNotReadMessage(sourceId, group.getId(), UNREAD);
            int index = -1;
            if (notReadMessages != null && !notReadMessages.isEmpty()) {
                index = notReadMessages.get(0).getId();
            }
            if (notReadMessages != null && notReadMessages.size() > 0) {
                for (GroupMessage message : notReadMessages) {
                    HashMap<String, Object> dataMap = new HashMap<>();
                    User user = userService.queryById(message.getSenderId());
                    GroupUser queryGroupUser = new GroupUser();
                    queryGroupUser.setGroupId(group.getId());
                    queryGroupUser.setUserId(user.getId());
                    List<GroupUser> groupUserList = groupUserService.queryAll(queryGroupUser);
                    //用户已经退群则跳过此条消息
                    if (groupUserList == null || groupUserList.size() == 0) {
                        continue;
                    }
                    GroupUser groupUser = groupUserList.get(0);
                    dataMap.put("msgFromName", groupUser.getUserNickname());
                    dataMap.put("msgFromAvatar", user.getPhoto());
                    dataMap.put("msg", message.getMessage());
                    dataMap.put("time", message.getSendtime());
                    dataMap.put("userId", message.getSenderId());
                    dataMap.put("chatId", group.getId());
                    data.add(dataMap);
                }
                recent = notReadMessages.get(notReadMessages.size()-1).getSendtime();
                index = notReadMessages.get(0).getId();
            }
            //若没有已读消息，index应该为群聊最近的一条消息+1 (因为索引的时候是使用 < 查询)
            if (index == -1) {
                GroupMessage latest = groupMessageService.queryLatestById(group.getId());
                if (latest == null) {
                    recent = new Date(System.currentTimeMillis());
                } else {
                    index = latest.getId() + 1;
                    recent = latest.getSendtime();
                }
            }
            //若没聊过天，index为-1(其实好像不影响，因为反正没有聊过天history就是空[手动狗头])
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
        List<HashMap<String, Object>> data = new ArrayList<>();
        HashMap<String, Object> mapChat = new HashMap<>();
        HashMap<String, Object> mapContact = new HashMap<>();
        HashMap<String, Object> mapGroup = new HashMap<>();
        String chatUrl = "/user/" + sourceId;
        String contactUrl = "/user/contact/" + sourceId;
        String groupUrl = "/group/" + sourceId;
        mapChat.put("type", 0);
        mapChat.put("url", chatUrl);
        mapContact.put("type", 1);
        mapContact.put("url", contactUrl);
        mapGroup.put("type", 2);
        mapGroup.put("url", groupUrl);
        data.add(mapChat);
        data.add(mapContact);
        data.add(mapGroup);
        return new ResponseService(data);
    }

    @RequestMapping(value = "/getHistoryMessage")
    public ResponseService getHistoryMessage(@RequestBody Map<String, Object> requestMap) {
        int srcId = (Integer) requestMap.get("userId");
        int chatId = (Integer) requestMap.get("chatId"); //需要type字段表示来自群聊还是私聊
        int index = (Integer) requestMap.get("index");
        String type = (String) requestMap.get("type");
        HashMap<String, Object> res = new HashMap<>();
        if (type.equals(GROUP)) {
            Group group = groupService.queryById(chatId);
            List<GroupMessage> groupMessageList = groupMessageService.queryHistoryById(chatId, index);

        } else {
            Friends friends = friendsService.queryById(chatId);
            int dstId = friends.getUserId1() == srcId ? friends.getUserId2() : friends.getUserId1();
            String srcNickName = friends.getUserId1() == srcId ? friends.getNickname1() : friends.getNickname2();
            String dstNickName = friends.getUserId1() == dstId ? friends.getNickname1() : friends.getNickname2();
            User userSrc = userService.queryById(srcId);
            User userDst = userService.queryById(dstId);
            List<PersonalMessage> historyMessage = new ArrayList<>();
            historyMessage.addAll(personalMessageService.queryHistoryById(chatId, srcId, index));
            historyMessage.addAll(personalMessageService.queryHistoryById(chatId, dstId, index));
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
                messageMap.put("userId", message.getSenderId());
                messageMap.put("chatId", message.getFriendsId());
                messageMap.put("msg", message.getMessage());
                if (message.getSenderId() == srcId) {
                    messageMap.put("msgFromName", srcNickName);
                    messageMap.put("msgFromAvatar", userSrc.getPhoto());
                } else {
                    messageMap.put("msgFromName", dstNickName);
                    messageMap.put("msgFromAvatar", userDst.getPhoto());
                }
                messageMap.put("time", message.getSendtime());
                data.add(messageMap);
            }
            res.put("data", data);
            int newIndex = index;
            //若还有剩余的历史消息，则更新index
            if (more) {
                newIndex = historyMessage.get(length - 1).getId();
            }
            res.put("index", newIndex);
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
        return new ResponseService();
    }
}
