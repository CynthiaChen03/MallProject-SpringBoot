package com.dmh.web.user;

import com.dmh.utils.SendEmailUtil;
import com.dmh.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dmh.entity.User;

import com.dmh.service.UserService;
import com.dmh.service.exception.LoginException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${spring.mail.username}")
    private String sourceEmail;

    @Value("${spring.mail.password}")
    private String authorizationCode;

    /**
     * 打开注册页面
     *
     * @return
     */
    @RequestMapping("/toRegister.html")
    public String toRegister() {
        return "mall/user/register";
    }

    /**
     * 打开登录页面
     *
     * @return
     */
    @RequestMapping("/toLogin.html")
    public String toLogin() {
        return "mall/user/login";
    }


    /**
     * 打开忘记密码页面
     *
     * @return
     */
    @RequestMapping("/toForget.html")
    public String toForget() {
        return "mall/user/forget";
    }
    /**
     * 登录
     *
     * @param username
     * @param password
     */
    @RequestMapping("/login.do")
    public void login(String username,
                      String password,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        User user = userService.checkLogin(username, password);
        if (user != null) {
            //登录成功 重定向到首页
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/mall/homepage.html");
        } else {
            response.sendRedirect("/mall/user/loginError.html");
        }

    }

    /**
     * 发送邮箱验证码
     *
     * @param email
     * @return
     */
    @PostMapping("/send_email")
    public String sendEmailCode(@RequestParam("email") String email, HttpSession session) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String generatorVCode = VerifyCodeUtils.generateVerifyCode(4);
        SendEmailUtil sendEmailUtil = new SendEmailUtil();
        sendEmailUtil.setSourceEmail(sourceEmail);//设置发件人邮箱
        sendEmailUtil.setEmail(email);//设置收件人邮箱
        sendEmailUtil.setCode(authorizationCode);//设置授权码

        sendEmailUtil.sendMsg("VerifyCode", generatorVCode.toUpperCase());//发送消息设置验证码
        session.setAttribute("registerVerifyCode", generatorVCode);


        session.setAttribute("email", email);
        session.setAttribute("dateTime", sdf.format(new Date()));

//        session.setAttribute("registerVerifyCode", "1234");
//
//
//        session.setAttribute("email", email);
//        session.setAttribute("dateTime", sdf.format(new Date()));

        return "redirect:/user/toRegister.html";
    }

    /**
     * 注册
     */
    @RequestMapping("/register.do")
    public String register(String username,
                           String password,
                           String name,
                           String phone,
                           String email,
                           String addr,
                           HttpServletResponse response, HttpSession session, String code, String mailCode) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object attribute = session.getAttribute("registerVerifyCode");
        if (attribute == null){
            return "redirect:/user/registerMailCodeError2.html";
        }
        String generateMailCode = attribute.toString();
        Date dateTime = sdf.parse((String) session.getAttribute("dateTime"));
        //判断验证码是否过期
        if (dateTime.getTime() + (60 * 1000) < new Date().getTime()) {
            //清空邮箱验证对应的Session
            session.setAttribute("registerVrifyCode", null);
            session.setAttribute("email", null);
            session.setAttribute("dateTime", null);
        }
        String seessionCode = session.getAttribute("code").toString();
        if (!seessionCode.equalsIgnoreCase(code)) {
            return "redirect:/user/registerGraphicCodeError.html";
        } else if (!mailCode.equalsIgnoreCase(generateMailCode)) {
            return "redirect:/user/registerMailCodeError.html";
        } else {
            User user = new User();
            user.setUsername(username);
            user.setPhone(phone);
            user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
            user.setName(name);
            user.setEmail(email);
            user.setAddr(addr);
            userService.create(user);
        }


        // 注册完成后重定向到登录页面
        //response.sendRedirect("/mall/user/toLogin.html");
        return "redirect:/user/toLogin.html";
    }

    /**
     * 图形验证码
     *
     * @param session
     * @param response
     * @throws IOException
     */
    @RequestMapping("generateImageCode")
    public void generateImageCode(HttpSession session, HttpServletResponse response) throws IOException {
        //生成随机数
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //保存到seession作用域
        session.setAttribute("code", code);
        //根据随机数去生成图片
        //通过response响应图片
        response.setContentType("image/png");
        ServletOutputStream os = response.getOutputStream();
        VerifyCodeUtils.outputImage(220, 60, os, code);

    }

    /**
     * 登出
     */
    @RequestMapping("/logout.do")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect("/mall/homepage.html");
    }

    /**
     * 发送忘记密码邮箱验证码
     *
     * @param email
     * @return
     */
    @PostMapping("/send_email_forget")
    public String sendEmailCodeForget(@RequestParam("email") String email, HttpSession session) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String generatorVCode = VerifyCodeUtils.generateVerifyCode(4);
        SendEmailUtil sendEmailUtil = new SendEmailUtil();
        sendEmailUtil.setSourceEmail(sourceEmail);//设置发件人邮箱
        sendEmailUtil.setEmail(email);//设置收件人邮箱
        sendEmailUtil.setCode(authorizationCode);//设置授权码

        sendEmailUtil.sendMsg("Verify Your Email", generatorVCode.toUpperCase());//发送消息

        //设置验证码
        session.setAttribute("forgetVerifyCode", generatorVCode);
        session.setAttribute("email", email);
        session.setAttribute("dateTime", sdf.format(new Date()));

//        session.setAttribute("forgetVerifyCode", "1234");
//        session.setAttribute("email", email);
//        session.setAttribute("dateTime", sdf.format(new Date()));

        return "redirect:/user/toForget.html";
    }


    /**
     * 忘记密码
     */

    @RequestMapping("/forget.do")
    public String forgetPassword( String password,String email, String graphicCode, String mailCode, HttpSession session) throws ParseException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //判断是否已发送验证码
        Object attribute = session.getAttribute("forgetVerifyCode");
        if (attribute == null){
            return "redirect:/user/registerMailCodeError2.html";
        }
        String generatedGraphicCode = session.getAttribute("code").toString();
        String generatedEmailCode = attribute.toString();
        Date dateTime = sdf.parse((String) session.getAttribute("dateTime"));
        //判断验证码是否过期
        if (dateTime.getTime() + (60 * 1000) < new Date().getTime()) {
            //清空邮箱验证对应的Session
            session.setAttribute("forgetVerifyCode", null);
            session.setAttribute("email", null);
            session.setAttribute("dateTime", null);
        }
        if (!generatedGraphicCode.equalsIgnoreCase(graphicCode)){
            return "redirect:/user/forgetGraphicCodeError.html";
        }

        if (!mailCode.equalsIgnoreCase(generatedEmailCode)) {
            return "redirect:/user/forgetMailCodeError.html";
        }

        User user = userService.findByEmail(email);

        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
        userService.update(user);
        return "redirect:/user/toLogin.html";
    }

    /**
     * 验证用户名是否唯一
     *
     * @param username
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkUsername.do")
    public boolean checkUsername(String username) {
        System.out.println(username);
        List<User> users = userService.findByUsername(username);
        if (users == null || users.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 验证邮箱地址是否唯一
     *
     * @param email
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkEmail.do")
    public boolean checkMailAdress(String email) {
        System.out.println(email);
        User users = userService.findByEmail(email);
        if (users == null) {
            return true;
        }
        return false;
    }

    /**
     * 如发生错误 转发到这页面
     *
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/error.html")
    public String error(HttpServletResponse response, HttpServletRequest request) {
        return "error";
    }

    @RequestMapping("/loginError.html")
    public String loginError() {
        return "loginError";
    }

    @RequestMapping("/forgetGraphicCodeError.html")
    public String forgetGraphicCodeError() {
        return "forgetGraphicCodeError";
    }

    @RequestMapping("/forgetMailCodeError.html")
    public String forgetMailCodeError() {
        return "forgetMailCodeError";
    }

    @RequestMapping("/registerGraphicCodeError.html")
    public String registerGraphicCodeError() {
        return "registerGraphicCodeError";
    }

    @RequestMapping("/registerMailCodeError.html")
    public String registerMailCodeError() {
        return "registerMailCodeError";
    }

    @RequestMapping("/registerMailCodeError2.html")
    public String registerMailCodeError2() {
        return "registerMailCodeError2";
    }
}
