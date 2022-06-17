package edu.poly.shop.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class __ParadigmModel {

    @NotNull(message = "Không dược để trống mã mô hình")
    private Integer id;

    @NotBlank(message = "Không dược để trống tên mô hình")
    private String paradigmName;


    private BigDecimal price;

    private Integer quantity;
    private Integer category_id;
    private MultipartFile image;
    private Integer figure_id;
    private Timestamp createDate;
    private Integer status;
    private String dimension;
    private Integer material_id;
}
