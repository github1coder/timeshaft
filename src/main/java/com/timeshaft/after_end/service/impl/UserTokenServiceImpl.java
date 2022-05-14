package com.timeshaft.after_end.service.impl;

import com.timeshaft.after_end.entity.UserToken;
import com.timeshaft.after_end.mapper.UserTokenMapper;
import com.timeshaft.after_end.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * (UserToken)表服务实现类
 *
 * @author makejava
 * @since 2022-05-08 20:32:05
 */
@Service("userTokenService")
public class UserTokenServiceImpl implements UserTokenService {
    @Resource
    private UserTokenMapper userTokenDao;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserToken queryById(Integer id) {
        return this.userTokenDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<UserToken> queryAllByLimit(int offset, int limit) {
        return this.userTokenDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param userToken 实例对象
     * @return 实例对象
     */
    @Override
    public UserToken insert(UserToken userToken) {
        this.userTokenDao.insert(userToken);
        return userToken;
    }

    /**
     * 修改数据
     *
     * @param userToken 实例对象
     * @return 实例对象
     */
    @Override
    public UserToken update(UserToken userToken) {
        this.userTokenDao.update(userToken);
        return this.queryById(userToken.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userTokenDao.deleteById(id) > 0;
    }

    @Override
    public List<UserToken> queryAll(UserToken userToken) {
        return this.userTokenDao.queryAll(userToken);
    }

    @Override
    public boolean isLogin(Integer user_id, String token) {
        boolean flag = false;
        boolean redisFlag = false;
        String userId = String.valueOf(user_id);
        String key = "userToken";
        if (redisTemplate.hasKey(key)) {
            if (redisTemplate.opsForHash().hasKey(key, userId)) {
                ArrayList<String> tokens = (ArrayList<String>) redisTemplate.opsForHash().get(key, userId);
                for (String tokenT : tokens) {
                    if (tokenT.equals(token)) {
                        redisFlag = true;
                        break;
                    }
                }
            }
            else {
                ArrayList<String> tokens = new ArrayList<>();
                redisTemplate.opsForHash().put(key, userId, tokens);
            }
        }
        else {
            ArrayList<String> tokens = new ArrayList<>();
            redisTemplate.opsForHash().put(key, userId, tokens);
        }

        if (!redisFlag) {
            List<UserToken> userTokens = this.userTokenDao.queryAll(new UserToken(user_id, token));
            if (userTokens.size() > 0) {
                ArrayList<String> tokens = (ArrayList<String>) redisTemplate.opsForHash().get(key, userId);
                tokens.add(token);
                redisTemplate.opsForHash().put(key, userId, tokens);
                flag = true;
            }
        }
        return redisFlag || flag;
    }
}