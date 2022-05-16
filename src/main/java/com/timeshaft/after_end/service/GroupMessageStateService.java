package com.timeshaft.after_end.service;

import com.timeshaft.after_end.entity.GroupMessageState;

import java.util.List;

/**
 * (GroupMessageState)表服务接口
 *
 * @author makejava
 * @since 2022-04-12 14:24:48
 */
public interface GroupMessageStateService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GroupMessageState queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<GroupMessageState> queryAllByLimit(int offset, int limit);

    /**
     * 通过实体查询
     *
     * @param groupMessageState 查询条件实体
     * @return 对象列表
     */
    List<GroupMessageState> queryAll(GroupMessageState groupMessageState);

    /**
     * 新增数据
     *
     * @param groupMessageState 实例对象
     * @return 实例对象
     */
    GroupMessageState insert(GroupMessageState groupMessageState);

    /**
     * 修改数据
     *
     * @param groupMessageState 实例对象
     * @return 实例对象
     */
    GroupMessageState update(GroupMessageState groupMessageState);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}