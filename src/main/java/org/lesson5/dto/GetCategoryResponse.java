package org.lesson5.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class GetCategoryResponse {

        private Integer id;
        private ArrayList<Product> products = new ArrayList<>();
        private String title;

}
