package com.timeshaft.after_end.mapper;

import com.timeshaft.after_end.entity.GroupMessage;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (GroupMessage)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-12 14:24:47
 */
public interface GroupMessageMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GroupMessage queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<GroupMessage> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param groupMessage 实例对象
     * @return 对象列表
     */
    List<GroupMessage> queryAll(GroupMessage groupMessage);

    /**
     * 新增数据
     *
     * @param groupMessage 实例对象
     * @return 影响行数
     */
    int insert(GroupMessage groupMessage);

    /**
     * 修改数据
     *
     * @param groupMessage 实例对象
     * @return 影响行数
     */
    int update(GroupMessage groupMessage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}