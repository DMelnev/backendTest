package org.Lesson5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lesson5.dto.CategoryResponseDto;
import retrofit2.Response;

import java.io.IOException;

import static org.lesson5.utils.RetrofitUtils.getCategoryService;

public class GetCategoryTest {
//    static CategoryService categoryService;
//
//    @BeforeAll
//    static void prepare() {
//        categoryService = RetrofitUtils.getCategoryService();
//    }

    @Test
    void getCategoryById() throws IOException {
        Response<CategoryResponseDto> response = getCategoryService().getCategory(1).execute();
        Assertions.assertTrue(response.isSuccessful());
        assert response.body() != null;
        Assertions.assertEquals(1, response.body().getId());
        Assertions.assertEquals("Food", response.body().getTitle());
        response.body().getProductDtos().forEach(product ->
                Assertions.assertEquals("Food", product.getCategoryTitle()));
    }
}
