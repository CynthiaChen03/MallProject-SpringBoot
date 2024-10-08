package com.dmh.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dmh.entity.User;

import java.util.List;

public interface UserService {
    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    User findById(int id);

    /**
     * 分页查询所有
     *
     * @param pageable
     * @return
     */
    Page<User> findAll(Pageable pageable);

    /**
     * 按条件查询
     *
     * @param example
     * @return
     */
    List<User> findAllExample(Example<User> example);

    /**
     * 更新
     *
     * @param user
     * @return
     */
    void update(User user);

    /**
     * 创建
     *
     * @param user
     * @return
     */
    int create(User user);

    /**
     * 根据Id删除
     *
     * @param id
     * @return
     */
    void delById(int id);

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    List<User> findByUsername(String username);

    /**
     * 检查登录
     * @param username
     * @param password
     * @return
     */
    User checkLogin(String username,String password);
    Page<User> getPage(Integer pageNumber, Integer pageSize, User user);

    /**
     * 查看邮箱是否重复
     */
    User findByEmail(String email);
}
