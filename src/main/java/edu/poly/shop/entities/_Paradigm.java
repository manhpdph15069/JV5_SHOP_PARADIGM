package edu.poly.shop.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "paradigm")
public class _Paradigm {
    @Id
    @Column(name = "paradigmID", nullable = false)
    private Integer id;

    @Column(name = "paradigmname")
    private String paradigmName;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private _Category category;

    @Column(name = "image", length = 500)
    private String image;

    @Column(name = "dimension")
    private String dimension;

    @Column(name = "material")
    private Integer material;

    @CreationTimestamp
    @Column(name = "createdate")
    private Timestamp createDate;

    @ManyToOne
    @JoinColumn(name = "figureID")
    private _Figure figure;

    @Column(name = "status")
    private Integer status = 1;

//    @ManyToOne
//    @JoinColumn(name = "order_order_id")
//    private Order order;

    @OneToMany(mappedBy = "paradigm")
    private List<_Orderdetail> orderdetails;

    public _Paradigm(Integer id, String paradigmName, Double price, Integer quantity, _Category category, String image, String dimension, Integer material, Timestamp createDate, _Figure figure, Integer status) {
        this.id = id;
        this.paradigmName = paradigmName;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.image = image;
        this.dimension = dimension;
        this.material = material;
        this.createDate = createDate;
        this.figure = figure;
        this.status = status;
    }
}