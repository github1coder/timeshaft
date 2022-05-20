package com.timeshaft.after_end.service;

import com.timeshaft.after_end.entity.GroupMessage;
import com.timeshaft.after_end.entity.Timeshaft;

import java.util.Date;
import java.util.List;

/**
 * (GroupMessage)表服务接口
 *
 * @author makejava
 * @since 2022-04-12 14:24:47
 */
public interface GroupMessageService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GroupMessage queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<GroupMessage> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param groupMessage 实例对象
     * @return 实例对象
     */
    GroupMessage insert(GroupMessage groupMessage);

    /**
     * 修改数据
     *
     * @param groupMessage 实例对象
     * @return 实例对象
     */
    GroupMessage update(GroupMessage groupMessage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<GroupMessage> queryTimeshaft(Timeshaft timeshaft);

    /**
     * 查询用户在群聊中的消息
     *
     * @param userId 用户Id
     * @param groupId 群聊Id
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
     * @param time 索引时间
     * @return 群聊消息列表
     */
    List<GroupMessage> queryHistoryById(int groupId, Date time);

    /**
     * 通过消息内容进行查询
     *
     * @param groupId 群聊Id
     * @param keyword 关键词
     * @return 实例对象列表
     */
    List<GroupMessage> queryByKeyword(Integer groupId, String keyword);

}