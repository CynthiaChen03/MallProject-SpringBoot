package com.dmh.web.admin;

import com.dmh.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.dmh.entity.User;
import com.dmh.service.UserService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private UserService userService;

    /**
     * 打开用户列表页面
     * @return
     */
    @RequestMapping("/toList.html")
    public String toList(@RequestParam(value="pageNum",defaultValue = "1",required = false) Integer pageNum,
                         @RequestParam(value = "pageSize",defaultValue = "6", required = false) Integer pageSize,
                         @RequestParam(value = "name",defaultValue = "", required = false) String name,
                         Model model)
    {
        User user = new User();
        user.setName(name);
        Page<User> page = userService.getPage(pageNum - 1, pageSize,user);
        model.addAttribute("pageInfo", page);
        return "admin/user/list";
    }



    @RequestMapping("/del.do")
    public String del(int id) {
        userService.delById(id);
        return "redirect://admin/user/toList.html";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update.do")
    public String update(int id,String username,
                                      String password,String name,
                                      String phone,String email,
                                      String addr) {
        // 更新前先查询
        User user = userService.findById(id);
        user.setId(id);
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setAddr(addr);
        user.setEmail(email);
        user.setPhone(phone);
        userService.update(user);
        return "redirect://admin/user/toList.html";
    }
}
