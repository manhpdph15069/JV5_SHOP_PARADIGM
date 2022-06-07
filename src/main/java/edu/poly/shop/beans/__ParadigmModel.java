package edu.poly.shop.beans;

import edu.poly.shop.entities._Category;
import edu.poly.shop.entities._Figure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

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
}
