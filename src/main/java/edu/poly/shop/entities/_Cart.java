package edu.poly.shop.entities;

import java.util.Map;

public class _Cart {

    private Map<String, _Orderdetail> cartDetails;

    public Map<String, _Orderdetail> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(Map<String, _Orderdetail> cartDetails) {
        this.cartDetails = cartDetails;
    }
}
