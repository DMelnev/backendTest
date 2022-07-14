package org.lesson5.api;

import okhttp3.ResponseBody;
import org.lesson5.dto.ProductDto;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ProductService {
    @POST("products")
    Call<ProductDto> createProduct(@Body ProductDto productDto);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);

    @PUT("products")
    Call<ProductDto> modifyProduct(@Body ProductDto productDto);

    @GET("products/{id}")
    Call<ProductDto> getProductByID(@Path("id") int id);

    @GET("products")
    Call<List<ProductDto>> getProducts();
}
