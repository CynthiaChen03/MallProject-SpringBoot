package com.dmh.web.admin;

import com.dmh.dao.ClaDao;
import com.dmh.dao.CustomizationDao;
import com.dmh.entity.Cla;
import com.dmh.entity.Customization;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.dmh.entity.Product;
import com.dmh.service.ProductService;
import com.dmh.utils.FileUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ClaDao claDao;
    @Autowired
    private CustomizationDao customizationDao;
    @RequestMapping("/toList.html")
    public String toList(@RequestParam(value = "title",defaultValue = "", required = false) String title,
                          Model model)
    {

        Product product = new Product();
        product.setTitle(title);

        List<Product> foodList= productService.findByTitle(product);
//        List<Product> foodList1= productService.findAll();
//        model.addAttribute("pageInfo", page);
        model.addAttribute("food",foodList);
        List <Cla> clas = claDao.findAll();
        model.addAttribute("class",clas);
        return "admin/product/list";
    }

    @RequestMapping("/toClass.html")
    public String toClass(Model model) {
        List <Cla> clas = claDao.findAll();
        model.addAttribute("class",clas);
        return "admin/product/class";
    }

    @RequestMapping("/deleteClass/{id}")
    public String deleteClass(@PathVariable Integer id){
        claDao.deleteById(id);
        return "redirect:/admin/product/toClass.html";
    }
    @RequestMapping("/toEditClass/{id}")
    public String toEditClass(@PathVariable int id, Model model) {
        Cla cla = claDao.getOne(id);
        model.addAttribute("cla", cla);
        return "admin/product/class-edit";
    }

    @RequestMapping("/EditClass")
    public String EditClass(int id, String clas) {
        Cla cla = new Cla();
        cla.setCla(clas);
        cla.setId(id);
        claDao.save(cla);
        return "redirect:/admin/product/toClass.html";
    }
    @RequestMapping("/toAddClass")
    public String addClass() {
        return "admin/product/add-class";
    }

    @RequestMapping("/addClass.html")
    public String addClass(String name) {
        Cla cla = new Cla();
        cla.setCla(name);
        claDao.save(cla);
        return "redirect:/admin/product/toClass.html";
    }

    @RequestMapping("/toEdit.html")
    public String toEdit(int id, Model model) {
        Product product = productService.findById(id);
        List<Cla> store = claDao.findAll();
        model.addAttribute("storeList", store);
        model.addAttribute("product", product);
        List<Customization> customizations = customizationDao.findAllByProductId(id);
        model.addAttribute("customizations",customizations);
        return "admin/product/edit";
    }



    @RequestMapping("/del.do")
    public String del(int id) {
        productService.delById(id);
        return "redirect:/admin/product/toList.html";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add.do")
    public String add(String title,
//                    Double marketPrice,
                    Double shopPrice,
                    String desc,
                    String cla,
                      MultipartFile image) throws Exception {
        Product product = new Product();
        product.setTitle(title);
        product.setMarketPrice(0.0);
        product.setShopPrice(shopPrice);
        product.setDesc(desc);
        product.setCla(cla);
        product.setPdate(new Date());
        String imgUrl = FileUtil.saveFile(image);
        System.out.println(imgUrl);
        product.setImage(imgUrl);
        productService.create(product);
        int id = productService.findByTitle(title).getId();
        return "redirect:/admin/product/toEdit.html?id="+id;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/update.do")
    public void update(int id,
                       String title,
                       Double marketPrice,
                       Double shopPrice,
                       String desc,
                       String cla,
                       MultipartFile image,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        Product product = productService.findById(id);
        product.setTitle(title);
        product.setMarketPrice(marketPrice);
        product.setShopPrice(shopPrice);
        product.setDesc(desc);
        product.setCla(cla);
        product.setPdate(new Date());
        String imgUrl = FileUtil.saveFile(image);
        if (StringUtils.isNotBlank(imgUrl)) {
            product.setImage(imgUrl);
        }
        boolean flag = false;
        try {
            productService.update(product);
            flag = true;
        } catch (Exception e) {
            throw new Exception(e);
        }

        response.sendRedirect("toList.html");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/img/{filename:.+}")
    public void getImage(@PathVariable(name = "filename", required = true) String filename,
                         HttpServletResponse res) throws IOException {
        File file = new File("file/" + filename);
        if (file != null && file.exists()) {
            res.setHeader("content-type", "application/octet-stream");
            res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            res.setContentLengthLong(file.length());
            Files.copy(Paths.get(file.toURI()), res.getOutputStream());
        }
    }

    @RequestMapping("onSale")
    public String onSale(int id){
        Product product = productService.findById(id);
        product.setIsHot(1);
        productService.update(product);
        return "redirect:/admin/product/toList.html";
    }

    @RequestMapping("offSale")
    public String offSale(int id){
        Product product = productService.findById(id);
        product.setIsHot(0);
        productService.update(product);
        return "redirect:/admin/product/toList.html";
    }

    @RequestMapping("listStock")
    public String toStock(@RequestParam(value="pageNum",defaultValue = "1",required = false) Integer pageNum,
                          @RequestParam(value = "pageSize",defaultValue = "6", required = false) Integer pageSize,
                          @RequestParam(value = "title",defaultValue = "", required = false) String title,
                          Model model){
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize <= 0) {
            pageSize = 6;
        }
        Product product = new Product();
        product.setTitle(title);
        Page<Product> page = productService.getStockPage(pageNum - 1, pageSize,product);
        List<Product> productList = productService.getStockPage(product);
        model.addAttribute("pageInfo",page);
        model.addAttribute("product",productList);
        return "admin/product/stock-list";
    }

    @RequestMapping("addStock/{id}")
    public String addStock(@PathVariable Integer id){
        Product byId = productService.findById(id);
        byId.setStock(byId.getStock()+100);
        productService.update(byId);
        return "redirect:/admin/product/listStock";
    }

    @RequestMapping("toSales")
    public String toSales() {
        return "admin/sale";
    }




}
