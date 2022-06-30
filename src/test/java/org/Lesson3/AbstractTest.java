package org.Lesson3;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractTest {
    static Properties properties = new Properties();
    private static InputStream configFile;


    @BeforeAll
    static void initTest() throws IOException {
        configFile = new FileInputStream("src/resources/setup.properties");
        properties.load(configFile);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = properties.getProperty("baseUrl");
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", properties.getProperty("apiKey"))
                .build();
    }

}
