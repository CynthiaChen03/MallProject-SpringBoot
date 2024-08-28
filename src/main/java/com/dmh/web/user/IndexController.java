package com.dmh.web.user;

import com.dmh.dao.ClaDao;
import com.dmh.entity.Cla;
import com.dmh.entity.Product;
import com.dmh.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Index Controller
 */
@Controller
public class IndexController {
	@Autowired
	private ProductServiceImpl productService;
	@Autowired
	private ClaDao claDao;

	/**
	 * Open Menu
	 * @return
	 */
	@GetMapping("/menu.html")
	public String toIndex(@RequestParam(value="pageNum",defaultValue = "1",required = false) Integer pageNum,
						  @RequestParam(value = "pageSize",defaultValue = "9", required = false) Integer pageSize,
						  @RequestParam(value = "title",defaultValue = "", required = false) String title,
						  @RequestParam(value = "cla",defaultValue = "", required = false) String cla,
						  Model model)
	{
		if (pageNum <= 0) {
		pageNum = 1;
	}
		if (pageSize <= 0) {
			pageSize = 9;
		}
		List<Cla> list = claDao.findAll();
		Product product = new Product();
		product.setTitle(title);
		product.setCla(cla);
		Page<Product> page = productService.getPage(pageNum - 1, pageSize,product);
		if (!cla.equals("")){
			page = productService.getCPage(pageNum - 1, pageSize,product);
		}
		model.addAttribute("pageInfo", page);
		model.addAttribute("food",product);
		model.addAttribute("clas",list);
		return "menu";
	}

	/**
	 * Open Homepage
	 * @return
	 */
	@GetMapping("/homepage.html")
	public String toHomepage(){
		return "homepage";
	}




	@RequestMapping("/")
	public String index(){
		return "forward:/homepage.html";
	}

}
