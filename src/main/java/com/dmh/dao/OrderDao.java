package com.dmh.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dmh.entity.Order;

public interface OrderDao extends JpaRepository<Order, Integer> {

	/**
	 * 更改订单状态
	 * @param state
	 * @param id
	 */
	@Modifying
	@Transactional
	@Query(value = "update `order` o set o.state=?1 where o.id=?2",nativeQuery = true)
	void updateState(int state,int id);

	/**
	 * 查找用户的订单
	 * @param userId
	 * @return
	 */
	List<Order> findByUserId(int userId);
	Order findFirstById(Integer id);

	Page <Order> findByOrderTime(Date date, Pageable pageable);
    Page <Order> findByNameContains(String name, Pageable pageable);

	Page <Order> findAll(Pageable pageable);

    List<Order> findByorderTimeBetween(Date orderTime, Date orderTime2);

	@Query("SELECT f.title as name, SUM(o.subTotal) as total " +
			"FROM Product f, OrderItem o " +
			"WHERE o.productId = f.id " +
			"GROUP BY f.title " +
			"ORDER BY total DESC")
	List<Object[]> queryTotal();
}
