package com.timeshaft.after_end.mapper;

import com.timeshaft.after_end.entity.GroupHeat;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (GroupHeat)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-11 20:05:41
 */
public interface GroupHeatMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GroupHeat queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<GroupHeat> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param groupHeat 实例对象
     * @return 对象列表
     */
    List<GroupHeat> queryAll(GroupHeat groupHeat);

    /**
     * 新增数据
     *
     * @param groupHeat 实例对象
     * @return 影响行数
     */
    int insert(GroupHeat groupHeat);

    /**
     * 修改数据
     *
     * @param groupHeat 实例对象
     * @return 影响行数
     */
    int update(GroupHeat groupHeat);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}