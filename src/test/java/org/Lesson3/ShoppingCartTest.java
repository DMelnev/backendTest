package org.Lesson3;

import io.restassured.path.json.JsonPath;
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
    static void getUserData() {
        JsonPath jsonPath = given()
                .body("{\n" +
                        "    \"username\": \"" + properties.getProperty("name") + "\",\n" +
                        "    \"firstName\": \"" + properties.getProperty("name") + "\",\n" +
                        "    \"lastName\": \"" + properties.getProperty("lastName") + "\",\n" +
                        "    \"email\": \"" + properties.getProperty("email") + "\"\n}")
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
    void addToShoppingCartTest(String item, String aisle) {
        given()
                .queryParam("hash", hash)
                .body("{\n" +
                        "    \"item\": \"" + item + "\",\n" +
                        "    \"aisle\": \"" + aisle + "\",\n" +
                        "    \"parse\": true\n}")
                .post("/mealplanner/{username}/shopping-list/items", userName)
                .then()
                .statusCode(200);

        id = given()
                .queryParam("hash", hash)
                .get("/mealplanner/{username}/shopping-list", userName)
                .then()
                .statusCode(200)
                .body("aisles", Matchers.hasSize(1))
                .body("aisles.aisle", Matchers.hasItems(aisle))
                .body("aisles.items", Matchers.hasSize(1))
                .extract()
                .jsonPath()
                .getInt("aisles.items[0].id[0]");
    }

    @AfterEach
    void tearDown() {
        given()
                .queryParam("hash", hash)
                .delete("/mealplanner/{username}/shopping-list/items/{id}", userName, id)
                .then()
                .statusCode(200);
    }
}
