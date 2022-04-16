package com.timeshaft.after_end.service.addressList;

import com.timeshaft.after_end.entity.Group;
import com.timeshaft.after_end.entity.GroupUser;
import com.timeshaft.after_end.service.GroupService;
import com.timeshaft.after_end.service.GroupUserService;
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

    public boolean createGroup(String name, String photo,
                               String notice, int master_id) {
        Group group = new Group(name, master_id, notice, photo, new Date());
        return groupService.insert(group) != null;
    }

    public boolean deleteGroup(int id) {
        return groupService.deleteById(id);
    }

    public boolean updateGroup(int id, String name, String photo, String notice) {
        Group group = groupService.queryById(id);
        group.setName(name);
        group.setNotice(notice);
        group.setPhoto(photo);
        return groupService.update(group) != null;
    }

    private int user_id;

    public List<Group> getGroup() {
        GroupUser groupUser = new GroupUser(null, null, user_id, null, null);
        List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
        List<Group> groups = new ArrayList<>();
        for(GroupUser tmp : groupUsers) {
            groups.add(groupService.queryById(tmp.getGroupId()));
        }
        return groups;
    }

    public boolean joinGroup(int group_id, int join_user_id) {
        GroupUser groupUser = new GroupUser(groupUserService.count() + 1, group_id, join_user_id, null, "member");
        return groupUserService.insert(groupUser) != null;
    }

    public boolean quitGroup(int group_id, int quit_user_id) {
        GroupUser groupUser = new GroupUser(null, group_id, quit_user_id, null, null);
        List<GroupUser> groupUsers = groupUserService.queryAll(groupUser);
        for(GroupUser tmp : groupUsers) {
            groupUserService.deleteById(tmp.getGroupId());
        }
        return true;
    }
}
