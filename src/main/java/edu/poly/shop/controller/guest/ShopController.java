package edu.poly.shop.controller.guest;

import edu.poly.shop.beans.__UserModel;
import edu.poly.shop.entities._Category;
import edu.poly.shop.entities._Figure;
import edu.poly.shop.entities._Paradigm;
import edu.poly.shop.service.*;
import edu.poly.shop.utility.CommonConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shop/paradigm")
public class ShopController {
    @Autowired
    IFigureService figureService;
    @Autowired
    ServletContext app;
    @Autowired
    ICategoryService categoryService;
    @Autowired
    IParadigmService paradigmService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    ICartService cardService;
    @Autowired
    IUserService userService;
    @Autowired
    HttpSession session;

    @RequestMapping("/")
    public String index(ModelMap model
            , @RequestParam(name = "pageNumber", defaultValue = "0" ,required = false) Optional<Integer> pageNumber
            , @RequestParam(name = "findCid", required = false) Integer findCid
            , @RequestParam(name = "findFid", required = false) Integer findFid) {
        if (findCid != null) {
            Optional<_Category> category = categoryService.findById(findCid);
            List<_Paradigm> paradigmsC = category.get().getParadigms();
            Pageable pageable = PageRequest.of(pageNumber.orElse(0), CommonConst.PAGE_SIZE);
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), paradigmsC.size());
            model.addAttribute("LIST_PARADIGM", new PageImpl<>(paradigmsC.subList(start, end), pageable, paradigmsC.size()));
            model.addAttribute("hrefPage","/shop/paradigm/?findCid="+findCid+"&pageNumber=");
        } else {
            if (findFid == null){
            Page<_Paradigm> paradigmsPage = paradigmService.getByPage(pageNumber.get(), CommonConst.PAGE_SIZE);
            model.addAttribute("LIST_PARADIGM", paradigmsPage);
            model.addAttribute("hrefPage","/shop/paradigm/?pageNumber=");
            }else {
                Optional<_Figure> figure = figureService.findById(findFid);
                List<_Paradigm> paradigmsC = figure.get().getParadigms();
                Pageable pageable = PageRequest.of(pageNumber.orElse(0), CommonConst.PAGE_SIZE);
                int start = (int) pageable.getOffset();
                int end = Math.min((start + pageable.getPageSize()), paradigmsC.size());
                model.addAttribute("LIST_PARADIGM", new PageImpl<>(paradigmsC.subList(start, end), pageable, paradigmsC.size()));
                model.addAttribute("hrefPage","/shop/paradigm/?findFid="+findFid+"&pageNumber=");
            }
        }
        model.addAttribute("content", "guest/shopping");
        return "layout-guest";
    }

    @RequestMapping("/addtoCart/{proID}")
    public String addToCart(ModelMap model, @PathVariable("proID") String proID) {
        cardService.addToCart(proID);
        return "redirect:/shop/paradigm/";
    }

    @GetMapping("/cart")
    public String cartDetaill(ModelMap model) {
        Object obj = session.getAttribute("myCart");
        if (obj != null) {
            model.addAttribute("CART_DETAILS", session.getAttribute("myCart"));
            model.addAttribute("TONGTIEN", cardService.total());
            model.addAttribute("content", "guest/cart");
            return "layout-guest";
        } else {
            model.addAttribute("message", "Bạn chưa có món đồ nào hãy chọn sản phẩm muốn mua");
            return "redirect:/shop/paradigm/";
        }
    }

    @RequestMapping("/delete")
    private String delete(ModelMap model, @RequestParam(value = "key") String key) {
        cardService.deleteCart(key);
        model.addAttribute("CART_DETAILS", session.getAttribute("myCart"));
        model.addAttribute("TONGTIEN", cardService.total());
        model.addAttribute("content", "guest/cart");
        return "layout-guest";
    }

    @RequestMapping("/register")
    public String register(ModelMap model){
        __UserModel userModel = new __UserModel();
        model.addAttribute("USERMODEL", userModel);
        model.addAttribute("ACTION", "/shop/paradigm/register-add");
        model.addAttribute("content", "/user/register");

        return "layout-guest";
    }


    @RequestMapping("/register-add")
    public String register(Model model, @ModelAttribute("USERMODEL") __UserModel dto) {
        userService.insert(dto);
        return "redirect:/shop/paradigm/";
    }

    @ModelAttribute("CATEGORIES")
    public List<_Category> categories() {
        return categoryService.findAll();
    }

    @ModelAttribute("FIGURES")
    public List<_Figure> figures() {
        return figureService.findAll();
    }
}
