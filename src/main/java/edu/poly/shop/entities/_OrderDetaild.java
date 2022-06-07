package edu.poly.shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class _OrderDetaild implements Serializable {
    private static final long serialVersionUID = 1L;

    private _Order order;
    private _Paradigm paradigm;
}
