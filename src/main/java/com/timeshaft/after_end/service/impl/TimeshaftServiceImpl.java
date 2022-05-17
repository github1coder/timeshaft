package com.timeshaft.after_end.service.impl;

import com.timeshaft.after_end.entity.Timeshaft;
import com.timeshaft.after_end.mapper.TimeshaftMapper;
import com.timeshaft.after_end.service.TimeshaftService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (Timeshaft)表服务实现类
 *
 * @author makejava
 * @since 2022-04-12 14:24:48
 */
@Service("TimeshaftService")
public class TimeshaftServiceImpl implements TimeshaftService {
    @Resource
    private TimeshaftMapper timeshaftMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Timeshaft queryById(Integer id) {
        return this.timeshaftMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Timeshaft> queryAllByLimit(int offset, int limit) {
        return this.timeshaftMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param timeshaft 实例对象
     * @return 实例对象
     */
    @Override
    public Timeshaft insert(Timeshaft timeshaft) {
        this.timeshaftMapper.insert(timeshaft);
        return timeshaft;
    }

    /**
     * 修改数据
     *
     * @param timeshaft 实例对象
     * @return 实例对象
     */
    @Override
    public Timeshaft update(Timeshaft timeshaft) {
        this.timeshaftMapper.update(timeshaft);
        return this.queryById(timeshaft.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.timeshaftMapper.deleteById(id) > 0;
    }

    @Override
    public List<Timeshaft> queryAll(Timeshaft timeshaft) {
        return this.timeshaftMapper.queryAll(timeshaft);
    }

    @Override
    public List<Timeshaft> queryTimeshaftByTime(Date start_time, Date end_time) {
        return this.timeshaftMapper.queryTimeshaftByTime(start_time, end_time);
    }
}