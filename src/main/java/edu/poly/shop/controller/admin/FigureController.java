package edu.poly.shop.controller.admin;

import edu.poly.shop.entities._Figure;
import edu.poly.shop.entities._Paradigm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/figure")
public class FigureController {
    @GetMapping("/")
    public String index(Model model, @ModelAttribute("figure") _Figure figure) {
        model.addAttribute("view1", "admin/paradigm/create.jsp");
        model.addAttribute("view2", "admin/paradigm/index.jsp");
        return "layout-admin";
    }
}
