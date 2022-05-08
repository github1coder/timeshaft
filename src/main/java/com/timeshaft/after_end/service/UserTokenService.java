package com.timeshaft.after_end.service;

import com.timeshaft.after_end.entity.UserToken;

import java.util.List;

/**
 * (UserToken)表服务接口
 *
 * @author makejava
 * @since 2022-05-08 20:32:05
 */
public interface UserTokenService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserToken queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserToken> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param userToken 实例对象
     * @return 实例对象
     */
    UserToken insert(UserToken userToken);

    /**
     * 修改数据
     *
     * @param userToken 实例对象
     * @return 实例对象
     */
    UserToken update(UserToken userToken);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<UserToken> queryAll(UserToken userToken);

}