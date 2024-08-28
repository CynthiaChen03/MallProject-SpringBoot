package com.dmh.web.user;

import com.dmh.service.CustomizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dmh.entity.Order;
import com.dmh.entity.OrderItem;

import com.dmh.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomizationService customizationService;
    /**
     * 打开订单列表页面
     *
     * @return
     */
    @RequestMapping("/toList.html")
    public String toOrderList(Model model,HttpServletRequest request) {
        List<Order> orders = orderService.findUserOrder(request);
        model.addAttribute("orders",orders);
        return "mall/order/list";
    }
    @RequestMapping("/toDetail.html")
    public String toDetailList(Model model,int orderId) {
        List<OrderItem> orderItems = orderService.findItems(orderId);
        for(OrderItem item:orderItems){
            item.setCustomization(customizationService.findByid(item.getCustomizationId()));
        }
        model.addAttribute("orderItems",orderItems);
        return "mall/order/detail";
    }

    @RequestMapping("/toScore.html")
    public String toScore(Model model,int orderId) {
        List<OrderItem> orderItems = orderService.findItems(orderId);
        model.addAttribute("orderItems",orderItems);
        return "mall/score/list";
    }

    /**
     * 提交订单
     *
     * @param recipient
     * @param phone
     * @param address
     * @param request
     * @param response
     */
    @RequestMapping("/submit.do")
    public void submit(String recipient,
                       String phone,
                       String address,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception {

        orderService.submit(recipient, phone, address, request, response);
    }

    /**
     * 支付方法
     *
     * @param orderId
     */
    @RequestMapping("pay.do")

    public String pay(int orderId) throws IOException {
        orderService.pay(orderId);
        return "redirect:/order/toList.html";
    }


    @RequestMapping("receive.do")

    public String receive(int orderId) throws IOException {
        orderService.receive(orderId);
        return "redirect:/order/toList.html";
    }


}
