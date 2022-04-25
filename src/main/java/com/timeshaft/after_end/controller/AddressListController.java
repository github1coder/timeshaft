package com.timeshaft.after_end.controller;


import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.entity.Group;
import com.timeshaft.after_end.service.addressList.FriendOp;
import com.timeshaft.after_end.service.addressList.GroupOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/AddressList")  //映射URL
public class AddressListController {
    @Autowired
    private FriendOp friendOp;
    @Autowired
    private GroupOp groupOp;

    @RequestMapping(value = "/getFriends")
    public List<Friends> getFriends(@SessionAttribute("user_id") Integer user_id) {
        return friendOp.getFriends(user_id);
    }

    @RequestMapping(value = "/addFriend")
    public boolean addFriend(@SessionAttribute("user_id") Integer user_id, @RequestParam(value = "friend_id") Integer friend_id) {
        return friendOp.addFriend(user_id, friend_id);
    }

    @RequestMapping(value = "/delFriend")
    public boolean delFriend(@SessionAttribute("user_id") Integer user_id, @RequestParam(value = "friend_id") Integer friend_id) {
        return friendOp.delFriend(user_id, friend_id);
    }

    @RequestMapping(value = "/changeNickname")
    public boolean changeNickname(@SessionAttribute("user_id") Integer user_id, @RequestBody Map<String, String> map) {
        return friendOp.changeNickname(user_id, Integer.parseInt(map.get("friend_id")), map.get("friend_nickname"));
    }

    @RequestMapping(value = "/getGroups")
    public List<Group> getGroups(@SessionAttribute("user_id") Integer user_id) {
        return groupOp.getGroup(user_id);
    }

    @RequestMapping(value = "/addGroup")
    public boolean addGroup(@SessionAttribute("user_id") Integer user_id, @RequestBody Map<String, String> map) {
        return groupOp.createGroup(map.get("name"), map.get("photo"), map.get("notice"), user_id);
    }

    @RequestMapping(value = "/updateGroup")
    public boolean updateGroup(@RequestBody Map<String, String> map) {
        return groupOp.updateGroup(Integer.parseInt(map.get("id")), map.get("name"), map.get("photo"), map.get("notice"));
    }

    @RequestMapping(value = "/joinGroup")
    public boolean joinGroup(@RequestBody Map<String, String> map) {
        return groupOp.joinGroup(Integer.parseInt(map.get("group_id")), Integer.parseInt(map.get("join_user_id")));
    }

    @RequestMapping(value = "/quitGroup")
    public boolean quitGroup(@RequestBody Map<String, String> map) {
        return groupOp.quitGroup(Integer.parseInt(map.get("group_id")), Integer.parseInt(map.get("quit_user_id")));
    }

    @RequestMapping(value = "/delGroup")
    public boolean delGroup(@RequestParam(value = "group_id") Integer group_id) {
        return groupOp.deleteGroup(group_id);
    }

    @RequestMapping(value = "/delGroupManager")
    public boolean delGroupManager(@RequestBody Map<String, String> map) {
        return groupOp.delManager(Integer.parseInt(map.get("group_id")), Integer.parseInt(map.get("user_id")));
    }

    @RequestMapping(value = "/addGroupManager")
    public boolean addGroupManager(@RequestBody Map<String, String> map) {
        return groupOp.addManager(Integer.parseInt(map.get("group_id")), Integer.parseInt(map.get("user_id")));
    }

    @RequestMapping(value = "/changeGroupNickname")
    public boolean changeGroupNickname(@RequestBody Map<String, String> map, @SessionAttribute("user_id") Integer user_id) {
        return groupOp.changeNickname(Integer.parseInt(map.get("group_id")), map.get("nickname"), user_id);
    }

}
