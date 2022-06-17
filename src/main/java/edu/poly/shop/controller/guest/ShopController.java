package edu.poly.shop.controller.guest;

import edu.poly.shop.beans.__CustomerModel;
import edu.poly.shop.beans.__Login;
import edu.poly.shop.beans.__ParadigmModel;
import edu.poly.shop.beans.__UserModel;
import edu.poly.shop.entities._Cart;
import edu.poly.shop.entities._Category;
import edu.poly.shop.entities._Figure;
import edu.poly.shop.entities._Paradigm;
import edu.poly.shop.service.*;
import edu.poly.shop.utility.CommonConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
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

    @RequestMapping("/home")
    public String home(ModelMap modelMap) {
        modelMap.addAttribute("content","guest/home");
        return "layout-guest";
    }

    @RequestMapping("/")
    public String index(ModelMap model
            , @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Optional<Integer> pageNumber
            , @RequestParam(name = "findCid", required = false) Integer findCid
            , @RequestParam(name = "findFid", required = false) Integer findFid) {

        if (findCid != null) {
            Optional<_Category> category = categoryService.findById(findCid);
            List<_Paradigm> paradigmsC = category.get().getParadigms();
            epKieu(pageNumber, paradigmsC, model);
            model.addAttribute("hrefPage", "/shop/paradigm/?findCid=" + findCid + "&pageNumber=");
        } else {
            if (findFid == null) {
                Page<_Paradigm> paradigmsPage = paradigmService.getByPage(pageNumber.get(), CommonConst.PAGE_SIZE);
                model.addAttribute("LIST_PARADIGM", paradigmsPage);
                model.addAttribute("hrefPage", "/shop/paradigm/?pageNumber=");
            } else {
                Optional<_Figure> figure = figureService.findById(findFid);
                List<_Paradigm> paradigmsF = figure.get().getParadigms();
                epKieu(pageNumber, paradigmsF, model);
                model.addAttribute("hrefPage", "/shop/paradigm/?findFid=" + findFid + "&pageNumber=");
            }
        }
        model.addAttribute("content", "guest/shopping");
        return "layout-guest";
    }

    @RequestMapping("/find")
    public String findName(Model model,
                           @RequestParam(value = "name") String name,
                           @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Optional<Integer> pageNumber
    ) {
        List<_Paradigm> paradigmListByName = paradigmService.findName(name);
        Pageable pageable = PageRequest.of(pageNumber.orElse(0), CommonConst.PAGE_SIZE);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), paradigmListByName.size());
        model.addAttribute("LIST_PARADIGM", new PageImpl<>(paradigmListByName.subList(start, end), pageable, paradigmListByName.size()));
        model.addAttribute("hrefPage", "/shop/paradigm/find?name=" + name + "&pageNumber=");
        model.addAttribute("content", "guest/shopping");
        return "layout-guest";
    }

    public void epKieu(Optional<Integer> pageNumber, List<_Paradigm> list, ModelMap model) {
        Pageable pageable = PageRequest.of(pageNumber.orElse(0), CommonConst.PAGE_SIZE);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        model.addAttribute("LIST_PARADIGM", new PageImpl<>(list.subList(start, end), pageable, list.size()));
    }

    @RequestMapping("/addtoCart/{proID}")
    public String addToCart(ModelMap model, @PathVariable("proID") Integer proID, RedirectAttributes redirAttrs) {

        String q = request.getParameter("quantity");
        Integer quantity = 1;
        if (q != null) {
            quantity = Integer.valueOf(q);
        }
        Optional<_Paradigm> paradigm = paradigmService.findById(proID);
        if (paradigm != null) {
            if (paradigm.get().getQuantity() == 0) {
                redirAttrs.addFlashAttribute("errors", "Sản phẩm hiện đã bán hết");
            } else {
                _Cart cart = new _Cart();
                cart.setProductId(paradigm.get().getId());
                cart.setQty(quantity);
                cart.setImage(paradigm.get().getImage());
                cart.setPrice(paradigm.get().getPrice());
                cart.setNamePro(paradigm.get().getParadigmName());
                cardService.add(cart);
                redirAttrs.addFlashAttribute("messageTC", "Thêm sản phẩm vào giỏ hàng thành công");
            }
        }
        return "redirect:/shop/paradigm/";
    }

    @RequestMapping("/cart")
    public String cartDetaill(ModelMap model, RedirectAttributes redirAttrs) {
        __Login user = (__Login) session.getAttribute("username");
        Object obj = cardService.getAllItem();
        Boolean check = true;
//        if (obj != null) {
        if (cardService.getAllItem().size() != 0) {
            if (user != null) {
                check = true;
            } else {
                model.addAttribute("CUSTOMER", new __CustomerModel());
                check = false;
            }
            model.addAttribute("CART_DETAILS", cardService.getAllItem());
            model.addAttribute("LIST_PARADIGM", paradigmService.findAll());
            model.addAttribute("check", check);
            model.addAttribute("content", "guest/cart");
            session.removeAttribute("errorCCH");
            return "layout-guest";
        } else {
            session.setAttribute("errorCCH", "Bạn chưa có món đồ nào hãy chọn sản phẩm muốn mua rồi quay lại");
            model.addAttribute("content", "guest/cart");
            return "layout-guest";
        }

//        }
    }

    @GetMapping("/cart/clear")
    public String clear() {
        cardService.clead();
        return "redirect:/shop/paradigm/cart";
    }

    @RequestMapping("/delete")
    private String delete(ModelMap model, @RequestParam(value = "key") Integer key) {
        cardService.remove(key);
        model.addAttribute("CART_DETAILS", cardService.getAllItem());
//        model.addAttribute("TONGTIEN", cardService.total());
        return "redirect:/shop/paradigm/cart";
    }

    @PostMapping("/update")
    public String update(@RequestParam("id") Integer id, @RequestParam("quantity") Integer qty,Model model) {
        cardService.update(id, qty);
//        model.addAttribute("CART_DETAILS", cardService.getAllItem());
//        model.addAttribute("content", "guest/cart");
        return "forward:/shop/paradigm/cart";
    }


    @GetMapping("/detail")
    public String detail(ModelMap model, @RequestParam("id") Integer id) {
        _Paradigm paradigm = cardService.detail(id);

        model.addAttribute("PARADIGM_DETAIL_ID", id);
        model.addAttribute("PARADIGM_DETAIL", paradigm);
        model.addAttribute("content", "guest/detail");
        return "layout-guest";
    }


    @ModelAttribute("CATEGORIES")
    public List<_Category> categories() {
        return categoryService.findAll();
    }

    @ModelAttribute("FIGURES")
    public List<_Figure> figures() {
        return figureService.findAll();
    }

    @ModelAttribute("TONGTIEN")
    public BigDecimal tongtien() {
        return cardService.total();
    }
}
