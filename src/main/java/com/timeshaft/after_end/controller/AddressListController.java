package com.timeshaft.after_end.controller;


import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.entity.Group;
import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.addressList.GroupOp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/addresslist")  //映射URL
public class AddressListController {
    @Autowired
    private FriendOp friendOp;
    @Autowired
    private GroupOp groupOp;

    @RequestMapping(value = "/getFriends")
    public ResponseService getFriends(@SessionAttribute("user_id") Integer user_id) {
        List<Friends> res = friendOp.getFriends(user_id);
        return new ResponseService(res);
    }

    @RequestMapping(value = "/addFriend")
    public ResponseService addFriend(@SessionAttribute("user_id") Integer user_id, @RequestParam(value = "friend_id") Integer friend_id) {
        friendOp.addFriend(user_id, friend_id);
        return new ResponseService();
    }

    @RequestMapping(value = "/delFriend")
    public ResponseService delFriend(@SessionAttribute("user_id") Integer user_id, @RequestParam(value = "friend_id") Integer friend_id) {
        friendOp.delFriend(user_id, friend_id);
        return new ResponseService();
    }

    @RequestMapping(value = "/changeNickname")
    public ResponseService changeNickname(@SessionAttribute("user_id") Integer user_id, @RequestBody Map<String, String> map) {
        friendOp.changeNickname(user_id, Integer.parseInt(map.get("friend_id")), map.get("friend_nickname"));
        return new ResponseService();
    }

    @RequestMapping(value = "/getGroups")
    public ResponseService getGroups(@SessionAttribute("user_id") Integer user_id) {
        List<Group> res = groupOp.getGroup(user_id);
        return new ResponseService(res);
    }

    @RequestMapping(value = "/addGroup")
    public ResponseService addGroup(@SessionAttribute("user_id") Integer user_id, @RequestBody Map<String, String> map) {
        groupOp.createGroup(map.get("name"), map.get("photo"), map.get("notice"), user_id);
        return new ResponseService();
    }

    @RequestMapping(value = "/updateGroup")
    public ResponseService updateGroup(@RequestBody Map<String, String> map) {
        groupOp.updateGroup(Integer.parseInt(map.get("id")), map.get("name"), map.get("photo"), map.get("notice"));
        return new ResponseService();
    }

    @RequestMapping(value = "/joinGroup")
    public ResponseService joinGroup(@RequestBody Map<String, String> map) {
        groupOp.joinGroup(Integer.parseInt(map.get("group_id")), Integer.parseInt(map.get("join_user_id")));
        return new ResponseService();
    }

    @RequestMapping(value = "/quitGroup")
    public ResponseService quitGroup(@RequestBody Map<String, String> map) {
        groupOp.quitGroup(Integer.parseInt(map.get("group_id")), Integer.parseInt(map.get("quit_user_id")));
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
    public ResponseService changeGroupNickname(@RequestBody Map<String, String> map, @SessionAttribute("user_id") Integer user_id) {
        groupOp.changeNickname(Integer.parseInt(map.get("group_id")), map.get("nickname"), user_id);
        return new ResponseService();
    }

    @RequestMapping(value = "/searchByNick")
    public List<Map<String, String>> searchByNick(@RequestParam(value = "name") String name, @RequestParam(value = "type") String type, @SessionAttribute("user_id") Integer user_id) {
        return friendOp.searchByNick(name, type, user_id);
    }

    @RequestMapping(value = "/apply")
    public boolean apply(@RequestParam(value = "type") String type, @RequestParam(value = "action") String action, @RequestParam(value = "id") Integer id, @SessionAttribute("user_id") Integer user_id) {
        return friendOp.apply(user_id, type, action, id);
    }

    @RequestMapping(value = "/getApplyList")
    public List<Map<String, String>> getApplyList(@RequestParam(value = "type") String type, @SessionAttribute("user_id") Integer user_id) {
        return friendOp.getApplyList(type, user_id);
    }
}
