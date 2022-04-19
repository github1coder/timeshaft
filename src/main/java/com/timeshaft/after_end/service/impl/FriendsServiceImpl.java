package com.timeshaft.after_end.service.impl;

import com.timeshaft.after_end.entity.Friends;
import com.timeshaft.after_end.mapper.FriendsMapper;
import com.timeshaft.after_end.service.FriendsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Friends)表服务实现类
 *
 * @author makejava
 * @since 2022-04-12 14:19:24
 */
@Component("FriendsService")
public class FriendsServiceImpl implements FriendsService {
    @Resource
    private FriendsMapper friendsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Friends queryById(Integer id) {
        return this.friendsDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Friends> queryAllByLimit(int offset, int limit) {
        return this.friendsDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param friends 实例对象
     * @return 实例对象
     */
    @Override
    public Friends insert(Friends friends) {
        this.friendsDao.insert(friends);
        return friends;
    }

    /**
     * 修改数据
     *
     * @param friends 实例对象
     * @return 实例对象
     */
    @Override
    public Friends update(Friends friends) {
        this.friendsDao.update(friends);
        return this.queryById(friends.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.friendsDao.deleteById(id) > 0;
    }

    @Override
    public List<Friends> queryAll(Friends friends) {
        return friendsDao.queryAll(friends);
    }

    @Override
    public int count() {
        return friendsDao.count();
    }
}