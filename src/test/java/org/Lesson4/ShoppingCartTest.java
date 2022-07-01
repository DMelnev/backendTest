package org.Lesson4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import org.Lesson4.dto.RequestBuilder;
import org.Lesson4.dto.RequestShoppingCart;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;

public class ShoppingCartTest extends AbstractTest {
    private static String userName;
    private static String hash;
    private int id;

    @BeforeAll
    static void getUserData() throws JsonProcessingException {
        JsonPath jsonPath = given()
                .body(new ObjectMapper().writeValueAsString(new RequestShoppingCart(
                        properties.getProperty("name"),
                        properties.getProperty("lastName"),
                        properties.getProperty("email")
                )))
                .post("/users/connect")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();
        userName = jsonPath.getString("username");
        hash = jsonPath.getString("hash");
    }

    @BeforeEach
    void setup() {
        given()
                .queryParam("hash", hash)
                .get("/mealplanner/{username}/shopping-list", userName)
                .then()
                .statusCode(200)
                .body("aisles", Matchers.hasSize(0));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1kg meat, Meat",
            "2kg nuts, Nuts",
            "1l milk, Milk",
            "500g Strawberry, Strawberry",
            "50ml strong coffee, Coffee"
    })
    void addToShoppingCartTest(String item, String aisle) throws JsonProcessingException {
        given()
                .queryParam("hash", hash)
                .body(new ObjectMapper().writeValueAsString(new RequestBuilder()
                                .setAisle(aisle)
                                .setItem(item)
                                .setParse("true")
                                .build()))
                .post("/mealplanner/{username}/shopping-list/items", userName)
                .then()
                .spec(responseSpecification);

        id = given()
                .queryParam("hash", hash)
                .get("/mealplanner/{username}/shopping-list", userName)
                .then()
                .spec(responseSpecification)
                .body("aisles", Matchers.hasSize(1))
                .body("aisles.aisle", Matchers.hasItems(aisle))
                .body("aisles.items", Matchers.hasSize(1))
                .extract()
                .jsonPath()
                .getInt("aisles.items[0].id[0]");
    }

    @AfterEach
    void delete() {
        given()
                .queryParam("hash", hash)
                .delete("/mealplanner/{username}/shopping-list/items/{id}", userName, id)
                .then()
                .statusCode(200);
    }
}
