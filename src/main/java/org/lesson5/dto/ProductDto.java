package org.lesson5.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class ProductDto {

    private Integer id;
    private String title;
    private Integer price;
    private String categoryTitle;


}
