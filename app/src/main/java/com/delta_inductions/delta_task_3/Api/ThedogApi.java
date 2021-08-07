package com.delta_inductions.delta_task_3.Api;

import com.delta_inductions.delta_task_3.Model.Breeds;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ThedogApi {
    @GET("v1/breeds")
    Call<List<Breeds>> getBreeds( @Query("limit") int limit, @Query("page") int page, @Query("order") String order);
    @GET("v1/breeds")
    Call<List<Breeds>> getAll();
    @Multipart
    @POST("v1/images")
    Call <ResponseBody> uploadImage(@Query("api_key") String key, @Part("original_filename") String imagename, @Part MultipartBody.Part image);
}
