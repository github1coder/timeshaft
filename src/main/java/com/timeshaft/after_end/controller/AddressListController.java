package com.timeshaft.after_end.controller;


import com.timeshaft.after_end.entity.*;
import com.timeshaft.after_end.service.FriendsService;
import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.UserService;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.addressList.GroupOp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/addresslist")  //映射URL
public class AddressListController {
    @Autowired
    private FriendOp friendOp;
    @Autowired
    private GroupOp groupOp;
    @Autowired
    private UserService userService;
    @Autowired
    private FriendsService friendsService;

    @RequestMapping(value = "/getFriends")
    public ResponseService getFriends(@RequestParam("user_id") Integer user_id) {
        List<Friends> friends = friendOp.getFriends(user_id);
        List<Map<String, String>> res = new ArrayList<>();
        for(Friends f : friends) {
            HashMap<String, String> map = new HashMap<>();
            User tmp;
            String nick;
            if(f.getUserId1().equals(user_id)) {
                tmp = userService.queryById(f.getUserId2());
                nick = f.getNickname2();
            } else {
                tmp = userService.queryById(f.getUserId1());
                nick = f.getNickname1();
            }
            map.put("friend_id", tmp.getId().toString());
            map.put("friend_name", tmp.getUsername());
            map.put("friend_photo", tmp.getPhoto());
            map.put("friend_nick", nick);
            map.put("mail", userService.queryById(tmp.getId()).getEmail());
            map.put("chat_id", f.getId().toString());
            res.add(map);
        }
        return new ResponseService(res);
    }

    @RequestMapping(value = "/addFriend")
    public ResponseService addFriend(@RequestParam("user_id") Integer user_id, @RequestParam(value = "friend_id") Integer friend_id) {
        friendOp.addFriend(user_id, friend_id);
        return new ResponseService();
    }

    @RequestMapping(value = "/delFriend")
    public ResponseService delFriend(@RequestParam("user_id") Integer user_id, @RequestParam(value = "friend_id") Integer friend_id) {
        friendOp.delFriend(user_id, friend_id);
        return new ResponseService();
    }

    @RequestMapping(value = "/changeNickname")
    public ResponseService changeNickname(@RequestBody Map<String, String> map) {
        friendOp.changeNickname(Integer.parseInt(map.get("user_id")), Integer.parseInt(map.get("friend_id")), map.get("friend_nickname"));
        return new ResponseService();
    }

    @RequestMapping(value = "/getGroups")
    public ResponseService getGroups(@RequestParam("user_id") Integer user_id) {
        List<Group> groups = groupOp.getGroup(user_id);
        List<Map<String, String>> res = new ArrayList<>();
        for(Group group : groups) {
            HashMap<String, String> map = new HashMap<>();
            map.put("group_id", group.getId().toString());
            map.put("group_name", group.getName());
            map.put("group_photo", group.getPhoto());
            map.put("master_id", group.getMasterId().toString());
            res.add(map);
        }
        return new ResponseService(res);
    }

    @RequestMapping(value = "/addGroup")
    public ResponseService addGroup(@RequestBody Map<String, String> map) {
        groupOp.createGroup(map.get("name"), map.get("static/photo"), map.get("notice"), Integer.parseInt(map.get("master_id")));
        return new ResponseService();
    }

    @RequestMapping(value = "/updateGroup")
    public ResponseService updateGroup(@RequestBody Map<String, String> map) {
        groupOp.updateGroup(Integer.parseInt(map.get("id")), map.get("name"), map.get("static/photo"), map.get("notice"));
        return new ResponseService();
    }

    @RequestMapping(value = "/joinGroup")
    public ResponseService joinGroup(@RequestBody Map<String, String> map) {
        groupOp.joinGroup(Integer.parseInt(map.get("group_id")), Integer.parseInt(map.get("join_user_id")));
        return new ResponseService();
    }

    @RequestMapping(value = "/quitGroup")
    public ResponseService quitGroup(@RequestBody Map<String, String> map) {
        groupOp.quitGroup(Integer.parseInt(map.get("group_id")), Integer.parseInt(map.get("user_id")));
        return new ResponseService();
    }

    @RequestMapping(value = "/delGroup")
    public ResponseService delGroup(@RequestParam(value = "group_id") Integer group_id) {
        groupOp.deleteGroup(group_id);
        return new ResponseService();
    }

    @RequestMapping(value = "/delGroupManager")
    public ResponseService delGroupManager(@RequestBody Map<String, String> map) {
        groupOp.delManager(Integer.parseInt(map.get("group_id")), Integer.parseInt(map.get("user_id")));
        return new ResponseService();
    }

    @RequestMapping(value = "/addGroupManager")
    public ResponseService addGroupManager(@RequestBody Map<String, String> map) {
        groupOp.addManager(Integer.parseInt(map.get("group_id")), Integer.parseInt(map.get("user_id")));
        return new ResponseService();
    }

    @RequestMapping(value = "/changeGroupNickname")
    public ResponseService changeGroupNickname(@RequestBody Map<String, String> map) {
        groupOp.changeNickname(Integer.parseInt(map.get("group_id")), map.get("nickname"), Integer.parseInt(map.get("user_id")));
        return new ResponseService();
    }

    @RequestMapping(value = "/searchByNick")
    public ResponseService searchByNick(@RequestParam(value = "name") String name, @RequestParam(value = "type") String type, @RequestParam("user_id") Integer user_id) {
        List<Map<String, String>> res = friendOp.searchByNick(name, type, user_id);
        return new ResponseService(res);
    }

    @RequestMapping(value = "/apply")
    public ResponseService apply(@RequestParam(value = "type") String type, @RequestParam(value = "action") String action, @RequestParam(value = "id") Integer id, @RequestParam("user_id") Integer user_id) {
        friendOp.apply(user_id, type, action, id);
        friendOp.sendNotification(type, action, id, user_id);
        return new ResponseService();
    }

    @RequestMapping(value = "/getApplyList")
    public ResponseService getApplyList(@RequestParam(value = "type") String type, @RequestParam("user_id") Integer user_id) {
        List<Map<String, String>> res = friendOp.getApplyList(type, user_id);
        return new ResponseService(res);
    }

    @RequestMapping(value = "/getGroupMember")
    public ResponseService getGroupMember(@RequestParam("id") Integer id) {
        Map<User, String> users = friendOp.getGroupMember(id);
        List<Map<String, String>> res = new ArrayList<>();
        for(User user : users.keySet()) {
            HashMap<String, String> ans = new HashMap<>();
            ans.put("id", user.getId().toString());
            ans.put("name", user.getUsername());
            ans.put("photo", user.getPhoto());
            ans.put("nick", users.get(user));
            ans.put("mail", user.getEmail());
            Friends friend1 = new Friends(id, user.getId(), null, null, null, "accept");
            Friends friend2 = new Friends(user.getId(), id, null, null, null, "accept");
            List<Friends> friends = friendsService.queryAll(friend1);
            friends.addAll(friendsService.queryAll(friend2));
            if(friends.size() != 0) {
                ans.put("chat_id", friends.get(0).getId().toString());
            } else {
                ans.put("chat_id", "-1");
            }
            res.add(ans);
        }
        return new ResponseService(res);
    }

    @RequestMapping(value = "/getInfoMsg")
    public ResponseService getInfoMsg(@RequestParam(value = "type") String type, @RequestParam("info_id") Integer info_id, @RequestParam("user_id") Integer user_id) {
        Map<String, String> res = friendOp.getInfoMsg(type, info_id, user_id);
        return new ResponseService(res);
    }
}
