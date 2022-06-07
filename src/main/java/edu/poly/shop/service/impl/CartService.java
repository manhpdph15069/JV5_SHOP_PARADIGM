package edu.poly.shop.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.poly.shop.entities._Orderdetail;
import edu.poly.shop.entities._Paradigm;
import edu.poly.shop.repository.IParadigmRepository;
import edu.poly.shop.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService implements ICartService {
    private static final String ATT_CART_NAME = "myCart";
    @Autowired
    private HttpServletRequest request;
    @Autowired
    HttpSession session;
    @Autowired
    IParadigmRepository paradigmRepository;

    @Override
    public void addToCart(String pId) {
        Optional<_Paradigm> paradigm = paradigmRepository.findById(Integer.parseInt(pId));

        HttpSession session = request.getSession();
        Object obj = session.getAttribute(ATT_CART_NAME);// luu tam vao session
        if (obj == null) {// tao moi
            // Tao mat hang
            _Orderdetail details = new _Orderdetail();
            details.setParadigm(paradigm.get());
            details.setPurchasedQuantity(1);
            details.setPurchasedMoney(paradigm.get().getPrice());
            // gio hang co nhieu mat hang

            Map<String, _Orderdetail> map = new HashMap<>();
            map.put(pId, details);// them mat hang vao ds

            session.setAttribute(ATT_CART_NAME, map);// luu tam vao session
        } else {
            Map<String, _Orderdetail> map = (Map<String, _Orderdetail>) obj;

            _Orderdetail details = map.get(pId);

            if (details == null) {
                details = new _Orderdetail();
                details.setParadigm(paradigm.get());
                details.setPurchasedQuantity(1);
                details.setPurchasedMoney(paradigm.get().getPrice());

                map.put(pId, details);
            } else {

                details.setPurchasedQuantity(details.getPurchasedQuantity() + 1);
            }

            session.setAttribute(ATT_CART_NAME, map);// luu tam vao session
        }
    }
    @Override
    public Double total(){
        HttpSession session = request.getSession();
        Object obj = session.getAttribute(ATT_CART_NAME);
        Map<String, _Orderdetail> map = (Map<String, _Orderdetail>) obj;
        Double total = 0.0;
        for (Map.Entry<String, _Orderdetail> entry : map.entrySet()) {
            total += entry.getValue().getPurchasedQuantity() * entry.getValue().getPurchasedMoney();
        }
        return total;
    }
    @Override
    public void deleteCart(String id) {
        // gio hang co nhieu mat hang
        HttpSession session = request.getSession();
        Object obj = session.getAttribute(ATT_CART_NAME);// luu tam vao session

        if (obj != null) {
            Map<String, _Orderdetail> map = (Map<String, _Orderdetail>) obj;
            map.remove(id);
            // update lai vao session
            session.setAttribute(ATT_CART_NAME, map);
        }
    }
}
