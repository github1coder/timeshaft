package com.timeshaft.after_end.service.impl;

import com.timeshaft.after_end.entity.Group;
import com.timeshaft.after_end.mapper.GroupMapper;
import com.timeshaft.after_end.service.GroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Group)表服务实现类
 *
 * @author makejava
 * @since 2022-04-12 14:24:47
 */
@Service("GroupService")
public class GroupServiceImpl implements GroupService {
    @Resource
    private GroupMapper groupMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Group queryById(Integer id) {
        return this.groupMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Group> queryAllByLimit(int offset, int limit) {
        return this.groupMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param group 实例对象
     * @return 实例对象
     */
    @Override
    public Group insert(Group group) {
        this.groupMapper.insert(group);
        return group;
    }

    /**
     * 修改数据
     *
     * @param group 实例对象
     * @return 实例对象
     */
    @Override
    public Group update(Group group) {
        this.groupMapper.update(group);
        return this.queryById(group.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.groupMapper.deleteById(id) > 0;
    }
}