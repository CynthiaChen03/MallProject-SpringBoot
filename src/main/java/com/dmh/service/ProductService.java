package com.dmh.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.dmh.entity.Product;

import java.util.List;

public interface ProductService {
	/**
	 * 根据id查询
	 *
	 * @param id
	 * @return
	 */
	Product findById(int id);

	/**
	 * 分页查询所有
	 *
	 * @param pageable
	 * @return
	 */
	Page<Product> findAll(Pageable pageable);
	List<Product> findAll();


	Page<Product> findHotProduct();

	Product findByTitle(String title);
	List<Product> findNewProduct(Pageable pageable);

	/**
	 * 更新
	 *
	 * @param product
	 * @return
	 */
	void update(Product product);

	/**
	 * 创建
	 *
	 * @param product
	 * @return
	 */
	int create(Product product);

	/**
	 * 根据Id删除
	 *
	 * @param id
	 * @return
	 */
	void delById(int id);
	Page<Product> getPage(Integer pageNumber, Integer pageSize, Product food);

	Page<Product> getCPage(Integer pageNum, Integer pageSize, Product product);

    Page<Product> getStockPage(Integer pageNum, Integer pageSize, Product product);
    List<Product> getStockPage(Product product);

    List<Product> findByTitle(Product product);
}
