package edu.poly.shop.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "material")
public class _Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "material_name", length = 200)
    private String materialName;

    @OneToMany(mappedBy = "material")
    private List<_Paradigm> paradigmList;

    public _Material(Integer id, String materialName) {
        this.id = id;
        this.materialName = materialName;
    }
}