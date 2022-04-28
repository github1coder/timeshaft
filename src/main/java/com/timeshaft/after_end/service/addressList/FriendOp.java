package com.timeshaft.after_end.service.addressList;

import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.service.FriendsService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class FriendOp {

    @Resource(name = "FriendsService")
    private FriendsService friendsService;

//    public static FriendOp friendOp;
//
//    @PostConstruct
//    private void init() {
//        friendOp = this;
//        friendOp.friendsService = this.friendsService;
//    }

    public List<Friends> getFriends(int id) {
        Friends friend1 = new Friends(id, null, null, null);
        Friends friend2 = new Friends(null, id, null, null);
        List<Friends> friends = friendsService.queryAll(friend1);
        friends.addAll(friendsService.queryAll(friend2));
        return friends;
    }

    public boolean addFriend(int friend1, int friend2) {
        Friends friend = new Friends(friend1, friend2, null, null);
        return friendsService.insert(friend) != null;
    }

    public boolean delFriend(int friend1, int friend2) {
        Friends friend = new Friends(friend1, friend2, null, null);
        List<Friends> friends = friendsService.queryAll(friend);
        Friends friend_2 = new Friends(friend2, friend1, null, null);
        friends.addAll(friendsService.queryAll(friend_2));
        for(Friends tmp : friends){
            friendsService.deleteById(tmp.getId());
        }
        return true;
    }

    public boolean changeNickname(int self_id, int friend_id, String name) {
        Friends friend1 = new Friends(self_id, friend_id, null, null);
        List<Friends> friends = friendsService.queryAll(friend1);
        for(Friends tmp : friends){
            tmp.setNickname2(name);
            return true;
        }
        Friends friend2 = new Friends(friend_id, self_id, null, null);
        friends.addAll(friendsService.queryAll(friend2));
        for(Friends tmp : friends){
            tmp.setNickname1(name);
        }
        return true;
    }
}
