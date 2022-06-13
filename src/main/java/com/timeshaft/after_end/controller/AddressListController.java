package com.timeshaft.after_end.controller;


import com.timeshaft.after_end.annotation.RequestLog;
import com.timeshaft.after_end.entity.*;
import com.timeshaft.after_end.service.FriendsService;
import com.timeshaft.after_end.service.GroupService;
import com.timeshaft.after_end.service.GroupUserService;
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
    @Autowired
    private GroupUserService groupUserService;
    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/getFriends")
    @RequestLog
    public ResponseService getFriends(@RequestHeader("user_id") Integer user_id) {
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
    @RequestLog
    public ResponseService addFriend(@RequestHeader("user_id") Integer user_id, @RequestParam(value = "friend_id") Integer friend_id) {
        friendOp.addFriend(user_id, friend_id);
        return new ResponseService();
    }

    @RequestMapping(value = "/delFriend")
    @RequestLog
    public ResponseService delFriend(@RequestHeader("user_id") Integer user_id, @RequestParam(value = "friend_id") Integer friend_id) {
        friendOp.delFriend(user_id, friend_id);
        return new ResponseService();
    }

    @RequestMapping(value = "/changeNickname")
    @RequestLog
    public ResponseService changeNickname(@RequestBody Map<String, String> map, @RequestHeader("user_id") Integer user_id) {
        friendOp.changeNickname(user_id, Integer.parseInt(map.get("friend_id")), map.get("friend_nickname"));
        return new ResponseService();
    }

    @RequestMapping(value = "/getGroups")
    @RequestLog
    public ResponseService getGroups(@RequestHeader("user_id") Integer user_id) {
        List<Group> groups = groupOp.getGroup(user_id);
        List<Map<String, Object>> res = new ArrayList<>();
        for(Group group : groups) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("group_id", group.getId().toString());
            map.put("group_name", group.getName());
            map.put("group_photo", group.getPhoto());
            map.put("master_id", group.getMasterId().toString());
            map.put("notice", group.getNotice());
            if(group.getPrivate1() == 1) {
                map.put("state", false);
            } else {
                map.put("state", true);
            }
            res.add(map);
        }
        return new ResponseService(res);
    }

    @RequestMapping(value = "/addGroup")
    @RequestLog
    public ResponseService addGroup(@RequestBody Map<String, String> map, @RequestHeader("user_id") Integer user_id) {
        groupOp.createGroup(map.get("name"), map.get("static/photo"), map.get("notice"), Integer.parseInt(map.get("master_id")));
        return new ResponseService();
    }

    @RequestMapping(value = "/updateGroup")
    @RequestLog
    public ResponseService updateGroup(@RequestBody Map<String, Object> map, @RequestHeader("user_id") Integer user_id) {
        boolean visibility = (boolean) map.get("state");
        int group_id = (int) map.get("id");
        String name = (String) map.get("name");
        String notice = (String) map.get("notice");
        groupOp.updateGroup(user_id, group_id, name, notice, visibility);
        return new ResponseService();
    }

    @RequestMapping(value = "/joinGroup")
    @RequestLog
    public ResponseService joinGroup(@RequestBody Map<String, String> map, @RequestHeader("user_id") Integer user_id) {
        groupOp.joinGroup(user_id, Integer.parseInt(map.get("group_id")), Integer.parseInt(map.get("join_user_id")));
        return new ResponseService();
    }

    @RequestMapping(value = "/quitGroup")
    @RequestLog
    public ResponseService quitGroup(@RequestBody Map<String, String> map, @RequestHeader("user_id") Integer user_id) {
        groupOp.quitGroup(Integer.parseInt(map.get("group_id")), user_id);
        return new ResponseService();
    }

    @RequestMapping(value = "/delGroup")
    @RequestLog
    public ResponseService delGroup(@RequestBody Map<String, String> map, @RequestHeader("user_id") Integer user_id) {
        groupOp.deleteGroup(user_id, Integer.parseInt(map.get("group_id")));
        return new ResponseService();
    }

    @RequestMapping(value = "/delGroupManager")
    @RequestLog
    public ResponseService delGroupManager(@RequestBody Map<String, String> map, @RequestHeader("user_id") Integer user_id) {
        groupOp.delManager(Integer.parseInt(map.get("id")), Integer.parseInt(map.get("group_id")), user_id);
        return new ResponseService();
    }

    @RequestMapping(value = "/addGroupManager")
    @RequestLog
    public ResponseService addGroupManager(@RequestBody Map<String, String> map, @RequestHeader("user_id") Integer user_id) {
        groupOp.addManager(Integer.parseInt(map.get("id")), Integer.parseInt(map.get("group_id")), user_id);
        return new ResponseService();
    }

    @RequestMapping(value = "/changeGroupNickname")
    @RequestLog
    public ResponseService changeGroupNickname(@RequestBody Map<String, String> map, @RequestHeader("user_id") Integer user_id) {
        groupOp.changeNickname(Integer.parseInt(map.get("group_id")), map.get("nickname"), user_id);
        return new ResponseService();
    }

    @RequestMapping(value = "/searchByNick")
    @RequestLog
    public ResponseService searchByNick(@RequestParam(value = "name") String name, @RequestParam(value = "type") String type, @RequestHeader("user_id") Integer user_id) throws Exception {
        List<Map<String, String>> res = friendOp.searchByNick(name, type, user_id);
        return new ResponseService(res);
    }

    @RequestMapping(value = "/apply")
    @RequestLog
    public ResponseService apply(@RequestParam(value = "invite") Integer invite, @RequestParam(value = "memId") Integer memId, @RequestParam(value = "type") String type, @RequestParam(value = "action") String action, @RequestParam(value = "id") Integer id, @RequestHeader("user_id") Integer user_id) throws Exception {
        friendOp.apply(user_id, type, action, id, memId, invite);
        friendOp.sendNotification(type, action, id, memId, user_id);
        return new ResponseService();
    }

    @RequestMapping(value = "/getApplyList")
    @RequestLog
    public ResponseService getApplyList(@RequestParam(value = "type") String type, @RequestHeader("user_id") Integer user_id) throws Exception {
        List<Map<String, String>> res = friendOp.getApplyList(type, user_id);
        return new ResponseService(res);
    }

    @RequestMapping(value = "/getGroupMember")
    @RequestLog
    public ResponseService getGroupMember(@RequestParam("id") Integer id, @RequestHeader("user_id") Integer user_id) {
        Map<User, String> users = friendOp.getGroupMember(id);
        List<Map<String, String>> res = new ArrayList<>();
        for(User user : users.keySet()) {
            HashMap<String, String> ans = new HashMap<>();
            ans.put("id", user.getId().toString());
            ans.put("name", user.getUsername());
            ans.put("photo", user.getPhoto());
            ans.put("nick", users.get(user));
            ans.put("mail", user.getEmail());
            List<GroupUser> groupUser = groupUserService.queryAll(new GroupUser(id, user.getId(), null, null, null, null));
            if(groupUser.get(0).getIdentity().equals("manager") || groupUser.get(0).getIdentity().equals("master")) {
                ans.put("type", "manager");
            } else {
                ans.put("type", "normal");
            }
            Friends friend1 = new Friends(user_id, user.getId(), null, null, "accept", null);
            Friends friend2 = new Friends(user.getId(), user_id, null, null, "accept", null);
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
    @RequestLog
    public ResponseService getInfoMsg(@RequestParam(value = "type") String type, @RequestParam("info_id") Integer info_id, @RequestHeader("user_id") Integer user_id) throws Exception {
        Map<String, String> res = friendOp.getInfoMsg(type, info_id, user_id);
        return new ResponseService(res);
    }

    @RequestMapping(value = "/finding")
    @RequestLog
    public ResponseService finding(@RequestParam(value = "type") String type, @RequestHeader("user_id") Integer user_id) throws Exception {
        List<Map<String, String>> res = friendOp.finding(user_id, type);
        return new ResponseService(res);
    }

    @RequestMapping(value = "/getHeat")
    @RequestLog
    public ResponseService getHeat(@RequestParam(value = "type") String type, @RequestParam("group_id") Integer group_id, @RequestHeader("user_id") Integer user_id) throws Exception {
        Map<String, Integer> res = new HashMap<>();
        Integer heat = groupOp.getHeat(group_id, type, user_id);
        res.put("heat",heat);
        return new ResponseService(res);
    }

    @RequestMapping(value = "/getFNotInG")
    @RequestLog
    public ResponseService getFNotInG(@RequestParam("id") Integer group_id, @RequestHeader("user_id") Integer user_id) throws Exception {
        ArrayList<Map<String, String>> res = groupOp.getFNotInG(group_id, user_id);
        return new ResponseService(res);
    }

    @RequestMapping(value = "/getInviteList")
    @RequestLog
    public ResponseService getInviteList(@RequestHeader("user_id") Integer user_id) throws Exception {
        ArrayList<Map<String, String>> res = groupOp.getInviteList(user_id);
        return new ResponseService(res);
    }

    @RequestMapping("/getAllChannel")
    public ResponseService getAllChannel(@RequestHeader("user_id") Integer user_id) throws Exception {
        List<Map<String, Object>> res = groupOp.getAllChannel(user_id);
        return new ResponseService(res);
    }
}
