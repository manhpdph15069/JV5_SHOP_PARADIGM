package edu.poly.shop.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class __Login {
    private String email;
    private String password;
    private Integer isAdmin;
}
