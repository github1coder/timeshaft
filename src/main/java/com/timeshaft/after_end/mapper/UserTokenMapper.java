package com.timeshaft.after_end.mapper;

import com.timeshaft.after_end.entity.UserToken;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (UserToken)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-08 20:32:03
 */
public interface UserTokenMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserToken queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserToken> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param userToken 实例对象
     * @return 对象列表
     */
    List<UserToken> queryAll(UserToken userToken);

    /**
     * 新增数据
     *
     * @param userToken 实例对象
     * @return 影响行数
     */
    int insert(UserToken userToken);

    /**
     * 修改数据
     *
     * @param userToken 实例对象
     * @return 影响行数
     */
    int update(UserToken userToken);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}