package edu.poly.shop.service.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.poly.shop.entities._Cart;
import edu.poly.shop.entities._Paradigm;
import edu.poly.shop.repository.IParadigmRepository;
import edu.poly.shop.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Service
public class CartService implements ICartService {
    private static final String ATT_CART_NAME = "myCart";
    Map<Integer, _Cart> maps = new HashMap<>();
    @Autowired
    private HttpServletRequest request;
    @Autowired
    HttpSession session;
    @Autowired
    IParadigmRepository paradigmRepository;

    @Override
    public BigDecimal total() {
        BigDecimal total = new BigDecimal(0.0);
        List<_Paradigm> paradigms = paradigmRepository.findAll();
        for (Map.Entry<Integer, _Cart> entry : maps.entrySet()) {
            for (_Paradigm p : paradigms
            ) {
                if (p.getId() == entry.getValue().getProductId()) {
                    total = total.add(new BigDecimal(entry.getValue().getQty()).multiply(p.getPrice()));
                }
            }
        }
        return total;
    }

    @Override
    public _Paradigm detail(Integer id) {
        Optional<_Paradigm> paradigm = paradigmRepository.findById(id);
        if (paradigm.isPresent()) {
            return paradigm.get();
        }
        return null;
    }


    @Override
    public void add(_Cart item) {
        _Cart cartItem = maps.get(item.getProductId());
        if (cartItem == null) {
            maps.put(item.getProductId(), item);
        } else {
            cartItem.setQty(cartItem.getQty() + 1);
        }
    }

    @Override
    public void remove(Integer id) {
        maps.remove(id);
    }

    @Override
    public _Cart update(Integer proID, int qty) {
        _Cart item = maps.get(proID);
        item.setQty(qty);
        return item;
    }

    @Override
    public void clead() {
        maps.clear();
    }

    @Override
    public Collection<_Cart> getAllItem() {
        return maps.values();
    }

    @Override
    public int getCount() {
        return maps.values().size();
    }

}
