package com.dmh.web.user;
import com.dmh.dao.ClaDao;
import com.dmh.dao.CustomizationDao;
import com.dmh.dao.ScoreDao;
import com.dmh.entity.Customization;
import com.dmh.entity.OrderItem;
import com.dmh.entity.Product;
import com.dmh.entity.Score;
import com.dmh.service.ProductService;
import com.dmh.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ScoreDao scoreDao;
	@Autowired
	private ShopCartService shopCartService;

	@Autowired
	private ClaDao claDao;
	@Autowired
	CustomizationDao customizationDao;







	@RequestMapping("/toCartView.html")
	public String toCartView(HttpServletRequest request,Model model) throws Exception {
		List<OrderItem> orderItems = shopCartService.listCart(request);
		model.addAttribute("orderItems",orderItems);
		return "mall/product/cart_view";
	}





	/**
	 * 加购物车
	 *
	 * @param productId
	 * @param request
	 * @return
	 */

	@RequestMapping("/addCart.do")
	public String addToCart(int productId, Integer customizationId, HttpServletRequest request) throws Exception {
		shopCartService.addCart(productId, customizationId,request);
		return "redirect:/product/get.html?id="+productId;
	}
	@RequestMapping("/minusCart.do")
	public String minusToCart(Integer customizationId, HttpServletRequest request) throws Exception {
		shopCartService.minusCart(customizationId,request);
		return "redirect:/product/toCartView.html";
	}
	@RequestMapping("/plusCart.do")
	public String plusToCart(Integer customizationId, HttpServletRequest request) throws Exception {
		shopCartService.addCart(0,customizationId,request);
		return "redirect:/product/toCartView.html";
	}


	@RequestMapping("/delCart.do")
	public String delToCart(int productId, Integer customizationId,HttpServletRequest request) throws Exception {
		shopCartService.remove(productId, customizationId, request);
		return "redirect:/product/toCartView.html";
	}

	@RequestMapping("/toPopUpCart.html")
	public String toPopUpCart(HttpServletRequest request,Model model) throws Exception {
		List<OrderItem> orderItems = shopCartService.listCart(request);
		model.addAttribute("orderItems",orderItems);
		return "mall/product/pop_up_cart";
	}

	//新增商品详情页面
	@RequestMapping("/menu_detail.html")
	public String toDetail(int id,Model model) {
		Product product = productService.findById(id);
		List<Score> scores = scoreDao.findAllByProductId(id);
		List<Customization> customizations = customizationDao.findAllByProductId(id);
		model.addAttribute("product", product);
		model.addAttribute("scores",scores);
		model.addAttribute("customizations",customizations);
		return "menu_details";
	}

}
