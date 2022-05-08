package com.timeshaft.after_end.service.addressList;

import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.entity.Group;
import com.timeshaft.after_end.entity.GroupUser;
import com.timeshaft.after_end.entity.User;
import com.timeshaft.after_end.service.FriendsService;
import com.timeshaft.after_end.service.GroupService;
import com.timeshaft.after_end.service.GroupUserService;
import com.timeshaft.after_end.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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



    public List<Friends> getFriends(Integer id) {
        Friends friend1 = new Friends(id, null, null, null, null, null);
        Friends friend2 = new Friends(null, id, null, null, null, null);
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
            List<Group> groups = groupService.queryAll(new Group(name, null, null, null, null, null));
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
            List<User> users = userService.queryAll(new User(null, null, name, null));
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
                    friend1.setNickname2("");
                    friend1.setNickname1("");
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
}
