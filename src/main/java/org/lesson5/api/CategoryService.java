package org.lesson5.api;

import org.lesson5.dto.GetCategoryResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryService {
    @GET("category/{id}")
    Call<GetCategoryResponse> getCategory(@Path("id") int id);

}
