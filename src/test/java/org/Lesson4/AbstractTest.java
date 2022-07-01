package org.Lesson4;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class AbstractTest {
    static Properties properties = new Properties();
    static String propertiesLink = "src/resources/setup.properties";

    @BeforeAll
    static void initTest() throws IOException {
        properties.load(new FileInputStream(propertiesLink));

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = properties.getProperty("baseUrl");
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", properties.getProperty("apiKey"))
                .build();
    }

}
