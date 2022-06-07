package edu.poly.shop.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class __UserModel {
    private Integer id;
    private String fullname;
    private String phone;
    private String email;
    private String password;
    private String confirm_password;
    private String address;
    private Date createDate;
    private Integer status = 1;
    private Integer isAdmin = 0;

    public __UserModel(String fullname, String phone, String email, String address) {
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }
}
