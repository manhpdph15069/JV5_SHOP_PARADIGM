package edu.poly.shop.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class __UserModel {

    @Size(min = 3,max=500)
    private String fullname;

    @NotBlank
    private String phone;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String password;

    @NotBlank
    private String confirm_password;


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
