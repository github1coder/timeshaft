package com.timeshaft.after_end.mapper;

import com.timeshaft.after_end.entity.GroupMessage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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

    List<GroupMessage> queryTimeshaft(@Param("group_id") Integer group_id, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 查询用户在群聊中的未读消息
     *
     * @param userId 查询起始位置
     * @param groupId 查询条数
     * @param state 消息状态
     * @return 群聊消息列表
     */
    List<GroupMessage> queryNotReadMessage(int userId, int groupId, String state);

    /**
     * 查询群聊中的最新消息
     *
     * @param groupId 群聊Id
     * @return 群聊消息列表
     */
    GroupMessage queryLatestById(int groupId);

    /**
     * 查询群聊历史消息
     *
     * @param groupId 群聊Id
     * @param index 索引id
     * @return 群聊消息列表
     */
    List<GroupMessage> queryHistoryById(int groupId, int index);

}