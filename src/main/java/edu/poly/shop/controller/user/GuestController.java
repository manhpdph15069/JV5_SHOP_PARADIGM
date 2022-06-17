package edu.poly.shop.controller.user;

import edu.poly.shop.beans.__Login;
import edu.poly.shop.beans.__UserModel;
import edu.poly.shop.entities._User;
import edu.poly.shop.service.IUserService;
import edu.poly.shop.utility.CommonConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/shop/user")
public class GuestController {
    @Autowired
    IUserService userService;
    @Autowired
    HttpSession session;

    @RequestMapping("register")
    public String register(ModelMap model) {
        __UserModel userModel = new __UserModel();
        model.addAttribute("USERMODEL", userModel);
        model.addAttribute("ACTION", "/shop/user/register-add");
        model.addAttribute("content", "/user/register");

        return "layout-guest";
    }

    @RequestMapping("/register-add")
    public String register(Model model, @ModelAttribute("USERMODEL") __UserModel dto,RedirectAttributes redirectAttributes) {
        userService.insert(dto);
        redirectAttributes.addFlashAttribute("messageTC", "Đăng ký tài khoản thành công");
        return "redirect:/shop/paradigm/";
    }

    @RequestMapping("/saveOrUpdate")
    public String update(Model model, @ModelAttribute("USERMODEL") __UserModel dto,RedirectAttributes redirectAttributes) {
        userService.insert(dto);
        redirectAttributes.addFlashAttribute("messageTC", "Cập nhập tài khoản thành công");
        return "redirect:/shop/paradigm/";
    }


    @RequestMapping("/edit")
    public String edit(ModelMap model) {
        __Login u = (__Login) session.getAttribute("username");
        Optional<_User> optional_user = userService.findByEmail(u.getEmail());
        __UserModel dto = null;
        if (optional_user.isPresent()) {
            _User user = optional_user.get();
            dto = new __UserModel(
                    user.getFullname()
                    , user.getPhone(),
                    user.getEmail(),
                    user.getAddress());
            model.addAttribute("USERMODEL", dto);
        }
        model.addAttribute("ACTION", "/shop/user/saveOrUpdate");
        model.addAttribute("content", "user/update-user");
        return "layout-guest";
    }
    @PostMapping("/dkm")
    public String dkm(@RequestParam("pass1")String pass1,@RequestParam("pass2")String pass2,@RequestParam("pass3")String pass3, RedirectAttributes redirectAttributes){

        userService.dkm(pass1,pass2,pass3,redirectAttributes);
        return "redirect:/shop/paradigm/";
    }
}
