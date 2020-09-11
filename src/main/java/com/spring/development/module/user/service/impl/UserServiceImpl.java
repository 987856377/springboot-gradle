package com.spring.development.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.spring.development.module.user.entity.User;
import com.spring.development.module.user.mapper.UserMapper;
import com.spring.development.module.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Future;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XuZhenkui
 * @since 2020-09-11
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Async
    @Override
    public Future<Integer> insert(User user) {
        return new AsyncResult<>(userMapper.insert(user));
    }

    @Async
    @Override
    public Future<Integer> removeById(Long id) {
        return new AsyncResult<>(userMapper.deleteById(id));
    }

    @Override
    public Future<List<User>> getAllUser() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.isNotNull("id");
        return new AsyncResult<>(userMapper.selectList(userQueryWrapper));
    }
}