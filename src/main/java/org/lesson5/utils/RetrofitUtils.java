package org.lesson5.utils;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//@UtilityClass
public class RetrofitUtils {
    static Properties properties = new Properties();
    static String propertiesLink = "src/resources/setup.properties";

    static {
        try {
            properties.load(new FileInputStream(propertiesLink));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    @SneakyThrows
    public static String getBaseUrl() {
        return properties.getProperty("retrofitURL");
    }

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
