package org.Lesson3;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;

public class GetTest extends AbstractTest {

    String complexSearch = "/recipes/complexSearch";

    @BeforeAll
    public static void setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addQueryParam("number", 10)
                .build();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "Burger",
            "Pizza",
            "Pasta",
            "Fish",
            "Pork",
            "Salad",
            "Chicken",
            "Egg",
            "Beans",
            "Potatoes"
    })
    public void searchTest(String param) {
        given()
                .queryParam("query", param)
                .get(complexSearch)
                .then()
                .statusCode(200)
                .body("results[0].title", Matchers.containsString(param));
    }

}
