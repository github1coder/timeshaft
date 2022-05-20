package com.timeshaft.after_end.mapper;

import com.timeshaft.after_end.entity.GroupMessage;
import com.timeshaft.after_end.entity.PersonalMessage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * (PersonalMessage)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-12 14:24:48
 */
public interface PersonalMessageMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PersonalMessage queryById(Integer id);

    /**
     * 通过id组合查询最新历史消息
     *
     * @param friendsId 接收者id
     * @param senderId 发送者id
     * @return 实例对象
     */
    PersonalMessage queryLatestById(@Param("friendsId") Integer friendsId, @Param("senderId") Integer senderId);

    /**
     * 查询最新消息
     *
     * @return 实例对象
     */
    PersonalMessage queryLatest();

    /**
     * 通过id组合与索引查询最近历史消息（默认20条）
     *
     * @param friendsId 接收者id
     * @param senderId 发送者id
     * @param time 查询起始的时间
     * @return 实例对象列表
     */
    List<PersonalMessage> queryHistoryById(@Param("friendsId") Integer friendsId,
                                           @Param("senderId") Integer senderId,
                                           @Param("time") Date time);

    /**
     * 通过消息内容进行查询
     *
     * @param friendsId 好友关系id
     * @param keyword 关键词
     * @return 实例对象列表
     */
    List<PersonalMessage> queryByKeyword(Integer friendsId, String keyword);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<PersonalMessage> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param personalMessage 实例对象
     * @return 对象列表
     */
    List<PersonalMessage> queryAll(PersonalMessage personalMessage);

    /**
     * 新增数据
     *
     * @param personalMessage 实例对象
     * @return 影响行数
     */
    int insert(PersonalMessage personalMessage);

    /**
     * 修改数据
     *
     * @param personalMessage 实例对象
     * @return 影响行数
     */
    int update(PersonalMessage personalMessage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<PersonalMessage> queryTimeshaft(@Param("group_id") Integer group_id, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

}