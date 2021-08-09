package com.delta_inductions.delta_task_3.Api;

import android.database.Observable;

import com.delta_inductions.delta_task_3.Model.Breeds;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    @POST("v1/images/upload")
    Call<ResponseBody> uploadimage   (@Query("api_key") String apikey,
                                      @Part MultipartBody.Part image,
                                            @Part("sub_id") RequestBody sub_id);
}
