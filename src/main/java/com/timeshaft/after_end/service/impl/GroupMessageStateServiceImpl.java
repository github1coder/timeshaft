package com.timeshaft.after_end.service.impl;

import com.timeshaft.after_end.entity.GroupMessageState;
import com.timeshaft.after_end.mapper.GroupMessageStateMapper;
import com.timeshaft.after_end.service.GroupMessageStateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (GroupMessageState)表服务实现类
 *
 * @author makejava
 * @since 2022-04-12 14:24:48
 */
@Service("GroupMessageStateService")
public class GroupMessageStateServiceImpl implements GroupMessageStateService {
    @Resource
    private GroupMessageStateMapper groupMessageStateMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public GroupMessageState queryById(Integer id) {
        return this.groupMessageStateMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<GroupMessageState> queryAllByLimit(int offset, int limit) {
        return this.groupMessageStateMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param groupMessageState 实例对象
     * @return 实例对象
     */
    @Override
    public GroupMessageState insert(GroupMessageState groupMessageState) {
        this.groupMessageStateMapper.insert(groupMessageState);
        return groupMessageState;
    }

    /**
     * 修改数据
     *
     * @param groupMessageState 实例对象
     * @return 实例对象
     */
    @Override
    public GroupMessageState update(GroupMessageState groupMessageState) {
        this.groupMessageStateMapper.update(groupMessageState);
        return this.queryById(groupMessageState.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.groupMessageStateMapper.deleteById(id) > 0;
    }
}