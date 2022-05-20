package com.timeshaft.after_end.service.addressList;

import com.timeshaft.after_end.entity.*;
import com.timeshaft.after_end.service.FriendsService;
import com.timeshaft.after_end.service.GroupService;
import com.timeshaft.after_end.service.GroupUserService;
import com.timeshaft.after_end.service.UserService;
import com.timeshaft.after_end.service.impl.PersonalMessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@PropertySource("classpath:content.properties")
public class FriendOp {

    @Resource(name = "FriendsService")
    private FriendsService friendsService;

    @Resource(name = "GroupService")
    private GroupService groupService;

    @Resource(name = "GroupUserService")
    private GroupUserService groupUserService;

    @Resource(name = "UserService")
    private UserService userService;

    @Value("${type.friendType}")
    private String friendType;
    @Value("${type.groupType}")
    private String groupType;
    @Value("${groupIdentity.manager}")
    private String MANAGER;
    @Value("${groupIdentity.member}")
    private String MEMBER;
    @Value("${friendState.neww}")
    private String NEW;
    @Value("${friendState.acceptt}")
    private String ACCEPT;
    @Value("${type.messageRead}")
    private String READ;
    @Value("${type.messageNotRead}")
    private String UNREAD;
    @Value("${type.textType}")
    private String TEXT;
    @Value("${type.timeShaftType}")
    private String TIMESHAFT;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private PersonalMessageServiceImpl personalMessageService;

    public List<Friends> getFriends(Integer id) {
        Friends friend1 = new Friends(id, null, null, null, ACCEPT, null);
        Friends friend2 = new Friends(null, id, null, null, ACCEPT, null);
        List<Friends> friends = friendsService.queryAll(friend1);
        friends.addAll(friendsService.queryAll(friend2));
        return friends;
    }

    public void addFriend(int friend1, int friend2) {
        Friends friend = new Friends(friend1, friend2, null, null, ACCEPT, null);
        friendsService.insert(friend);
    }

    public void delFriend(int friend1, int friend2) {
        Friends friend = new Friends(friend1, friend2, null, null, null, null);
        List<Friends> friends = friendsService.queryAll(friend);
        Friends friend_2 = new Friends(friend2, friend1, null, null, null, null);
        friends.addAll(friendsService.queryAll(friend_2));
        for(Friends tmp : friends){
            friendsService.deleteById(tmp.getId());
        }
    }

    public void changeNickname(int self_id, int friend_id, String name) {
        Friends friend1 = new Friends(self_id, friend_id, null, null, null, null);
        List<Friends> friends = friendsService.queryAll(friend1);
        for(Friends tmp : friends){
            tmp.setNickname2(name);
            friendsService.update(tmp);
            return;
        }
        Friends friend2 = new Friends(friend_id, self_id, null, null, null, null);
        friends.addAll(friendsService.queryAll(friend2));
        for(Friends tmp : friends){
            tmp.setNickname1(name);
            friendsService.update(tmp);
        }
    }

    public List<Map<String, String>> searchByNick(String name, String type, Integer id) {
        List<Map<String, String>> ans = new ArrayList<>();
        if(groupType.equals(type)) {
            List<Group> groups = groupService.queryAll(new Group(name, null, null, null, null, null, 0));
            List<Group> res = new ArrayList<>();
            for(Group group : groups) {
                GroupUser groupUser = new GroupUser(group.getId(), id, null, null, null);
                List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
                if(groupUsers.size() == 0) {
                    res.add(group);
                }
            }
            for(Group group : res) {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", group.getId().toString());
                map.put("name", group.getName());
                map.put("photo", group.getPhoto());
                ans.add(map);
            }
        } else {
            List<User> users = userService.queryAll(new User(null, null, name, null, 0));
            List<User> tmp = userService.queryAll(new User(name, null, null, null, 0));
            for(User user : tmp) {
                if(!users.contains(user)) {
                    users.add(user);
                }
            }
            List<User> res = new ArrayList<>();
            for(User user : users) {
                Friends friend1 = new Friends(id, user.getId(), null, null, null, null);
                Friends friend2 = new Friends(user.getId(), id, null, null, null, null);
                List<Friends> friends = friendsService.queryAll(friend1);
                friends.addAll(friendsService.queryAll(friend2));
                if(friends.size() == 0) {
                    res.add(user);
                }
            }
            for(User user : res) {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", user.getId().toString());
                map.put("name", user.getUsername());
                map.put("photo", user.getPhoto());
                ans.add(map);
            }
        }
        return ans;
    }

    public void apply(Integer self_id, String type, String action, Integer id) {
        if(groupType.equals(type)) {
            GroupUser groupUser = new GroupUser(id, self_id, null, null, null);
            if(action.equals(NEW)) {
                List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
                if (groupUsers.size() == 0) {
                    groupUser.setState(action);
                    groupUser.setIdentity("");
                    groupUser.setUserNickname("");
                    groupUserService.insert(groupUser);
                }
            } else if(action.equals(ACCEPT)) {
                groupUser.setState(NEW);
                List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
                groupUsers.get(0).setState(action);
                groupUser = groupUsers.get(0);
                groupUser.setIdentity(MEMBER);
                groupUserService.update(groupUser);
            } else {
                groupUser.setState(NEW);
                List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
                groupUserService.deleteById(groupUsers.get(0).getId());
            }
        } else {
            Friends friend1 = new Friends(self_id, id, null, null, null, null);
            Friends friend2 = new Friends(id, self_id, null, null, null, null);
            if(action.equals(NEW)) {
                List<Friends> friends = friendsService.queryAll(friend1);
                friends.addAll(friendsService.queryAll(friend2));
                if (friends.size() == 0) {
                    friend1.setState(action);
                    friend1.setNickname2(userService.queryById(id).getUsername());
                    friend1.setNickname1(userService.queryById(self_id).getUsername());
                    friendsService.insert(friend1);
                }
            } else if(action.equals(ACCEPT)) {
                friend1.setState(NEW);
                friend2.setState(NEW);
                List<Friends> friends = friendsService.queryAll(friend1);
                friends.addAll(friendsService.queryAll(friend2));
                friends.get(0).setState(action);
                friendsService.update(friends.get(0));
            } else {
                friend1.setState(NEW);
                friend2.setState(NEW);
                List<Friends> friends = friendsService.queryAll(friend1);
                friends.addAll(friendsService.queryAll(friend2));
                friendsService.deleteById(friends.get(0).getId());
            }
        }
    }

    public List<Map<String, String>> getApplyList(String type, Integer id) {
        List<Map<String, String>> ans = new ArrayList<>();
        if(groupType.equals(type)) {
            GroupUser groupUser = new GroupUser(null, id, null, "master", null);
            List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
            groupUser.setIdentity(MANAGER);
            groupUsers.addAll(groupUserService.queryAll(groupUser));
            List<Group> groups = new ArrayList<>();
            for(GroupUser g : groupUsers) {
                groups.add(groupService.queryById(g.getGroupId()));
            }
            for(Group group: groups) {
                GroupUser tmp = new GroupUser(group.getId(), null, null, null, NEW);
                List<GroupUser> apply = groupUserService.queryAll(tmp);
                for(GroupUser g : apply) {
                    HashMap<String, String> map = new HashMap<>();
                    User user = userService.queryById(g.getUserId());
                    map.put("group_id", g.getGroupId().toString());
                    map.put("group_name", group.getName());
                    map.put("id", user.getId().toString());
                    map.put("name", user.getUsername());
                    map.put("photo", user.getPhoto());
                    map.put("show", "true");
                    ans.add(map);
                }
            }
        } else {
            Friends friend = new Friends(null, id, null, null, NEW, null);
            List<Friends> friends = friendsService.queryAll(friend);
            for(Friends f : friends){
                HashMap<String, String> map = new HashMap<>();
                User user = userService.queryById(f.getUserId1());
                map.put("id", user.getId().toString());
                map.put("name", user.getUsername());
                map.put("photo", user.getPhoto());
                map.put("show", "true");
                ans.add(map);
            }
        }
        return ans;
    }

    public Map<User, String> getGroupMember(int id) {
        List<GroupUser> groupUsers = groupUserService.queryAll(new GroupUser(id, null, null, null, ACCEPT));
        Map<User, String> users = new HashMap<>();
        for (GroupUser groupUser : groupUsers) {
            User user = userService.queryById(groupUser.getUserId());
            users.put(user, groupUser.getUserNickname());
        }
        return users;
    }

    public void sendNotification(String type, String action,  Integer id, Integer user_id) {
        if (type.equals(friendType) && action.equals(ACCEPT)) {
            User acceptor = userService.queryById(user_id);
            User sender = userService.queryById(id);
            Friends friend1 = new Friends(user_id, id, null, null, null, null);
            Friends friend2 = new Friends(id, user_id, null, null, null, null);
            List<Friends> friends = friendsService.queryAll(friend1);
            friends.addAll(friendsService.queryAll(friend2));
            Friends friendsRelation = friends.get(0);
            //设置打招呼消息
            Date date = new Date(System.currentTimeMillis() - 1);
            PersonalMessage helloMessage = new PersonalMessage();
            helloMessage.setSendtime(date);
            String senderNickname = Objects.equals(friendsRelation.getUserId1(), sender.getId()) ?
                    friendsRelation.getNickname1():friendsRelation.getNickname2();
            helloMessage.setMessage("你好，我是" + senderNickname);
            helloMessage.setFriendsId(friendsRelation.getId());
            helloMessage.setSenderId(sender.getId());
            helloMessage.setState(UNREAD);
            personalMessageService.insert(helloMessage);
            HashMap<String, Object> res = new HashMap<>();
            res.put("id", friendsRelation.getId());
            res.put("type", "private");
            String senderNickName = Objects.equals(friendsRelation.getUserId1(), sender.getId()) ? friendsRelation.getNickname1():friendsRelation.getNickname2();
            String accNickName = Objects.equals(friendsRelation.getUserId1(), acceptor.getId()) ? friendsRelation.getNickname1():friendsRelation.getNickname2();
            res.put("chatName", senderNickName);
            res.put("chatAvatar", sender.getPhoto());
            PersonalMessage personalMessage = personalMessageService.queryLatestById(friendsRelation.getId(), sender.getId());
            if (personalMessage == null) {
                personalMessage = personalMessageService.queryLatest();
            }
            res.put("lastTime", new Date(System.currentTimeMillis()));
            HashMap<String, Object> lastMessage = new HashMap<>();
            lastMessage.put("msg", personalMessage.getMessage());
            lastMessage.put("time", personalMessage.getSendtime());
            res.put("lastMessage", lastMessage);
            res.put("number", 1);
            res.put("msgType", TEXT);
            messagingTemplate.convertAndSend("/user/contact/" + acceptor.getId(), res);
            /*下面发送给请求方*/
            res.put("number", 0);
            res.put("chatName", accNickName);
            res.put("chatAvatar", acceptor.getPhoto());
            messagingTemplate.convertAndSend("/user/contact/" + sender.getId(), res);
        } else if (type.equals(groupType) && action.equals(ACCEPT)) {
            HashMap<String, Object> res = new HashMap<>();
            Group group = groupService.queryById(id);
            res.put("id", id);
            res.put("type", "group");
            res.put("chatName", group.getName());
            res.put("chatAvatar", null);
            res.put("lastTime", new Date(System.currentTimeMillis()));
            res.put("number", 0);
            HashMap<String, Object> lastMessage = new HashMap<>();
            lastMessage.put("msg", null);
            lastMessage.put("time", null);
            res.put("lastMessage", lastMessage);
            messagingTemplate.convertAndSend("/user/contact/" + user_id, res);
        }
    }

    public Map<String, String> getInfoMsg(String type, int info_id, int user_id) {
        Map<String, String> res = new HashMap<>();
        if(groupType.equals(type)) {
            Group group = groupService.queryById(info_id);
            res.put("id", group.getId().toString());
            res.put("photo", group.getPhoto());
            res.put("email", null);
            res.put("name", group.getName());
            res.put("nick", null);
            res.put("notice", group.getNotice());
            res.put("master", group.getMasterId().toString());
        } else {
            Friends friends = friendsService.queryById(info_id);
            if(friends.getUserId1() == user_id) {
                User user = userService.queryById(friends.getUserId2());
                res.put("id", user.getId().toString());
                res.put("photo", user.getPhoto());
                res.put("email", user.getEmail());
                res.put("name", user.getUsername());
                res.put("nick", friends.getNickname2());
            } else {
                User user = userService.queryById(friends.getUserId1());
                res.put("id", user.getId().toString());
                res.put("photo", user.getPhoto());
                res.put("email", user.getEmail());
                res.put("name", user.getUsername());
                res.put("nick", friends.getNickname1());
            }
        }
        return res;
    }

    public List<Map<String, String>> finding(int user_id, String type) {
        List<Map<String, String>> ans = new ArrayList<>();
        if (groupType.equals(type)) {
            List<Group> groups = groupService.queryAll(new Group(null, null, null, null, null,null,0));
            List<Group> res = new ArrayList<>();
            while (groups.size() > 0 && res.size() < 5) {
                int random = new Random().nextInt(groups.size());
                Group randomGroup = groups.remove(random);
                List<GroupUser> groupUsers = groupUserService.queryAll(new GroupUser(randomGroup.getId(), user_id, null, null, null));
                if (groupUsers.size() <= 0) {
                    res.add(randomGroup);
                }
            }
            for (Group group : res) {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", group.getId().toString());
                map.put("name", group.getName());
                map.put("photo", group.getPhoto());
                map.put("master", group.getMasterId().toString());
                ans.add(map);
            }
        } else {
            List<User> users = userService.queryAll(new User(null, null, null, null, 0));
            List<User> res = new ArrayList<>();
            while (users.size() > 0 && res.size() < 10) {
                int random = new Random().nextInt(users.size());
                User randomUser = users.remove(random);
                List<Friends> friends = friendsService.queryAll(new Friends(user_id, randomUser.getId(), null, null, null, null));
                friends.addAll(friendsService.queryAll(new Friends(randomUser.getId(), user_id, null, null, null, null)));
                if (randomUser.getId() != user_id && friends.size() <= 0) {
                    res.add(randomUser);
                }
            }
            for (User user : res) {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", user.getId().toString());
                map.put("name", user.getUsername());
                map.put("photo", user.getPhoto());
                map.put("master", "");
                ans.add(map);
            }
        }
        return ans;
    }
}
