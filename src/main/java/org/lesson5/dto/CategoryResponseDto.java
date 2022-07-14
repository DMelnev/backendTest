package org.lesson5.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CategoryResponseDto {

        private Integer id;
        private ArrayList<ProductDto> productDtos = new ArrayList<>();
        private String title;

}
