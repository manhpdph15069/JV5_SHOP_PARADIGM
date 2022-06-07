package edu.poly.shop.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class __ParadigmModel {
    private Integer id;
    private String paradigmName;
    private Double price;
    private Integer quantity;
    private Integer category_id;
    private MultipartFile image;
    private Integer figure_id;
    private Timestamp createDate;
    private Integer status;
    private String dimension;
    private Integer material;
}
