package com.timeshaft.after_end.service.impl;

import com.timeshaft.after_end.entity.GroupMessage;
import com.timeshaft.after_end.entity.PersonalMessage;
import com.timeshaft.after_end.entity.Timeshaft;
import com.timeshaft.after_end.mapper.PersonalMessageMapper;
import com.timeshaft.after_end.service.PersonalMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (PersonalMessage)表服务实现类
 *
 * @author makejava
 * @since 2022-04-12 14:24:48
 */
@Service("PersonalMessageService")
public class PersonalMessageServiceImpl implements PersonalMessageService {
    @Resource
    private PersonalMessageMapper personalMessageMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PersonalMessage queryById(Integer id) {
        return this.personalMessageMapper.queryById(id);
    }

    /**
     * 通过id组合查询最新历史消息
     *
     * @param friendsId 接收者id
     * @param senderId 发送者id
     * @return 实例对象
     */
    @Override
    public PersonalMessage queryLatestById(Integer friendsId, Integer senderId) {
        return this.personalMessageMapper.queryLatestById(friendsId, senderId);
    }

    /**
     * 查询最新历史消息
     *
     * @return 实例对象
     */
    @Override
    public PersonalMessage queryLatest() {
        return this.personalMessageMapper.queryLatest();
    }

    /**
     * 通过id组合与索引查询最近历史消息（默认20条）
     *
     * @param friendsId 好友关系id
     * @param senderId 发送者id
     * @param time 查询起始的时间
     * @return 实例对象列表
     */
    @Override
    public List<PersonalMessage> queryHistoryById(Integer friendsId, Integer senderId, Date time) {
        return this.personalMessageMapper.queryHistoryById(friendsId, senderId, time);
    }

    /**
     * 通过消息内容进行查询
     *
     * @param friendsId 好友关系id
     * @param keyword 关键词
     * @return 实例对象列表
     */
    @Override
    public List<PersonalMessage> queryByKeyword(Integer friendsId, String keyword) {
        return this.personalMessageMapper.queryByKeyword(friendsId, keyword);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<PersonalMessage> queryAllByLimit(int offset, int limit) {
        return this.personalMessageMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param personalMessage 实例对象
     * @return 实例对象
     */
    @Override
    public PersonalMessage insert(PersonalMessage personalMessage) {
        this.personalMessageMapper.insert(personalMessage);
        return personalMessage;
    }

    /**
     * 修改数据
     *
     * @param personalMessage 实例对象
     * @return 实例对象
     */
    @Override
    public PersonalMessage update(PersonalMessage personalMessage) {
        this.personalMessageMapper.update(personalMessage);
        return this.queryById(personalMessage.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.personalMessageMapper.deleteById(id) > 0;
    }

    @Override
    public List<PersonalMessage> queryTimeshaft(Timeshaft timeshaft) {
        return this.personalMessageMapper.queryTimeshaft(timeshaft.getGroupId(), timeshaft.getBeginTime(), timeshaft.getEndTime());
    }

    @Override
    public List<PersonalMessage> queryAll(PersonalMessage personalMessage) {
        return this.personalMessageMapper.queryAll(personalMessage);
    }

    @Override
    public List<PersonalMessage> queryBeginEndMsg(int group_id, int startId, int endId) {
        return  this.personalMessageMapper.queryBeginEndMsg(group_id, startId, endId);
    }
}