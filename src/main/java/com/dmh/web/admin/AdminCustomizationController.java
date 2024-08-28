package com.dmh.web.admin;
import com.dmh.dao.CustomizationDao;
import com.dmh.entity.Cla;
import com.dmh.entity.Customization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dmh.entity.OrderItem;
import com.dmh.entity.Product;

import com.dmh.service.ProductService;
import com.dmh.service.ShopCartService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Controller
@RequestMapping("/admin/customization")
public class AdminCustomizationController {
    @Autowired
    private CustomizationDao customizationDao;
    @Autowired
    private ProductService productService;

    @RequestMapping("/toAdd.html")
    public String toAdd(int pid,Model model) {
       Product product = new Product();
       product = productService.findById(pid);
       model.addAttribute("product",product);
       return "admin/customization/add";
    }

    @RequestMapping("/toEdit.html")
    public String toEdit(int id, Model model) {


        Customization customization = new Customization();
        customization = customizationDao.findById(id);
        model.addAttribute("customization",customization);
        return "admin/customization/edit";
    }

    @RequestMapping("/add.do")
    public String add(int pid, String name) {
        Customization customization = new Customization();
        customization.setProductId(pid);
        customization.setName(name);
        customizationDao.save(customization);
        return "redirect:/admin/product/toEdit.html?id=" + pid;
    }

    @RequestMapping("/edit.do")
    public String edit(int id, int pid, String name,Model model) {
        Customization customization = new Customization();
        customization.setId(id);
        customization.setProductId(pid);
        customization.setName(name);
        customizationDao.save(customization);
        return "redirect:/admin/product/toEdit.html?id=" + pid;
    }

    @RequestMapping("/del.do")
    public String delete(int id, int productId) {
        customizationDao.deleteById(id);
        return "redirect:/admin/product/toEdit.html?id=" + productId;
    }

}
