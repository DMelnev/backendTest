package org.Lesson4;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.internal.mapping.Jackson1Mapper;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public abstract class AbstractTest {
    static Properties properties = new Properties();
    static String propertiesLink = "src/resources/setup.properties";
    protected static ResponseSpecification responseSpecification;
    protected static RequestSpecification requestSpecification;

    @BeforeAll
    static void initTest() throws IOException {
        properties.load(new FileInputStream(propertiesLink));

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = properties.getProperty("baseUrl");
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", properties.getProperty("apiKey"))
                .setContentType(ContentType.JSON)
                .addQueryParam("offset", 0)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectResponseTime(Matchers.lessThan(1500L), TimeUnit.MILLISECONDS)
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .build();
        RestAssured.config = RestAssured.config().objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.JACKSON_2));
    }
}
