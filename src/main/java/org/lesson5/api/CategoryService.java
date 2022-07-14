package org.lesson5.api;

import org.lesson5.dto.CategoryResponseDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryService {
    @GET("categories/{id}")
    Call<CategoryResponseDto> getCategory(@Path("id") int id);
}
