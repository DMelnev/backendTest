package org.Lesson5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.lesson5.api.CategoryService;
import org.lesson5.dto.GetCategoryResponse;
import org.lesson5.utils.RetrofitUtils;
import retrofit2.Response;

import java.io.IOException;

public class GetCategoryTest {
    static CategoryService categoryService;

    @BeforeAll
    static void prepare() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @Test
    void getCategoryById() throws IOException {
        Response<GetCategoryResponse> response = categoryService.getCategory(1).execute();
        Assertions.assertTrue(response.isSuccessful());
        assert response.body() != null;
        Assertions.assertEquals(1, response.body().getId());
        Assertions.assertEquals("Food", response.body().getTitle());
        response.body().getProducts().forEach(product ->
                Assertions.assertEquals("Food",product.getCategoryTitle()));
    }
}
