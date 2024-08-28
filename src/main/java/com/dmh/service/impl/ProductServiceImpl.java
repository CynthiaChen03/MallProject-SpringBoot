package com.dmh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dmh.dao.ProductDao;
import com.dmh.entity.Product;
import com.dmh.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;


	@Override
	public Product findById(int id) {
		return productDao.getOne(id);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productDao.findAll(pageable);
	}

	@Override
	public List<Product> findAll() {
		return productDao.findAll();
	}

	/**
	 * 查找特价公告商品
	 *
	 * @return
	 */
	 @Override
	 public Page<Product> findHotProduct() {
		 return productDao.findByIsHot(1, null);
	 }

	 /**
	  * 查找最新商品
	  *
	  * @param pageable
	  * @return
	  */
	 @Override
	 public List<Product> findNewProduct(Pageable pageable) {
		 // 查找两周内上架的商品
		 //        Calendar calendar = Calendar.getInstance();
		 //        calendar.add(Calendar.DAY_OF_MONTH, -14);
		 return productDao.findNew(pageable);
	 }



	 @Override
	 public void update(Product product) {
		 productDao.save(product);
	 }

	 @Override
	 public int create(Product product) {
		 return productDao.save(product).getId();
	 }

	 @Override
	 public void delById(int id) {
		 productDao.deleteById(id);
	 }
@Override
	public Page<Product> getPage(Integer pageNumber, Integer pageSize, Product food) {
		Pageable pageRequest = PageRequest.of(pageNumber, pageSize);

	if (food == null || food.getTitle() == null) {
		return productDao.findAll(pageRequest);
	} else {


		return productDao.findByTitleContaining(food.getTitle(), pageRequest);
	}
	}
	public Page<Product> getCPage(Integer pageNumber, Integer pageSize, Product food) {
		Pageable pageRequest = PageRequest.of(pageNumber, pageSize);

		if (food == null || food.getCla() == null) {
			return productDao.findAll(pageRequest);
		} else {


			return productDao.findByCla(food.getCla(), pageRequest);
		}
	}

	@Override
	public Page<Product> getStockPage(Integer pageNumber, Integer pageSize, Product food) {
		Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
		return productDao.findByIsHot(1, pageRequest);
	}

	@Override
	public List<Product> getStockPage(Product product) {
		return productDao.findByIsHot(1);
	}

	@Override
	public List<Product> findByTitle(Product product) {
		return productDao.findByTitleContaining(product.getTitle());
	}

	@Override
	public Product findByTitle(String title){
		 return productDao.findByTitle(title);
	};
}
