package com.timeshaft.after_end.service;


import com.timeshaft.after_end.entity.GroupMessage;
import com.timeshaft.after_end.entity.PersonalMessage;
import com.timeshaft.after_end.entity.Timeshaft;

import java.util.List;

/**
 * (PersonalMessage)表服务接口
 *
 * @author makejava
 * @since 2022-04-12 14:24:48
 */
public interface PersonalMessageService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PersonalMessage queryById(Integer id);

    /**
     * 通过id组合与索引查询最近历史消息（默认20条）
     *
     * @param friendsId 接收者id
     * @param senderId 发送者id
     * @param index 查询起始的id
     * @return 实例对象列表
     */
    List<PersonalMessage> queryHistoryById(Integer friendsId, Integer senderId, Integer index);

    /**
     * 查询最新消息
     *
     * @return 实例对象
     */
    PersonalMessage queryLatest();

    /**
     * 通过id组合与索引查询最新历史消息
     *
     * @param friendsId 接收者id
     * @param senderId 发送者id
     * @return 实例对象
     */
    PersonalMessage queryLatestById(Integer friendsId, Integer senderId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<PersonalMessage> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param personalMessage 实例对象
     * @return 实例对象
     */
    PersonalMessage insert(PersonalMessage personalMessage);

    /**
     * 修改数据
     *
     * @param personalMessage 实例对象
     * @return 实例对象
     */
    PersonalMessage update(PersonalMessage personalMessage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<PersonalMessage> queryTimeshaft(Timeshaft timeshaft);
    /**
     * 查询符合条件的私信
     *
     * @param personalMessage 包含查询信息的实体
     * @return 查询结果列表
     */
    List<PersonalMessage> queryAll(PersonalMessage personalMessage);

}