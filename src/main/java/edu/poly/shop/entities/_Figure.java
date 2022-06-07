package edu.poly.shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "figure")
public class _Figure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "figureID", nullable = false)
    private Integer id;

    @Column(name = "figurename")
    private String figureName;

    @OneToMany(mappedBy = "figure")
    private List<_Paradigm> paradigms;

    public _Figure(Integer id, String figureName) {
        this.id = id;
        this.figureName = figureName;
    }
}