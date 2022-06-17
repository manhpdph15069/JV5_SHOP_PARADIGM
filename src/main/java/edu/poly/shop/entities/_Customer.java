package edu.poly.shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class _Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerID", nullable = false)
    private Integer id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone", length = 25)
    private String phone;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "status")
    private Integer status =1;

    @CreationTimestamp
    @Column(name = "create_date")
    private Timestamp createDate;

    @OneToMany(mappedBy = "customer")
    private List<_Order> orders;
}