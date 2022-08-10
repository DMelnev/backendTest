package org.lesson5.utils;

import lombok.experimental.UtilityClass;
import org.lesson5.api.CategoryService;
import org.lesson5.api.ProductService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@UtilityClass //make all methods as static
public class RetrofitUtils {
    static Properties properties = new Properties();
    static String propertiesLink = "src/main/resources/setup.properties";

    static {
        try {
            properties.load(new FileInputStream(propertiesLink));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    @SneakyThrows
    public String getBaseUrl() {
        return properties.getProperty("retrofitURL");
    }

    public Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static CategoryService getCategoryService() {
        return RetrofitUtils.getRetrofit().create(CategoryService.class);
    }
    public static ProductService getProductService() {
        return RetrofitUtils.getRetrofit().create(ProductService.class);
    }

}
