package org.Lesson5;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.lesson5.dto.ProductDto;
import retrofit2.Response;

import static org.lesson5.utils.RetrofitUtils.getProductService;

public class ProductTest {
    ProductDto productDto;

    BeforeAll
    void beforeAll(){

    }

    @SneakyThrows
    @Test
    void createProductTest() {
        Response<ProductDto> response = getProductService().createProduct(productDto).execute();
        Assertions.assertTrue(response.isSuccessful());
        assert response.body() != null;
        Assertions.assertEquals(productDto.getTitle(), response.body().getTitle());
        Assertions.assertEquals(productDto.getPrice(), response.body().getPrice());
    }
}
