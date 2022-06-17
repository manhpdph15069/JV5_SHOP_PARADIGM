package edu.poly.shop.controller.user;

import edu.poly.shop.beans.__Login;
import edu.poly.shop.entities._User;
import edu.poly.shop.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    IUserService userService;
    @Autowired
    HttpSession session;

    @GetMapping("")
    public String loginView(Model model) {
        __Login login = new __Login();
        model.addAttribute("LOGINMODEL", login);
        return "user/login";
    }

    @PostMapping("/dn")
    public String login(@ModelAttribute("LOGINMODEL") __Login login, RedirectAttributes redirectAttributes) {
        userService.login(login.getEmail(),login.getPassword());
        __Login user = (__Login) session.getAttribute("username");

        if (user == null) {
            return "user/login";
        } else {
            if (user.getIsAdmin() == 1) {
                return "redirect:/admin/paradigm/list";
            } else {
                return "redirect:/shop/paradigm/home";
            }
        }

//                Object ruri = session.getAttribute("redirect-uri");
//                if (ruri!=null){
//                    session.removeAttribute("redirect-uri");
//                    return "redirect:/" ;
//                }
    }


}
