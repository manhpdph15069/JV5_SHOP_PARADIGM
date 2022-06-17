package edu.poly.shop.controller.admin;

import edu.poly.shop.repository.IStatsRepository;
import edu.poly.shop.repository.impl.StatsRepositoryImpl;
import edu.poly.shop.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/tk")
public class StatsController {
    @Autowired
    IOrderDetailService orderDetailService;

    @RequestMapping("/")
    public String banChay(Model model){
        Map<String, Integer> CHARBC = new LinkedHashMap<>();


        for (Object[] o: orderDetailService.banChay()
             ) {
            CHARBC.put(o[1].toString(),Integer.valueOf(String.valueOf(o[2])));
        }
        model.addAttribute("CHARBC",CHARBC);
        model.addAttribute("PARADIGM_STATSBC",orderDetailService.banChay());
        model.addAttribute("PARADIGM_STATSDT",orderDetailService.doanhThu());
        model.addAttribute("form", "/admin/order/barGraph");
        model.addAttribute("table", "/admin/stats/paradigm-stats");
        return "layout-admin";
    }

}
