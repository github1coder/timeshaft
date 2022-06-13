package com.timeshaft.after_end.service;

import com.timeshaft.after_end.entity.Star;
import com.timeshaft.after_end.entity.Timeshaft;

import java.util.List;

/**
 * (Star)表服务接口
 *
 * @author makejava
 * @since 2022-06-11 12:23:01
 */
public interface StarService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Star queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Star> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param star 实例对象
     * @return 实例对象
     */
    Star insert(Star star);

    /**
     * 修改数据
     *
     * @param star 实例对象
     * @return 实例对象
     */
    Star update(Star star);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    List<Star> queryAll(Star star);
}