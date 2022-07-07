package org.lesson5.api;

import okhttp3.ResponseBody;
import org.lesson5.dto.Product;
import retrofit2.Call;
import retrofit2.http.*;

public interface ProductService {
    @POST("products")
    Call<Product> createProduct(@Body Product createProductRequest);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);

    @PUT("products")
    Call<Product> modifyProduct(@Body Product modifyProductRequest);

    @GET("products/{id}")
    Call<Product> getProductByID(@Path("id") int id);

    @GET("products")
    Call<ResponseBody> getProducts();
}
