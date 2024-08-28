package com.dmh.web.admin;

import com.dmh.entity.*;
import com.dmh.service.CustomizationService;
import com.dmh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dmh.service.OrderService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomizationService customizationService;
    /**
     * 打开订单列表页面
     * @return
     */
    @RequestMapping("/toList.html")
    public String toList(@RequestParam(value="pageNum",defaultValue = "1",required = false) Integer pageNum,
                         @RequestParam(value = "pageSize",defaultValue = "5", required = false) Integer pageSize,
                         Model model)
    {
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }

        Page<Order> page = orderService.getPage(pageNum - 1, pageSize);
        List<Order> orders1 = orderService.findAll();
        model.addAttribute("pageInfo", page);
        model.addAttribute("order", orders1);
        return "admin/order/list";
    }



    @RequestMapping("/send.do")
    public String send(int id) {
        int status = orderService.findById(id).getState();
        if (status == 2) orderService.updateStatus(id,3);
        return "redirect:/admin/order/toList.html";
    }

    @RequestMapping("searchOrders")
    public String searchOrders(
            @RequestParam(value="pageNum",defaultValue = "1",required = false) Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "6", required = false) Integer pageSize,
            @RequestParam(value="startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")Date startDate,
            @RequestParam(value="endDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")Date endDate,
            Model model){
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize <= 0) {
            pageSize = 6;
        }
        if(endDate == null && startDate == null){
            return "redirect:/admin/order/toList.html";
        }

        List<Order> orders1 = orderService.findByOrderDateBetween(startDate, endDate);
        model.addAttribute("order", orders1);
        return "admin/order/list";
    }

    @RequestMapping("/toDetail.html")
    public String toDetailList(Model model,int orderId) {
        List<OrderItem> orderItems = orderService.findItems(orderId);
        User user = userService.findById(orderService.findById(orderId).getUserId());
//        Customization customization = customizationService.findByid();
        for(OrderItem item:orderItems){
            item.setCustomization(customizationService.findByid(item.getCustomizationId()));
        }
        model.addAttribute("orderItems",orderItems);
        model.addAttribute("User", user);
        return "admin/order/detail";
    }
}
