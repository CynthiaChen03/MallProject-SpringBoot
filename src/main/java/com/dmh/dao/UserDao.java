package com.dmh.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dmh.entity.User;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

	User findByUsernameAndPassword(String username, String password);

	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	List<User> findByUsername(String username);
	User findFirstById(int id);

	Page<User> findByNameContains(String name, Pageable pageable);

	User findByEmail(String email);
}
