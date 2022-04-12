package com.timeshaft.after_end.service;

import com.timeshaft.after_end.entity.Group;
import java.util.List;

/**
 * (Group)表服务接口
 *
 * @author makejava
 * @since 2022-04-12 14:24:47
 */
public interface GroupService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Group queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Group> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param group 实例对象
     * @return 实例对象
     */
    Group insert(Group group);

    /**
     * 修改数据
     *
     * @param group 实例对象
     * @return 实例对象
     */
    Group update(Group group);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}