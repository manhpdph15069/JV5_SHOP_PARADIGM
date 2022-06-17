package edu.poly.shop.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class __CustomerModel {
    private Integer id;

    @NotBlank
    private String fullname;

    @NotBlank
    @Size(max = 10)
    private String phone;

    @NotBlank
    private String email;

    @NotBlank
    private String address;

    private Timestamp createDate;
    private Integer status = 1;
}
