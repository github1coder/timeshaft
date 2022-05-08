package com.timeshaft.after_end.service.addressList;

import com.timeshaft.after_end.entity.Group;
import com.timeshaft.after_end.entity.GroupUser;
import com.timeshaft.after_end.service.GroupService;
import com.timeshaft.after_end.service.GroupUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GroupOp {
    @Resource(name = "GroupService")
    private GroupService groupService;

    @Resource(name = "GroupUserService")
    private GroupUserService groupUserService;

    @Value("${groupIdentity.manager}")
    private String MANAGER;
    @Value("${groupIdentity.member}")
    private String MEMBER;
    @Value("${friendState.neww}")
    private String NEW;
    @Value("${friendState.acceptt}")
    private String ACCEPT;


    public void createGroup(String name, String photo,
                            String notice, int master_id) {
        Group group = new Group(name, master_id, notice, photo, new Date(), "");
        group = groupService.insert(group);
        GroupUser groupUser = new GroupUser(group.getId(), master_id, null, "master", ACCEPT);
        groupUserService.insert(groupUser);
    }

    public void deleteGroup(int id) {
        List<GroupUser> groupUsers = groupUserService.queryAll(new GroupUser(id, null, null, null, null));
        for(GroupUser groupUser : groupUsers) {
            groupUserService.deleteById(groupUser.getId());
        }
        groupService.deleteById(id);
    }

    public void updateGroup(int id, String name, String photo, String notice) {
        Group group = groupService.queryById(id);
        group.setName(name);
        group.setNotice(notice);
        group.setPhoto(photo);
        groupService.update(group);
    }

    public List<Group> getGroup(int user_id) {
        GroupUser groupUser = new GroupUser(null, user_id, null, null, ACCEPT);
        List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
        List<Group> groups = new ArrayList<>();
        for(GroupUser tmp : groupUsers) {
            groups.add(groupService.queryById(tmp.getGroupId()));
        }
        return groups;
    }

    public void joinGroup(int group_id, int join_user_id) {
        GroupUser groupUser = new GroupUser(group_id, join_user_id, null, MEMBER, ACCEPT);
        groupUserService.insert(groupUser);
    }

    public void quitGroup(int group_id, int quit_user_id) {
        GroupUser groupUser = new GroupUser(group_id, quit_user_id, null, null, null);
        List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
        for(GroupUser tmp : groupUsers) {
            groupUserService.deleteById(tmp.getId());
        }
    }

    public void addManager(int group_id, int user_id) {
        GroupUser groupUser = new GroupUser(group_id, user_id, null, null, null);
        List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
        for(GroupUser tmp : groupUsers) {
            tmp.setIdentity(MANAGER);
            groupUserService.update(tmp);
        }
    }

    public void delManager(int group_id, int user_id) {
        GroupUser groupUser = new GroupUser(group_id, user_id, null, MANAGER, null);
        List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
        for (GroupUser tmp : groupUsers) {
            tmp.setIdentity(MEMBER);
            groupUserService.update(tmp);
        }
    }

    public void changeNickname(int group_id, String name, int user_id) {
        GroupUser groupUser = new GroupUser(group_id, user_id, null, null, null);
        List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
        for (GroupUser tmp : groupUsers) {
            tmp.setUserNickname(name);
            groupUserService.update(tmp);
        }
    }
}
