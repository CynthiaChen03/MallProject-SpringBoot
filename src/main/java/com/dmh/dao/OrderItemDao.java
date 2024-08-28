package com.dmh.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dmh.entity.OrderItem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemDao extends JpaRepository<OrderItem, Integer> {
	/**
	 * 根据订单Id查询
	 * @param orderId
	 * @return
	 */
	List<OrderItem> findByOrderId(int orderId);
	OrderItem findFirstById(int id);

	List<OrderItem> findByProductId(int productId);
}
