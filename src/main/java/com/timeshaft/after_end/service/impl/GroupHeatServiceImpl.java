package com.timeshaft.after_end.service.impl;

import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.entity.GroupHeat;
import com.timeshaft.after_end.mapper.GroupHeatMapper;
import com.timeshaft.after_end.service.GroupHeatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (GroupHeat)表服务实现类
 *
 * @author makejava
 * @since 2022-05-11 20:05:43
 */
@Service("groupHeatService")
public class GroupHeatServiceImpl implements GroupHeatService {
    @Resource
    private GroupHeatMapper groupHeatMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public GroupHeat queryById(Integer id) {
        return this.groupHeatMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<GroupHeat> queryAllByLimit(int offset, int limit) {
        return this.groupHeatMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param groupHeat 实例对象
     * @return 实例对象
     */
    @Override
    public GroupHeat insert(GroupHeat groupHeat) {
        this.groupHeatMapper.insert(groupHeat);
        return groupHeat;
    }

    /**
     * 修改数据
     *
     * @param groupHeat 实例对象
     * @return 实例对象
     */
    @Override
    public GroupHeat update(GroupHeat groupHeat) {
        this.groupHeatMapper.update(groupHeat);
        return this.queryById(groupHeat.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.groupHeatMapper.deleteById(id) > 0;
    }

    @Override
    public List<GroupHeat> queryAll(GroupHeat groupHeat) {
        return this.groupHeatMapper.queryAll(groupHeat);
    }
}