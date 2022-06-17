package edu.poly.shop.controller.admin;

import edu.poly.shop.beans.__CustomerModel;
import edu.poly.shop.entities._Order;
import edu.poly.shop.service.IOrderDetailService;
import edu.poly.shop.service.IOrderService;
import edu.poly.shop.utility.CommonConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class OrderController {
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderDetailService orderDetailService;

    @RequestMapping("/admin/order//done/{id}")
    public String done(@PathVariable("id")Integer id,RedirectAttributes redirectAttributes){
        _Order order = orderService.done(id);
        if (order != null){
            redirectAttributes.addFlashAttribute("messageTC","Xác nhận đơn đã hoàn thành");
        }else {
            redirectAttributes.addFlashAttribute("errors","Xác nhận đơn đã hoàn thành lỗi");
        }
        return "redirect:/admin/order/index";
    }

    @RequestMapping("/admin/order/confirm/{id}")
    public String confirm(@PathVariable("id") Integer id,RedirectAttributes redirectAttributes) {
        _Order order = orderService.confirm(id);
        if (order != null){
            redirectAttributes.addFlashAttribute("messageTC","Xác nhận đơn thành công");
        }else {
            redirectAttributes.addFlashAttribute("errors","Xác nhận đơn lỗi");
        }
        return "redirect:/admin/order/index";
    }

    @RequestMapping("/admin/order/cancel/{id}")
    public String cancel(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        _Order order = orderService.cancel(id);
        if (order != null) {
            redirectAttributes.addFlashAttribute("messageTC", "Hủy đơn thành thành công");
        } else {
            redirectAttributes.addFlashAttribute("errors", "Hủy đơn đơn lỗi");
        }
        return "redirect:/admin/order/index";
    }

    @GetMapping("/admin/order/index")
    public String index
            (
                    @RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                    Model model
            ) {

        model.addAttribute("LIST_ORDER", orderService.findAllPage(pageNumber, CommonConst.PAGE_SIZE));

        model.addAttribute("table", "/admin/order/view");
        model.addAttribute("form", "/admin/order/empty");

        return "layout-admin";
    }

    @GetMapping("/admin/order/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("ORDERDETAIL", orderService.findById(id).get());
        model.addAttribute("table", "/admin/order/detail");
        model.addAttribute("form", "/admin/order/empty");
        return "layout-admin";
    }



    @RequestMapping("/order/add-order")
    public String add(
            Model model,
             @ModelAttribute("CUSTOMER") __CustomerModel dto,
             RedirectAttributes redirAttrs) {
            return orderService.add(dto, redirAttrs);
        }
    }

