package com.dmh.web.user;

import com.dmh.dao.ScoreDao;
import com.dmh.entity.Product;
import com.dmh.entity.Score;
import com.dmh.entity.User;
import com.dmh.service.ProductService;
import com.dmh.service.exception.LoginException;
import com.dmh.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    private ScoreDao scoreDao;
    @Autowired
    private ProductService productService;
    @RequestMapping("/add.html")
    public String toList(Model model,int pid){
        Product product = new Product();
        product = productService.findById(pid);
        model.addAttribute("product",product);
        return "mall/score/add";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/add.do")
    public String add(String content,
                    Integer product_id,
                    Double score,
                    HttpServletRequest request){
        Object user = request.getSession().getAttribute("user");
        if (user == null)
            throw new LoginException("请登录！");
        User loginUser = (User) user;
        Score score1 = new Score();
        score1.setContent(content);
        score1.setProductId(product_id);
        score1.setScore(score);
        score1.setTime(new Date());
        score1.setUserId(loginUser.getId());
        scoreDao.save(score1);
        return "redirect:/order/toList.html";
    }

}
