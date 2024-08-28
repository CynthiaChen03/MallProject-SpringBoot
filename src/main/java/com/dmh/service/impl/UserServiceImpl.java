package com.dmh.service.impl;

import com.dmh.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dmh.dao.UserDao;
import com.dmh.entity.User;
import com.dmh.service.UserService;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findById(int id) {
        return userDao.getOne(id);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userDao.findAll(pageable);
    }

    @Override
    public List<User> findAllExample(Example<User> example) {
        return userDao.findAll(example);
    }

    @Override
    public void update(User user) {
        userDao.save(user);
    }

    @Override
    public int create(User user) {
        return userDao.save(user).getId();
    }

    @Override
    public void delById(int id) {
        userDao.deleteById(id);
    }

    /**
     * 根据用户名查询
     *
     * @param username
     * @return
     */
    @Override
    public List<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * 检查登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public User checkLogin(String username, String password) {
        return userDao.findByUsernameAndPassword(username, DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public Page<User> getPage(Integer pageNumber, Integer pageSize, User user) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);

        if (user == null || user.getName() == null) {

            return userDao.findAll(pageRequest);

        } else {

            return userDao.findByNameContains(user.getName(), pageRequest);
        }
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
