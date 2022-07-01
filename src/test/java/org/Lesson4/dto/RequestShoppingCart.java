package org.Lesson4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestShoppingCart {
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    public RequestShoppingCart(String firstName, String lastName, String email) {
        this.username = firstName + "_" + lastName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
