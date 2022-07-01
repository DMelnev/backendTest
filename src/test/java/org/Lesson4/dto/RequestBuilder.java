package org.Lesson4.dto;

public class RequestBuilder {
    private String item = "true";
    private String aisle = "";
    private String parse = "";

    public RequestBuilder setItem(String item) {
        this.item = item;
        return this;
    }

    public RequestBuilder setAisle(String aisle) {
        this.aisle = aisle;
        return this;
    }

    public RequestBuilder setParse(String parse) {
        this.parse = parse;
        return this;
    }

    public RequestAddToShoppingCart build() {
        return new RequestAddToShoppingCart(item, aisle, parse);
    }
}
