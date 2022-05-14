package com.timeshaft.after_end.service.impl;

import com.timeshaft.after_end.entity.GroupUser;
import com.timeshaft.after_end.mapper.GroupUserMapper;
import com.timeshaft.after_end.service.GroupUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (GroupUser)表服务实现类
 *
 * @author makejava
 * @since 2022-04-12 14:24:48
 */
@Service("GroupUserService")
@Slf4j
public class GroupUserServiceImpl implements GroupUserService {
    @Value("${groupIdentity.manager}")
    private String MANAGER;
    @Value("${groupIdentity.master}")
    private String MASTER;
    @Resource
    private GroupUserMapper groupUserMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public GroupUser queryById(Integer id) {
        return this.groupUserMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<GroupUser> queryAllByLimit(int offset, int limit) {
        return this.groupUserMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param groupUser 实例对象
     * @return 实例对象
     */
    @Override
    public GroupUser insert(GroupUser groupUser) {
        this.groupUserMapper.insert(groupUser);
        return groupUser;
    }

    /**
     * 修改数据
     *
     * @param groupUser 实例对象
     * @return 实例对象
     */
    @Override
    public GroupUser update(GroupUser groupUser) {
        this.groupUserMapper.update(groupUser);
        return this.queryById(groupUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.groupUserMapper.deleteById(id) > 0;
    }

    @Override
    public List<GroupUser> queryAll(GroupUser groupUser) {
        return groupUserMapper.queryAll(groupUser);
    }

    @Override
    public int count() {
        return groupUserMapper.count();
    }

    @Override
    public void hasPower(GroupUser groupUser, Integer level) throws Exception {   //1为群主，2为群主或管理员
        boolean flag = false;
        List<GroupUser> groupUsers = groupUserMapper.queryAll(groupUser);
        if (groupUsers!=null && groupUsers.size()>0) {
            if (level == 2) {
                if (MASTER.equals(groupUsers.get(0).getIdentity()) || MANAGER.equals(groupUsers.get(0).getIdentity())) {
                    flag = true;
                }
            }
            else if (level == 1) {
                if (MASTER.equals(groupUsers.get(0).getIdentity())) {
                    flag = true;
                }
            }
        }
        if (!flag) {
            throw new Exception("你没有权限哦");
        }
    }


}