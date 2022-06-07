package edu.poly.shop.service;

import edu.poly.shop.entities._Cart;

import javax.servlet.http.HttpServletRequest;

public interface ICartService {

    void addToCart(String pId);

    Double total();

    void deleteCart(String id);
}
