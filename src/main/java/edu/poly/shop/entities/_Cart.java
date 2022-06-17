package edu.poly.shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class _Cart {
    private Integer productId;
    private String namePro;
    private String image;
    private BigDecimal price;
    private int qty = 1;
}
