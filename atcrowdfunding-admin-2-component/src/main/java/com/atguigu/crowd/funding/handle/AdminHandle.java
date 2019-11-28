package com.atguigu.crowd.funding.handle;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.service.api.AdminService;
import com.atguigu.crowd.funding.util.CrowdFundingConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminHandle {
    @Autowired
    private AdminService adminService;


    @RequestMapping("/admin/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/index.html";
    }

    @RequestMapping("/admin/to/login/page")
    public String toLoginPage(){
        return "admin-login";
    }

    @RequestMapping("/admin/do/login")
    public String doLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            Model model,
            HttpSession session) {

        // 调用adminService的login方法执行登录业务逻辑，返回查询到的Admin对象
        Admin admin = adminService.login(loginAcct, userPswd);

        // 判断admin是否为null
        if(admin == null) {

            model.addAttribute(CrowdFundingConstant.ATTR_NAME_MESSAGE, CrowdFundingConstant.MESSAGE_LOGIN_FAILED);

            return "admin-login";
        }

        session.setAttribute(CrowdFundingConstant.ATTR_NAME_LOGIN_ADMIN, admin);

        return "admin-main";
    }

    @RequestMapping("/admin/get/all")
    public String getAll(Model model){
        List<Admin> list = adminService.getAll();
    model.addAttribute("list",list);
    return "admin-target";
    }
}
