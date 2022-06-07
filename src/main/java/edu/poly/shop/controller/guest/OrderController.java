package edu.poly.shop.controller.guest;

import edu.poly.shop.repository.IOrderRepository;
import edu.poly.shop.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop/paradigm/order")
public class OrderController {
    @Autowired
    IOrderService orderService;

    @RequestMapping("/add-order")
    public String add(){
       return orderService.add();
    }
}
