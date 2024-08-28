package com.dmh.service;

import com.dmh.entity.OrderItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 购物车
 */
public interface ShopCartService {

    String NAME_PREFIX = "shop_cart_";

    /**
     * 加购物车
     * @param
     */
    void addCart(int productId, Integer customizationId,  HttpServletRequest request) throws Exception;
    void minusCart(Integer customizationId, HttpServletRequest request) throws Exception;
    /**
     * 移除

     */
    void remove(int productId, Integer customizationId,HttpServletRequest request) throws Exception;

    /**
     * 查看购物车
     * @param request
     * @return
     */
    List<OrderItem> listCart(HttpServletRequest request) throws Exception;
}
