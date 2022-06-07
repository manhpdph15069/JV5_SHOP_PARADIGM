package edu.poly.shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class _User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID", nullable = false)
    private Integer id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "phone", length = 25)
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "address", length = 500)
    private String address;

//    @CreationTimestamp
    @Column(name = "createdate")
    private Date createDate;

    @Column(name = "status")
    private Integer status = 1;

    @Column(name = "isadmin")
    private Integer isAdmin = 0;

    @OneToMany(mappedBy = "user")
    private List<_Order> orderList;

    public _User(Integer id, String fullname, String phone, String email, String password, String address, Date createDate, Integer status, Integer isAdmin) {
        this.id = id;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.createDate = createDate;
        this.status = status;
        this.isAdmin = isAdmin;
    }
}