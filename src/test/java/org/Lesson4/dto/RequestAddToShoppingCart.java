package org.Lesson4.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestAddToShoppingCart {
    private String item;
    private String aisle;
    private String parse;

    public RequestAddToShoppingCart(String item, String aisle, String parse) {
        this.item = item;
        this.aisle = aisle;
        this.parse = parse;
    }
}
