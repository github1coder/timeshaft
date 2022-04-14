package com.timeshaft.after_end.service.impl;

import com.timeshaft.after_end.entity.GroupMessage;
import com.timeshaft.after_end.mapper.GroupMessageMapper;
import com.timeshaft.after_end.service.GroupMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (GroupMessage)表服务实现类
 *
 * @author makejava
 * @since 2022-04-12 14:24:47
 */
@Service("groupMessageService")
public class GroupMessageServiceImpl implements GroupMessageService {
    @Resource
    private GroupMessageMapper groupMessageMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public GroupMessage queryById(Integer id) {
        return this.groupMessageMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<GroupMessage> queryAllByLimit(int offset, int limit) {
        return this.groupMessageMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param groupMessage 实例对象
     * @return 实例对象
     */
    @Override
    public GroupMessage insert(GroupMessage groupMessage) {
        this.groupMessageMapper.insert(groupMessage);
        return groupMessage;
    }

    /**
     * 修改数据
     *
     * @param groupMessage 实例对象
     * @return 实例对象
     */
    @Override
    public GroupMessage update(GroupMessage groupMessage) {
        this.groupMessageMapper.update(groupMessage);
        return this.queryById(groupMessage.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.groupMessageMapper.deleteById(id) > 0;
    }
}