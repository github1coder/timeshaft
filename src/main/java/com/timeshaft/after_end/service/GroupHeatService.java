package com.timeshaft.after_end.service;

import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.entity.GroupHeat;
import java.util.List;

/**
 * (GroupHeat)表服务接口
 *
 * @author makejava
 * @since 2022-05-11 20:05:43
 */
public interface GroupHeatService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GroupHeat queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<GroupHeat> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param groupHeat 实例对象
     * @return 实例对象
     */
    GroupHeat insert(GroupHeat groupHeat);

    /**
     * 修改数据
     *
     * @param groupHeat 实例对象
     * @return 实例对象
     */
    GroupHeat update(GroupHeat groupHeat);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<GroupHeat> queryAll(GroupHeat groupHeat);

}