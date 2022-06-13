package com.timeshaft.after_end.service.addressList;

import com.timeshaft.after_end.annotation.PermissionAnnotation;
import com.timeshaft.after_end.entity.*;
import com.timeshaft.after_end.service.FriendsService;
import com.timeshaft.after_end.service.GroupHeatService;
import com.timeshaft.after_end.service.GroupService;
import com.timeshaft.after_end.service.GroupUserService;
import com.timeshaft.after_end.service.impl.GroupMessageServiceImpl;
import com.timeshaft.after_end.service.impl.GroupMessageStateServiceImpl;
import com.timeshaft.after_end.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class GroupOp {
    @Resource(name = "GroupService")
    private GroupService groupService;
    @Resource(name = "GroupUserService")
    private GroupUserService groupUserService;
    @Autowired
    private GroupHeatService groupHeatService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private GroupMessageServiceImpl groupMessageService;
    @Autowired
    private GroupMessageStateServiceImpl groupMessageStateService;
    @Autowired
    private FriendsService friendsService;

    @Value("${groupIdentity.manager}")
    private String MANAGER;
    @Value("${groupIdentity.member}")
    private String MEMBER;
    @Value("${friendState.neww}")
    private String NEW;
    @Value("${friendState.acceptt}")
    private String ACCEPT;
    @Value("${type.groupType}")
    private String GROUP;
    @Value("${type.messageRead}")
    private String READ;
    @Value("${type.messageNotRead}")
    private String UNREAD;

    public void createGroup(String name, String photo,
                            String notice, int master_id) {
        Group group = new Group(name, master_id, notice, photo, new Date(), "offMeeting", 0);
        group = groupService.insert(group);
        User user = userService.queryById(master_id);
        GroupUser groupUser = new GroupUser(group.getId(), master_id, user.getUsername(), "master", ACCEPT, null);
        groupUserService.insert(groupUser);
        Date date = new Date(System.currentTimeMillis());
        GroupMessage helloMessage = new GroupMessage();
        helloMessage.setSendtime(date);
        helloMessage.setMessage("欢迎来到" + group.getName());
        helloMessage.setGroupId(groupUser.getGroupId());
        helloMessage.setSenderId(groupUser.getUserId());
        GroupMessage inserted = groupMessageService.insert(helloMessage);
        GroupMessageState groupMessageState = new GroupMessageState();
        groupMessageState.setState(READ);
        groupMessageState.setMessageId(inserted.getId());
        groupMessageState.setUserId(inserted.getSenderId());
        groupMessageStateService.insert(groupMessageState);
        groupHeatService.insert(new GroupHeat(group.getId(), 0, 0, GROUP));
    }

    @PermissionAnnotation(level=11)
    public void deleteGroup(int user_id, int group_id) {
        List<GroupUser> groupUsers = groupUserService.queryAll(new GroupUser(group_id, null, null, null, null, null));
        for(GroupUser groupUser : groupUsers) {
            groupUserService.deleteById(groupUser.getId());
        }
        groupService.deleteById(group_id);

        List<GroupHeat> groupHeats = groupHeatService.queryAll(new GroupHeat(group_id, null, null, GROUP));
        for(GroupHeat groupHeat : groupHeats) {
            groupHeatService.deleteById(groupHeat.getId());
        }
    }

    @PermissionAnnotation(level=12)
    public void updateGroup(Integer user_id, int group_id, String name, String notice, boolean visibility) {
        Group group = groupService.queryById(group_id);
        group.setName(name);
        group.setNotice(notice);
        if(visibility) {
            group.setPrivate1(0);
        } else {
            group.setPrivate1(1);
        }
        groupService.update(group);
    }

    public List<Group> getGroup(int user_id) {
        GroupUser groupUser = new GroupUser(null, user_id, null, null, ACCEPT, null);
        List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
        List<Group> groups = new ArrayList<>();
        for(GroupUser tmp : groupUsers) {
            groups.add(groupService.queryById(tmp.getGroupId()));
        }
        return groups;
    }

    @PermissionAnnotation(level=13)
    public void joinGroup(int user_id, int group_id, int join_user_id) {
        User user = userService.queryById(user_id);
        GroupUser groupUser = new GroupUser(group_id, join_user_id, user.getUsername(), MEMBER, ACCEPT, null);
        groupUserService.insert(groupUser);
    }

    @PermissionAnnotation(level=13)
    public void quitGroup(int group_id, int user_id) {
        GroupUser groupUser = new GroupUser(group_id, user_id, null, null, null, null);
        List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
        for(GroupUser tmp : groupUsers) {
            groupUserService.deleteById(tmp.getId());
        }
    }

    @PermissionAnnotation(level=11)
    public void addManager(int id, int group_id, int user_id) {
        GroupUser groupUser = new GroupUser(group_id, id, null, null, null, null);
        List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
        for(GroupUser tmp : groupUsers) {
            tmp.setIdentity(MANAGER);
            groupUserService.update(tmp);
        }
    }

    @PermissionAnnotation(level=11)
    public void delManager(int id, int group_id, int user_id) {
        GroupUser groupUser = new GroupUser(group_id, id, null, MANAGER, null,null);
        List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
        for (GroupUser tmp : groupUsers) {
            tmp.setIdentity(MEMBER);
            groupUserService.update(tmp);
        }
    }

    @PermissionAnnotation(level=13)
    public void changeNickname(int group_id, String name, int user_id) {
        GroupUser groupUser = new GroupUser(group_id, user_id, null, null, null, null);
        List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
        for (GroupUser tmp : groupUsers) {
            tmp.setUserNickname(name);
            groupUserService.update(tmp);
        }
    }

    @PermissionAnnotation(level=39)
    public Integer getHeat(int group_id, String type, int user_id) throws Exception {
        List<GroupHeat> groupHeats = groupHeatService.queryAll(new GroupHeat(group_id, null, null, type));
        if (groupHeats.size() <= 0) {
            throw new Exception("抱歉，该群不存在");
        }
        Integer groupHeat = groupHeats.get(0).getGroupHeat();
        if (groupHeat<=5) return 0;
        else if (groupHeat<=20) return 1;
        else if (groupHeat<=40) return 2;
        else if (groupHeat<=80) return 3;
        else return 4;
    }

    @PermissionAnnotation(level=13)
    public ArrayList<Map<String, String>> getFNotInG(int group_id, int user_id) {
        ArrayList<Map<String, String>> res = new ArrayList<>();
        List<Friends> friends = friendsService.queryAll(new Friends(user_id, null, null, null, ACCEPT, null));
        friends.addAll(friendsService.queryAll(new Friends(null, user_id, null, null, ACCEPT, null)));
        for(Friends friend : friends) {
            int id;
            if(friend.getUserId1() == user_id) {
                id = friend.getUserId2();
            } else {
                id = friend.getUserId1();
            }
            if(groupUserService.queryAll(new GroupUser(group_id, id, null, null, null, null)).isEmpty()) {
                User user = userService.queryById(id);
                HashMap<String, String> out = new HashMap<>();
                out.put("friend_id", user.getId().toString());
                out.put("friend_name", user.getUsername());
                out.put("friend_photo", user.getPhoto());
                out.put("mail", user.getEmail());
                out.put("chat_id", friend.getId().toString());
                res.add(out);
            }
        }
        return res;
    }

    public ArrayList<Map<String, String>> getInviteList(int user_id) {
        ArrayList<Map<String, String>> res = new ArrayList<>();
        List<GroupUser> groupUsers = groupUserService.queryAll(new GroupUser(null, user_id, null, null, NEW, null));
        for(GroupUser groupUser : groupUsers) {
            if(groupUser.getInvite() != null && groupUser.getInvite() != 0) {
                HashMap<String, String> out = new HashMap<>();
                out.put("id", groupUser.getGroupId().toString());
                Group group = groupService.queryById(groupUser.getGroupId());
                out.put("name", group.getName());
                User user = userService.queryById(groupUser.getInvite());
                out.put("inviteName", user.getUsername());
                res.add(out);
            }
        }
        return res;
    }

    public List<Map<String, Object>> getAllChannel(Integer user_id) throws Exception {
        ArrayList<Map<String, Object>> res = new ArrayList<>();
        List<Friends> friends = friendsService.queryAll(new Friends(user_id, null, null, null, "accept", null));
        friends.addAll(friendsService.queryAll(new Friends(null, user_id, null, null, "accept", null)));
        List<GroupUser> groupUsers = groupUserService.queryAll(new GroupUser(null, user_id, null, null, "accept", null));
        for(Friends friend : friends) {
            Map<String, Object> out = new HashMap<>();
            User user;
            if(friend.getUserId1().equals(user_id)) {
                user = userService.queryById(friend.getUserId2());
            } else {
                user = userService.queryById(friend.getUserId1());
            }
            out.put("chatId", friend.getId());
            out.put("name", user.getUsername());
            out.put("type", "friend");
            res.add(out);
        }
        for(GroupUser groupUser: groupUsers) {
            Map<String, Object> out = new HashMap<>();
            Group group = groupService.queryById(groupUser.getGroupId());
            out.put("chatId", group.getId());
            out.put("name", group.getName());
            out.put("type", "group");
            res.add(out);
        }
        return res;
    }
}
