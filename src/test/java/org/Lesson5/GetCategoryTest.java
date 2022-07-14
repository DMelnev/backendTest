package org.Lesson5;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.lesson5.dto.CategoryResponseDto;
import retrofit2.Response;

import static org.lesson5.utils.RetrofitUtils.getCategoryService;

public class GetCategoryTest {

    @SneakyThrows
    @ParameterizedTest
    @CsvSource(value = {
            "1, Food",
            "2, Electronic"
    })
    void getCategoryById(int id, String title) {
        Response<CategoryResponseDto> response = getCategoryService().getCategory(id).execute();
        Assertions.assertTrue(response.isSuccessful());
        assert response.body() != null;
        Assertions.assertEquals(id, response.body().getId());
        Assertions.assertEquals(title, response.body().getTitle());
        response.body().getProducts().forEach(product ->
                Assertions.assertEquals(title, product.getCategoryTitle()));
    }
}
