package com.timeshaft.after_end.mapper;

import com.timeshaft.after_end.entity.Timeshaft;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * (Timeshaft)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-12 14:24:48
 */
public interface TimeshaftMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Timeshaft queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Timeshaft> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param timeshaft 实例对象
     * @return 对象列表
     */
    List<Timeshaft> queryAll(Timeshaft timeshaft);

    /**
     * 新增数据
     *
     * @param timeshaft 实例对象
     * @return 影响行数
     */
    int insert(Timeshaft timeshaft);

    /**
     * 修改数据
     *
     * @param timeshaft 实例对象
     * @return 影响行数
     */
    int update(Timeshaft timeshaft);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);


    /**
     * 通过时间查询数据
     *
     * @param start_time 开始时间
     * @param end_time 结束时间
     * @return 对象列表
     */
    List<Timeshaft> queryTimeshaftByTime(Date start_time, Date end_time);
}