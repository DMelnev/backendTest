package Lesson3;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractTest {
    static Properties properties = new Properties();
    private static InputStream configFile;
    private static String apiKey;
    private static String baseURL;


    @BeforeAll
    static void initTest() throws IOException {
        configFile = new FileInputStream("src/resources/setup.properties");
        properties.load(configFile);
        apiKey = properties.getProperty("apiKey");
        baseURL = properties.getProperty("baseUrl");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getBaseURL() {
        return baseURL;
    }
}
