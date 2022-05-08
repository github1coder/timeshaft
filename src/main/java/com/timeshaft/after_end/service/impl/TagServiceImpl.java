package com.timeshaft.after_end.service.impl;

import com.timeshaft.after_end.mapper.TagMapper;
import com.timeshaft.after_end.service.TagService;
import org.springframework.stereotype.Service;
import com.timeshaft.after_end.entity.Tag;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-04-12 14:24:48
 */
@Service("TagService")
public class TagServiceImpl implements TagService {
    @Resource
    private TagMapper tagMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Tag queryById(Integer id) {
        return this.tagMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Tag> queryAllByLimit(int offset, int limit) {
        return this.tagMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tag 实例对象
     * @return 实例对象
     */
    @Override
    public Tag insert(Tag tag) {
        this.tagMapper.insert(tag);
        return tag;
    }

    /**
     * 修改数据
     *
     * @param tag 实例对象
     * @return 实例对象
     */
    @Override
    public Tag update(Tag tag) {
        this.tagMapper.update(tag);
        return this.queryById(tag.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tagMapper.deleteById(id) > 0;
    }

    @Override
    public List<Tag> queryAll(Tag tag) {
        return this.tagMapper.queryAll(tag);
    }
}