package com.dmh.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dmh.entity.Product;

public interface ProductDao extends JpaRepository<Product, Integer> {


	/**
	 * 通过标题搜索商品
	 *
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	List<Product> findByTitleIsLike(String keyword, Pageable pageable);

	/**
	 * 查找某个日期之后上架的商品
	 * @param date
	 * @param pageable
	 * @return
	 */
	List<Product> findByPdateAfter(Date date, Pageable pageable);

	/**
	 * 查找特价公告商品
	 *
	 * @param isHot
	 * @param pageable
	 * @return
	 */
	Page<Product> findByIsHot(int isHot, Pageable pageable);
	List<Product> findByIsHot(int isHot);

	/**
	 * 查询最新商品，最近上新的24个商品
	 * @param pageable
	 * @return
	 */
	@Query(value = "SELECT * FROM (SELECT  * FROM product ORDER BY pdate DESC limit 0,24) a /*#pageable*/",nativeQuery = true)
	List<Product> findNew(Pageable pageable);
	Product findFirstById(int id);
	Page<Product> findAll(Pageable pageable);
	List<Product> findAll();

	Product findByTitle(String title);
	Page<Product> findByTitleContaining(String title, Pageable pageRequest);

	Page<Product> findByCla(String cla, Pageable pageable);


	List<Product> findByTitleContaining(String title);
}
