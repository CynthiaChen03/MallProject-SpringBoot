package com.dmh.service.impl;

import com.dmh.dao.CustomizationDao;
import com.dmh.entity.Customization;
import com.dmh.entity.OrderItem;
import com.dmh.entity.Product;
import com.dmh.entity.User;
import com.dmh.service.ProductService;
import com.dmh.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ShopCartServiceImpl implements ShopCartService {

	@Autowired
	private ProductService productService;
@Autowired private CustomizationDao customizationDao;
	/**
	 * 加购物车
	 * 将商品id保存到Session中List<Integer>中
	 *
	 * @param productId
	 * @param request
	 */
	@Override
	public void addCart(int productId, Integer customizationId, HttpServletRequest request) throws Exception {
		User loginUser = (User) request.getSession().getAttribute("user");
		if (loginUser == null)
			throw new Exception("未登录！请重新登录");
		List<Integer> customizationIds = (List<Integer>) request.getSession().getAttribute(NAME_PREFIX +"c_"+ loginUser.getId());
		if (customizationIds == null) {
			customizationIds = new ArrayList<>();
			request.getSession().setAttribute(NAME_PREFIX + "c_"+loginUser.getId(), customizationIds);
		}
		customizationIds.add(customizationId);
	}

	@Override
	public void minusCart(Integer customizationId, HttpServletRequest request) throws Exception {
		User loginUser = (User) request.getSession().getAttribute("user");
		if (loginUser == null)
			throw new Exception("未登录！请重新登录");
		List<Integer> customizationIds = (List<Integer>) request.getSession().getAttribute(NAME_PREFIX + "c_" + loginUser.getId());
		if (customizationIds == null) {
			throw new Exception("购物车中没有该商品！");
		}
		if (customizationIds.contains(customizationId)) {
			customizationIds.remove(customizationId);
		} else {
			throw new Exception("购物车中没有该商品！");
		}
	}


	/**
	 * 移除
	 *
	 * 移除session List中对应的商品Id
	 *
	 * @param productId
	 * @param request
	 */
	@Override
	public void remove(int productId, Integer customizationId, HttpServletRequest request) throws Exception {
		User loginUser = (User) request.getSession().getAttribute("user");
		if (loginUser == null)
			throw new Exception("未登录！请重新登录");
		List<Integer> customizationIds = (List<Integer>) request.getSession().getAttribute(NAME_PREFIX +"c_"+ loginUser.getId());
		Iterator<Integer> iterator2 = customizationIds.iterator();
		while (iterator2.hasNext()) {
			if (Objects.equals(customizationId, iterator2.next())) {
				iterator2.remove();
			}
		}
	}

	/**
	 * 查看购物车
	 *
	 * 查询出session的List中所有的Id,并封装成List<OrderItem>返回
	 *
	 * @param request
	 * @return
	 */
	@Override
	public List<OrderItem> listCart(HttpServletRequest request) throws Exception {
		User loginUser = (User) request.getSession().getAttribute("user");
		if (loginUser == null)
			throw new Exception("未登录！请重新登录");
		List<Integer> customizationIds = (List<Integer>) request.getSession().getAttribute(NAME_PREFIX +"c_"+ loginUser.getId());
		System.out.println(customizationIds);
		Map<Integer, OrderItem> productMap = new HashMap<>();
		if (customizationIds == null){
			return new ArrayList<>();
		}
		for (int customizationId : customizationIds) {
			if (productMap.get(customizationId) == null) {
			    Customization customization = customizationDao.findById(customizationId);
				Product product = productService.findById(customization.getProductId());
				OrderItem orderItem = new OrderItem();
				orderItem.setProduct(product);
				orderItem.setProductId(customization.getProductId());
				orderItem.setCount(1);
				orderItem.setSubTotal(product.getShopPrice());
				orderItem.setCustomizationId(customizationId);
				orderItem.setCustomization(customizationDao.findById(customizationId));
				productMap.put(customizationId, orderItem);
			} else {
				Customization customization = customizationDao.findById(customizationId);
				Product product = productService.findById(customization.getProductId());
				OrderItem orderItem = productMap.get(customizationId);
				int count = orderItem.getCount();
				orderItem.setCount(++count);
				Double subTotal = orderItem.getSubTotal();
				orderItem.setSubTotal(product.getShopPrice()+subTotal);
				orderItem.setCustomizationId(customizationId);
				orderItem.setCustomization(customizationDao.findById(customizationId));
				productMap.put(customizationId, orderItem);
			}
		}
		List<OrderItem> orderItems = new ArrayList<>(productMap.values());
		System.out.println(orderItems);
		return orderItems;
	}
}
