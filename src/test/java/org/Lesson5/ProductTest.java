package org.Lesson5;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lesson5.dto.ProductDto;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static org.lesson5.utils.RetrofitUtils.getCategoryService;
import static org.lesson5.utils.RetrofitUtils.getProductService;

public class ProductTest {
    ProductDto productDto, newProductDto;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";


    @SneakyThrows
    @BeforeEach
    void beforeEach() {
        productDto = new ProductDto()
                .withTitle(new Faker().food().ingredient())
                .withPrice((int) (Math.random() * 10000));
        newProductDto = new ProductDto()
                .withTitle(new Faker().food().ingredient())
                .withPrice((int) (Math.random() * 10000));
        System.out.println(ANSI_YELLOW + "yellow");
        System.out.println(ANSI_GREEN + "green");
        System.out.println(ANSI_CYAN + "cyan");
        System.out.println(ANSI_RED + "red");
        System.out.println(ANSI_BLUE + "blue" + ANSI_RESET);

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
