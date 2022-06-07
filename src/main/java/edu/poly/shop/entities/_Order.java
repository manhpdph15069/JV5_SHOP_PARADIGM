package edu.poly.shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`order`")
public class _Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderID", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userID")
    private _User user;

    @ManyToOne
    @JoinColumn(name = "customerID")
    private _Customer customer;

    @CreationTimestamp
    @Column(name = "createdate")
    private Timestamp createDate;

    @Column(name = "status")
    private Integer status =1;

    @Column(name = "total")
    private Double total;

    @OneToMany(mappedBy = "order")
    private List<_Orderdetail> orderdetails;
//    @OneToMany(mappedBy = "order")
//    private List<Paradigm> paradigms;


}