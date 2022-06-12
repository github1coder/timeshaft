package com.timeshaft.after_end.service.impl;

import com.timeshaft.after_end.entity.RequestInfo;
import com.timeshaft.after_end.mapper.RequestInfoMapper;
import com.timeshaft.after_end.service.RequestInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (RequestInfo)表服务实现类
 *
 * @author makejava
 * @since 2022-06-10 21:00:14
 */
@Service("requestInfoService")
public class RequestInfoServiceImpl implements RequestInfoService {
    @Resource
    private RequestInfoMapper requestInfoMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public RequestInfo queryById(Integer id) {
        return this.requestInfoMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<RequestInfo> queryAllByLimit(int offset, int limit) {
        return this.requestInfoMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param requestInfo 实例对象
     * @return 实例对象
     */
    @Override
    public RequestInfo insert(RequestInfo requestInfo) {
        this.requestInfoMapper.insert(requestInfo);
        return requestInfo;
    }

    /**
     * 修改数据
     *
     * @param requestInfo 实例对象
     * @return 实例对象
     */
    @Override
    public RequestInfo update(RequestInfo requestInfo) {
        this.requestInfoMapper.update(requestInfo);
        return this.queryById(requestInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.requestInfoMapper.deleteById(id) > 0;
    }
}