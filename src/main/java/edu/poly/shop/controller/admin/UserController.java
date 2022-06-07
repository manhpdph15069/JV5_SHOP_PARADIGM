package edu.poly.shop.controller.admin;

import edu.poly.shop.beans.__ParadigmModel;
import edu.poly.shop.beans.__UserModel;
import edu.poly.shop.entities._Paradigm;
import edu.poly.shop.entities._User;
import edu.poly.shop.service.IUserService;
import edu.poly.shop.utility.CommonConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    IUserService userService;


    @RequestMapping("/list")
    public String index(ModelMap model, @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber) {
        Page<_User> paradigmsPage = userService.findAll(pageNumber, CommonConst.PAGE_SIZE);
        model.addAttribute("LIST_USER", paradigmsPage);
        __UserModel userModel = new __UserModel();
        model.addAttribute("USERMODEL", userModel);
        model.addAttribute("ACTION", "/admin/user/saveOrUpdate");
        model.addAttribute("title", "Thêm mới");
        model.addAttribute("form", "admin/user/create-update");
        model.addAttribute("table", "admin/user/view-user");
        return "layout-admin";
    }

    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(Model model, @ModelAttribute("USERMODEL") __UserModel dto) {
        userService.insert(dto);
        return "redirect:/admin/user/list";
    }


    @RequestMapping("/edit/{email}")
    public String edit(ModelMap model, @PathVariable("email") String email, @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber) {
        Optional<_User> optional_user = userService.findByEmail(email);
        __UserModel dto = null;
        if (optional_user.isPresent()) {
            _User user = optional_user.get();
            dto = new __UserModel(
                    user.getFullname()
                    , user.getPhone(),
                    user.getEmail(),
                    user.getAddress());
            model.addAttribute("USERMODEL", dto);
        } else {
            model.addAttribute("USERMODEL", new __UserModel());
        }
        Page<_User> userPage = userService.findAll(pageNumber, CommonConst.PAGE_SIZE);
        model.addAttribute("LIST_USER", userPage);
        model.addAttribute("ACTION", "/admin/user/saveOrUpdate");
        model.addAttribute("title", "Cập nhập");
        model.addAttribute("form", "admin/user/create-update");
        model.addAttribute("table", "admin/user/view-user");
        return "layout-admin";
    }

    @RequestMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return "redirect:/admin/user/list";
    }
}
