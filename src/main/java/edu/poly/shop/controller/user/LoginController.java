package edu.poly.shop.controller.user;

import edu.poly.shop.beans.__Login;
import edu.poly.shop.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    IUserService userService;
    @GetMapping("")
    public String loginView(Model model) {
        __Login login = new __Login();
        model.addAttribute("LOGINMODEL", login);
        return "user/login";
    }

    @PostMapping("/dn")
    public String login(@ModelAttribute("LOGINMODEL")__Login login) {
        System.out.println(login.getEmail()+login.getPassword());
        return userService.login(login.getEmail(),login.getPassword());
    }
}
