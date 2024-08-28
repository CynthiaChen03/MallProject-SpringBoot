package com.dmh.web.admin;

import com.dmh.entity.Sales;
import com.dmh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/sale")
public class SaleController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("Sales")
    public List<Sales> Sales(){
        List<Object[]> originalList = orderService.queryTotal();
        List<Sales> countList = new ArrayList<>();
        for (Object[] objects : originalList) {
            Sales sale = new Sales();
            sale.setType((String) objects[0]);
            sale.setMount((Double) objects[1]);
            countList.add(sale);
        }
        if (countList.size()<12){
            return countList;
        }

        List<Sales>arrList=new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            arrList.add(countList.get(i));
        }
        return arrList;
    }

}

