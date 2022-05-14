package com.timeshaft.after_end.service;

import com.timeshaft.after_end.entity.GroupUser;

import java.util.List;

/**
 * (GroupUser)表服务接口
 *
 * @author makejava
 * @since 2022-04-12 14:24:48
 */
public interface GroupUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GroupUser queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<GroupUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param groupUser 实例对象
     * @return 实例对象
     */
    GroupUser insert(GroupUser groupUser);

    /**
     * 修改数据
     *
     * @param groupUser 实例对象
     * @return 实例对象
     */
    GroupUser update(GroupUser groupUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<GroupUser> queryAll(GroupUser groupUser);

    int count();

    void hasPower(GroupUser groupUser, Integer level) throws Exception;
}