package org.Lesson4;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;

public class PostTest extends AbstractTest {

    @BeforeAll
    public static void setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType("application/x-www-form-urlencoded")
                .build();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "Falafel Burger, Middle Eastern",
            "Vegan Jambalaya, Creole",
            "African Chicken Peanut Stew, African",
            "Chicken Fajita Stuffed Bell Pepper, Mexican",
            "Spicy Black-Eyed Pea Curry with Swiss Chard and Roasted Eggplant, Indian"
    })
    public void cuisineTest(String title, String cuisine) {
        String cuisineRequest = "/recipes/cuisine";
        given()
                .queryParam("title", title)
                .post(cuisineRequest)
                .then()
                .statusCode(200)
                .body("cuisine", Matchers.containsString(cuisine));
    }
}
