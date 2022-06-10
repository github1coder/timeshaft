package com.timeshaft.after_end.service;

import com.timeshaft.after_end.entity.RequestInfo;
import java.util.List;

/**
 * (RequestInfo)表服务接口
 *
 * @author makejava
 * @since 2022-06-10 21:00:14
 */
public interface RequestInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RequestInfo queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<RequestInfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param requestInfo 实例对象
     * @return 实例对象
     */
    RequestInfo insert(RequestInfo requestInfo);

    /**
     * 修改数据
     *
     * @param requestInfo 实例对象
     * @return 实例对象
     */
    RequestInfo update(RequestInfo requestInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}