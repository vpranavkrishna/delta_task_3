package com.delta_inductions.delta_task_3.Api;

import com.delta_inductions.delta_task_3.Model.Breeds;
import com.delta_inductions.delta_task_3.Model.ResultResponse;
import com.delta_inductions.delta_task_3.Model.UploadResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ThedogApi {
    @GET("v1/breeds")
    Call<List<Breeds>> getBreeds( @Query("limit") int limit, @Query("page") int page, @Query("order") String order);
    @GET("v1/breeds")
    Call<List<Breeds>> getAll();
//    @Headers("x-api-key: 4998955b-fd71-48df-b0a3-e59382b293e8")
    @Multipart
    @POST("v1/images/upload")
    Call<UploadResponse> uploadimage   (@Header("x-api-key") String key , @Part MultipartBody.Part file, @Part("sub_id") RequestBody id);
    @GET("v1/images/{image_id}/analysis")
    Call<List<ResultResponse>> getLables(@Header("x-api-key") String key, @Path("image_id") String imageid );
}
