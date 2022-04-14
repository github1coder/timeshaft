package com.timeshaft.after_end.service.impl;

import com.timeshaft.after_end.entity.GroupUser;
import com.timeshaft.after_end.mapper.GroupUserMapper;
import com.timeshaft.after_end.service.GroupUserService;
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
public class GroupUserServiceImpl implements GroupUserService {
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
}