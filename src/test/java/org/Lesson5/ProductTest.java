package org.Lesson5;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lesson5.dto.ProductDto;
import retrofit2.Response;

import java.util.Objects;

import static org.lesson5.utils.RetrofitUtils.getCategoryService;
import static org.lesson5.utils.RetrofitUtils.getProductService;

public class ProductTest {
    ProductDto productDto, newProductDto;

    @SneakyThrows
    @BeforeEach
    void beforeEach() {
        productDto = new ProductDto()
                .withTitle(new Faker().food().ingredient())
                .withPrice((int) (Math.random() * 10000));
        newProductDto = new ProductDto()
                .withTitle(new Faker().food().ingredient())
                .withPrice((int) (Math.random() * 10000));
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void createProductTest(int categoryId) {

        productDto.setCategoryTitle(getCategoryById(categoryId));
        Response<ProductDto> response = createProduct(categoryId);
        productDto.setId(response.body().getId());
        Assertions.assertEquals(productDto, response.body());
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void modifyProductTest(int categoryId) {

        Response<ProductDto> response = createProduct(categoryId);
        newProductDto.setCategoryTitle(productDto.getCategoryTitle());
        productDto.setId(response.body().getId());
        newProductDto.setId(productDto.getId());
        Response<ProductDto> responseNew = getProductService()
                .modifyProduct(newProductDto)
                .execute();
        Assertions.assertTrue(responseNew.isSuccessful());
        assert responseNew.body() != null;
        newProductDto.setId(responseNew.body().getId());
        Assertions.assertEquals(newProductDto, responseNew.body());
        productDto = newProductDto;

    }

    @SneakyThrows
    @Test
    void getProductByIdTest() {
        Response<ProductDto> response = createProduct((int) (Math.random() + 1.5));
        productDto.setId(response.body().getId());
        Response<ProductDto> responseNew = getProductService()
                .getProductByID(productDto.getId())
                .execute();
        Assertions.assertTrue(responseNew.isSuccessful());
        assert responseNew.body() != null;
        Assertions.assertEquals(productDto, responseNew.body());

    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        Assertions.assertTrue(
                getProductService()
                        .deleteProduct(productDto.getId())
                        .execute()
                        .isSuccessful()
        );
    }

    @SneakyThrows
    private String getCategoryById(int id) {
        return Objects.requireNonNull(getCategoryService()
                        .getCategory(id)
                        .execute()
                        .body())
                .getTitle();
    }

    @SneakyThrows
    private Response<ProductDto> createProduct(int categoryId) {
        productDto.setCategoryTitle(getCategoryById(categoryId));
        Response<ProductDto> response = getProductService()
                .createProduct(productDto)
                .execute();
        Assertions.assertTrue(response.isSuccessful());
        assert response.body() != null;
        return response;
    }
}
