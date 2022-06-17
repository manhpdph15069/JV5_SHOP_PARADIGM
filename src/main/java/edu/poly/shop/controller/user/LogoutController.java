package edu.poly.shop.controller.user;

import edu.poly.shop.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {
    @Autowired
    HttpSession session;
    @Autowired
    ICartService cartService;

    @RequestMapping("/logout")
    public String logout(){
        session.removeAttribute("username");
        cartService.clead();
        return "redirect:/login";
    }
}
