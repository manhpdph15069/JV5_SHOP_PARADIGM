package edu.poly.shop.service;

import edu.poly.shop.entities._Cart;
import edu.poly.shop.entities._Paradigm;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Collection;

public interface ICartService {

//    String addToCart(String pId, RedirectAttributes redirAttrs,Integer quantity);

    BigDecimal total();

//    void deleteCart(String id);

    _Paradigm detail(Integer id);

    void add(_Cart item);

    void remove(Integer id);

    _Cart update(Integer proID, int qty);

    void clead();

    Collection<_Cart> getAllItem();

    int getCount();
}
