package com.timeshaft.after_end.service;

import com.timeshaft.after_end.entity.Friends;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Friends)表服务接口
 *
 * @author makejava
 * @since 2022-04-12 14:19:23
 */

public interface FriendsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Friends queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Friends> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param friends 实例对象
     * @return 实例对象
     */
    Friends insert(Friends friends);

    /**
     * 修改数据
     *
     * @param friends 实例对象
     * @return 实例对象
     */
    Friends update(Friends friends);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<Friends> queryAll(Friends friends);

    int count();
}