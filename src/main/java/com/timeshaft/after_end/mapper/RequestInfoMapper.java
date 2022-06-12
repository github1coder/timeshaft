package com.timeshaft.after_end.mapper;

import com.timeshaft.after_end.entity.RequestInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (RequestInfo)表数据库访问层
 *
 * @author makejava
 * @since 2022-06-10 21:00:12
 */
public interface RequestInfoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RequestInfo queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<RequestInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param requestInfo 实例对象
     * @return 对象列表
     */
    List<RequestInfo> queryAll(RequestInfo requestInfo);

    /**
     * 新增数据
     *
     * @param requestInfo 实例对象
     * @return 影响行数
     */
    int insert(RequestInfo requestInfo);

    /**
     * 修改数据
     *
     * @param requestInfo 实例对象
     * @return 影响行数
     */
    int update(RequestInfo requestInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}